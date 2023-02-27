package com.seda.payer.svecchiamento.svecchiamentoArchivioStorico.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.seda.bap.components.core.BapException;
import com.seda.bap.components.core.spi.ClassPrinting;
import com.seda.bap.components.core.spi.PrintCodes;
import com.seda.commons.properties.PropertiesLoader;
import com.seda.data.dao.DAOHelper;
import com.seda.data.datasource.DataSourceFactoryImpl;
import com.seda.emailsender.webservices.dati.EMailSenderResponse;
import com.seda.payer.core.bean.FlussiRen;
import com.seda.payer.core.bean.ModuloIntegrazionePagamenti;
import com.seda.payer.core.bean.ModuloIntegrazionePagamentiNodo;
import com.seda.payer.core.bean.ModuloIntegrazionePagamentiPaymentStatus;
import com.seda.payer.core.bean.NodoSpcRpt;
import com.seda.payer.core.bean.POS;
import com.seda.payer.core.bean.QuadraturaNodo;
import com.seda.payer.core.bean.Transazione;
import com.seda.payer.core.bean.TransazioneAtm;
import com.seda.payer.core.bean.TransazioneFreccia;
import com.seda.payer.core.bean.TransazioneIV;
import com.seda.payer.core.bean.TransazioneIci;
import com.seda.payer.core.exception.DaoException;
import com.seda.payer.core.riconciliazionemt.bean.GiornaleDiCassa;
import com.seda.payer.core.riconciliazionemt.bean.MovimentoDiCassa;
import com.seda.payer.svecchiamento.dao.SvecchiamentoDAO;
import com.seda.payer.svecchiamento.model.AssociazioneFlussi;
import com.seda.payer.svecchiamento.model.AssociazioneTransazioni;
import com.seda.payer.svecchiamento.svecchiamentoArchivioStorico.config.SvecchiamentoArchivioStoricoContext;
import com.seda.payer.svecchiamento.svecchiamentoArchivioStorico.config.SvecchiamentoArchivioStoricoResponse;
import com.seda.payer.svecchiamento.util.EMailSender;

public class SvecchiamentoArchivioStoricoCore {

	private static String myPrintingKeySV_SYSOUT = "SYSOUT";
	private static final SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private SvecchiamentoArchivioStoricoContext svecchiamentoContext;
	
	DataSource datasourceBK;
	private ClassPrinting classPrinting;
	String schemaBK;

	private Connection connectionBK;
	private java.sql.Date dataSvecchiamento;

	SvecchiamentoDAO sveccDAOBK = null;
	
	private String outputDir;
	private String delimiter = ",";
	
    private StringBuffer sbEmailBody = new StringBuffer();
	
	ArrayList<String> errors = new ArrayList<String>();

	String lineSeparator = "============================================================================================";

	public SvecchiamentoArchivioStoricoCore() {
		super();
		welcome();
	}

	public SvecchiamentoArchivioStoricoResponse run(String[] params, ClassPrinting classPrinting ,Logger logger) throws BapException {

		this.classPrinting = classPrinting;

		SvecchiamentoArchivioStoricoResponse svecchiamentoOutputResponse = new SvecchiamentoArchivioStoricoResponse();
		svecchiamentoOutputResponse.setCode("00");
		svecchiamentoOutputResponse.setMessage("");
		
		try {
			preProcess(params);
			processSvecchiamentoArchivioStorico();
 			printRow( myPrintingKeySV_SYSOUT, "Elaborazione terminata");
 			printRow( myPrintingKeySV_SYSOUT, lineSeparator);
			
		} catch (Exception e) {
			e.printStackTrace();
			printRow(myPrintingKeySV_SYSOUT, "Elaborazione completata con errori " + e.getMessage());
 			printRow(myPrintingKeySV_SYSOUT, lineSeparator);
 			svecchiamentoOutputResponse.setCode("30");
 			svecchiamentoOutputResponse.setMessage("Operazione terminata con errori ");
		}

		return svecchiamentoOutputResponse;
	}
	
	private void processSvecchiamentoArchivioStorico() throws DaoException, SQLException {
		printRow(myPrintingKeySV_SYSOUT, lineSeparator);
		printRow(myPrintingKeySV_SYSOUT, "Process " + "Svecchiamento archivio storico "+ "");
		printRow(myPrintingKeySV_SYSOUT, lineSeparator);
		
		try {
			printRow(myPrintingKeySV_SYSOUT, "Inserimento transazioni nei file");
			boolean result = svecchiamentoTransazioni();
			
			if(result) {
				System.out.println("Commit!");
				//commit archivio backup
				connectionBK.commit();
				connectionBK.setAutoCommit(true);
				connectionBK.close();
			} else {
				System.out.println("Rollback!");
				//rollback archivio backup
				connectionBK.rollback();
				connectionBK.setAutoCommit(true);
				connectionBK.close();
				//invio mail di errore
				bodyEmailErrori();
				invioMailErrori(sbEmailBody);		
			}
			
			System.out.println(errors);

		} catch (Exception e) {
			System.out.println("Rollback!");
			//rollback archivio backup
			connectionBK.rollback();
			connectionBK.setAutoCommit(true);
			connectionBK.close();
			//invio mail di errore
			bodyEmailErrori();
			sbEmailBody.append("<br>"+e.getMessage());
			invioMailErrori(sbEmailBody);	
		}
	}
	
	private boolean svecchiamentoTransazioni() {
		ArrayList<Transazione> listaTRA = new ArrayList<Transazione>();
		ArrayList<FlussiRen> listaREN = new ArrayList<FlussiRen>();
		ArrayList<NodoSpcRpt> listaRPT = new ArrayList<NodoSpcRpt>();
		ArrayList<QuadraturaNodo> listaQUN = new ArrayList<QuadraturaNodo>();
		ArrayList<TransazioneFreccia> listaTFR = new ArrayList<TransazioneFreccia>();
		ArrayList<TransazioneIV> listaTDT = new ArrayList<TransazioneIV>();
		ArrayList<TransazioneIci> listaTIC = new ArrayList<TransazioneIci>();
		ArrayList<ModuloIntegrazionePagamenti> listaMIP = new ArrayList<ModuloIntegrazionePagamenti>();
		ArrayList<ModuloIntegrazionePagamentiNodo> listaMIN = new ArrayList<ModuloIntegrazionePagamentiNodo>();
		ArrayList<ModuloIntegrazionePagamentiPaymentStatus> listaMPS = new ArrayList<ModuloIntegrazionePagamentiPaymentStatus>();
		ArrayList<TransazioneAtm> listaATM = new ArrayList<TransazioneAtm>();
		ArrayList<POS> listaPOS = new ArrayList<POS>();
		ArrayList<GiornaleDiCassa> listaGDC = new ArrayList<GiornaleDiCassa>();
		ArrayList<MovimentoDiCassa> listaMDC = new ArrayList<MovimentoDiCassa>();
		ArrayList<AssociazioneTransazioni> listaRMT = new ArrayList<AssociazioneTransazioni>();
		ArrayList<AssociazioneFlussi> listaRMF = new ArrayList<AssociazioneFlussi>();
		//creazione cartella dove verranno salvati i file
		outputDir = svecchiamentoContext.getOutputDir()+"/Svecchiamento_"+dataSvecchiamento.toString()+"/";
		File dir = new File(outputDir);
		dir.mkdirs();
		printRow(myPrintingKeySV_SYSOUT, "Inizio salvataggio file in: "+dir.getPath());
		//recupero delle transazioni dal db storico e creazione dei file di backup
		try {
			listaTRA = sveccDAOBK.getListTRA(dataSvecchiamento, "");
			try {
				writeFileTRA(listaTRA);
			} catch(Exception e) {
				errors.add("Creazione file TRA "+e.getMessage());
			}
			
			listaREN = sveccDAOBK.getListREN(dataSvecchiamento, "");
			try {
				writeFileREN(listaREN);
			} catch(Exception e) {
				errors.add("Creazione file REN "+e.getMessage());
			}

			listaRPT = sveccDAOBK.getListRPT(dataSvecchiamento, "");
			try {
				writeFileRPT(listaRPT);
			} catch(Exception e) {
				errors.add("Creazione file RPT "+e.getMessage());
			}
			
			listaQUN = sveccDAOBK.getListQUN(dataSvecchiamento, "");
			try {
				writeFileQUN(listaQUN);
			} catch(Exception e) {
				errors.add("Creazione file QUN "+e.getMessage());
			}
			
			listaTFR = sveccDAOBK.getListTFR(dataSvecchiamento, "");
			try {
				writeFileTFR(listaTFR);
			} catch(Exception e) {
				errors.add("Creazione file TFR "+e.getMessage());
			}
			
			listaTDT = sveccDAOBK.getListTDT(dataSvecchiamento, "");
			try {
				writeFileTDT(listaTDT);
			} catch(Exception e) {
				errors.add("Creazione file TDT "+e.getMessage());
			}
			
			listaTIC = sveccDAOBK.getListTIC(dataSvecchiamento, "");
			try {
				writeFileTIC(listaTIC);
			} catch(Exception e) {
				errors.add("Creazione file TIC "+e.getMessage());
			}
			
			listaMIN = sveccDAOBK.getListMIN(dataSvecchiamento, "");
			try {
				writeFileMIN(listaMIN);
			} catch(Exception e) {
				errors.add("Creazione file MIN "+e.getMessage());
			}
			
			listaMIP = sveccDAOBK.getListMIP(dataSvecchiamento, "");
			try {
				writeFileMIP(listaMIP);
			} catch(Exception e) {
				errors.add("Creazione file MIP "+e.getMessage());
			}
			
			listaMPS = sveccDAOBK.getListMPS(dataSvecchiamento, "");
			try {
				writeFileMPS(listaMPS);
			} catch(Exception e) {
				errors.add("Creazione file MPS "+e.getMessage());
			}
			
			listaATM = sveccDAOBK.getListATM(dataSvecchiamento, "");
			try {
				writeFileATM(listaATM);
			} catch(Exception e) {
				errors.add("Creazione file ATM "+e.getMessage());
			}
			
			listaPOS = sveccDAOBK.getListPOS(dataSvecchiamento, "");
			try {
				writeFilePOS(listaPOS);
			} catch(Exception e) {
				errors.add("Creazione file POS "+e.getMessage());
			}
			
			listaGDC = sveccDAOBK.getListGDC(dataSvecchiamento, "");
			try {
				writeFileGDC(listaGDC);
			} catch(Exception e) {
				errors.add("Creazione file GDC "+e.getMessage());
			}
			
			listaMDC = sveccDAOBK.getListMDC(dataSvecchiamento, "");
			try {
				writeFileMDC(listaMDC);
			} catch(Exception e) {
				errors.add("Creazione file MDC "+e.getMessage());
			}
			
			listaRMT = sveccDAOBK.getListRMT(dataSvecchiamento, "");
			try {
				writeFileRMT(listaRMT);
			} catch(Exception e) {
				errors.add("Creazione file RMT "+e.getMessage());
			}
			
			listaRMF = sveccDAOBK.getListRMF(dataSvecchiamento, "");
			try {
				writeFileRMF(listaRMF);
			} catch(Exception e) {
				errors.add("Creazione file RMF "+e.getMessage());
			}
			
		} catch(Exception e) {
			errors.add("Errore svecchiamento transazioni "+e.getMessage());
			e.printStackTrace();
		}		
		
		//eliminazione delle transazioni dal db operativo
		if(errors.isEmpty()) {
			printRow(myPrintingKeySV_SYSOUT, "Cancellazione transazioni nell'Archivio Operativo");
			
			for(AssociazioneFlussi associazioneFlussi: listaRMF){
				try {
					sveccDAOBK.deleteRMF(associazioneFlussi);
				} catch(Exception e) {
					errors.add("Cancellazione RMF "+associazioneFlussi.getIdMdc()+" - "+e.getMessage());
				}
			}
			for(AssociazioneTransazioni associazioneTransazioni: listaRMT){
				try {
					sveccDAOBK.deleteRMT(associazioneTransazioni);
				} catch(Exception e) {
					errors.add("Cancellazione RMT "+associazioneTransazioni.getIdMdc()+" - "+e.getMessage());
				}
			}
			for(MovimentoDiCassa movimentoDiCassa: listaMDC){
				try {
					sveccDAOBK.deleteMDC(movimentoDiCassa);
				} catch(Exception e) {
					errors.add("Cancellazione MDC "+movimentoDiCassa.getId()+" - "+e.getMessage());
				}
			}
			for(GiornaleDiCassa giornaleDiCassa: listaGDC){
				try {
					sveccDAOBK.deleteGDC(giornaleDiCassa);
				} catch(Exception e) {
					errors.add("Cancellazione GDC "+giornaleDiCassa.getId()+" - "+e.getMessage());
				}
			}
			for(POS pos: listaPOS){
				try {
					sveccDAOBK.deletePOS(pos);
				} catch(Exception e) {
					errors.add("Cancellazione POS "+pos.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			for(TransazioneAtm transazioneAtm: listaATM){
				try {
					sveccDAOBK.deleteATM(transazioneAtm);
				} catch(Exception e) {
					errors.add("Cancellazione ATM "+transazioneAtm.getChiaveTransazioneInterna()+" - "+e.getMessage());
				}
			}
			for(ModuloIntegrazionePagamentiPaymentStatus moduloIntegrazionePagamentiPaymentStatus: listaMPS){
				try {
					sveccDAOBK.deleteMPS(moduloIntegrazionePagamentiPaymentStatus);
				} catch(Exception e) {
					errors.add("Cancellazione MPS "+moduloIntegrazionePagamentiPaymentStatus.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			for(ModuloIntegrazionePagamenti moduloIntegrazionePagamenti: listaMIP){
				try {
					sveccDAOBK.deleteMIP(moduloIntegrazionePagamenti);
				} catch(Exception e) {
					errors.add("Cancellazione MIP "+moduloIntegrazionePagamenti.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			for(ModuloIntegrazionePagamentiNodo moduloIntegrazionePagamentiNodo: listaMIN){
				try {
					sveccDAOBK.deleteMIN(moduloIntegrazionePagamentiNodo);
				} catch(Exception e) {
					errors.add("Cancellazione MIN "+moduloIntegrazionePagamentiNodo.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			for(TransazioneIci transazioneIci: listaTIC){
				try {
					sveccDAOBK.deleteTIC(transazioneIci);
				} catch(Exception e) {
					errors.add("Cancellazione TIC "+transazioneIci.getChiaveTransazioneIci()+" - "+e.getMessage());
				}
			}
			for(TransazioneIV transazioneIV: listaTDT){
				try {
					sveccDAOBK.deleteTDT(transazioneIV);
				} catch(Exception e) {
					errors.add("Cancellazione TDT "+transazioneIV.getChiaveTransazioneDettaglio()+" - "+e.getMessage());
				}
			}
			for(TransazioneFreccia transazioneFreccia: listaTFR){				
				try {
					sveccDAOBK.deleteTFR(transazioneFreccia);
				} catch(Exception e) {
					errors.add("Cancellazione TFR "+transazioneFreccia.getChiaveTransazioneDettaglio()+" - "+e.getMessage());
				}
			}
			for(QuadraturaNodo quadraturaNodo: listaQUN){	
				try {
					sveccDAOBK.deleteQUN(quadraturaNodo);
				} catch(Exception e) {
					errors.add("Cancellazione QUN "+quadraturaNodo.getKeyQuadratura()+" - "+e.getMessage());
				}
			}
			for(NodoSpcRpt nodoSpcRpt: listaRPT){
				try {
					sveccDAOBK.deleteRPT(nodoSpcRpt);
				} catch(Exception e) {
					errors.add("Cancellazione RPT "+nodoSpcRpt.getId()+" - "+e.getMessage());
				}
			}
			for(FlussiRen flussiRen: listaREN){
				try {
					sveccDAOBK.deleteREN(flussiRen);
				} catch(Exception e) {
					errors.add("Cancellazione REN "+flussiRen.getChiaveRendicontazione()+" - "+e.getMessage());
				}
			}
			//eliminazione del trigger PYTRAT3
			try {
				sveccDAOBK.dropTrigger();
			} catch(Exception e) {
				errors.add("Cancellazione TRIGGER PYTRAT3 "+e.getMessage());
			}	
			for(Transazione transazione: listaTRA){
				try {
					sveccDAOBK.deleteTRA(transazione);
				} catch(Exception e) {
					errors.add("Cancellazione TRA "+transazione.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			//ricreo il trigger PYTRAT3 cancellato prima
			try {
				sveccDAOBK.createTrigger(schemaBK);
			} catch(Exception e) {
				e.printStackTrace();
				errors.add("Creazione TRIGGER PYTRAT3 "+e.getMessage());
			}
			
		}
		if(errors.isEmpty()) {
			return true;
		} else {
			printRow(myPrintingKeySV_SYSOUT, "Si è verificato un errore. Annullamento operazione di svecchiamento.");
			return false;
			
		}
	}

	private void welcome() {
		StringBuffer w = new StringBuffer("");    	
		w.append("" +" Svecchiamento archivio storico "+ "\n");
		w.append(System.getProperties().get("java.specification.vendor") + " ");
		w.append(System.getProperties().get("java.version") + "\n");
		w.append("JAVA HOME : "+System.getProperties().get("java.home") + "\n");
		w.append("(C) Copyright 2018 di E-SED Scrl"  + "\n");
		w.append("\n");
		printRow(myPrintingKeySV_SYSOUT,w.toString());
		w=null;

		printRow(myPrintingKeySV_SYSOUT,lineSeparator);
		printRow(myPrintingKeySV_SYSOUT,"Avvio " + "Svecchiamento archivio storico " + "");
		printRow(myPrintingKeySV_SYSOUT,lineSeparator);
	}

	private void printRow(String printer, String string) {
		System.out.println(string);
		if (classPrinting != null)
			try {
				classPrinting.print(printer, string);
			} catch (SQLException e) {
			}
	}

	public void printRow(String printer, String row, PrintCodes printCodes) {
		System.out.println(row);
		if (classPrinting != null)
			try {
				classPrinting.print(printer, row, printCodes);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	private void preProcess(String[] params) throws Exception {
		svecchiamentoContext = new SvecchiamentoArchivioStoricoContext();
		svecchiamentoContext.loadSchedeBap(params);
		Properties config = null;

		String fileConf = svecchiamentoContext.getParameter("CONFIGPATH"); 																	
		String data = svecchiamentoContext.getParameter("DATE");
		dataSvecchiamento = new java.sql.Date(dateformat.parse(data).getTime());

		try {
			config = PropertiesLoader.load(fileConf);
		} catch (FileNotFoundException e) {
			printRow(myPrintingKeySV_SYSOUT, "File di configurazione " + fileConf + " non trovato");
			throw new Exception();
		} catch (IOException e) {
			printRow(myPrintingKeySV_SYSOUT, "Errore file di configurazione " + fileConf + " " + e);
			throw new Exception();
		}
		svecchiamentoContext.setConfig(config);

		printRow(myPrintingKeySV_SYSOUT, "Configurazione esterna caricata da " + fileConf);
		printRow(myPrintingKeySV_SYSOUT, "Data svecchiamento = " + dataSvecchiamento);
		
		if (svecchiamentoContext.getOutputDir() == null) {
			printRow(myPrintingKeySV_SYSOUT, "Cartella di destinazione non configurata");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcDriverBK() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCDriverBK non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcUrlBK() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCUrlBK non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcUserBK() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCUSerBK non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcPasswordBK() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCPasswordBK non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getSchemaBK() == null) {
			printRow(myPrintingKeySV_SYSOUT, "Datasource Schema BK non configurato");
			throw new Exception();
		}
		if (this.datasourceBK == null) {
			DataSourceFactoryImpl dataSourceFactory = new DataSourceFactoryImpl();
			Properties dsProperties = new Properties();
			dsProperties.put(DAOHelper.JDBC_DRIVER, svecchiamentoContext.getJdbcDriverBK());
			dsProperties.put(DAOHelper.JDBC_URL, svecchiamentoContext.getJdbcUrlBK());
			dsProperties.put(DAOHelper.JDBC_USER, svecchiamentoContext.getJdbcUserBK());
			dsProperties.put(DAOHelper.JDBC_PASSWORD, svecchiamentoContext.getJdbcPasswordBK());
			//dsProperties.put("autocommit", "true");
			dataSourceFactory.setProperties(dsProperties);
			this.datasourceBK = dataSourceFactory.getDataSource();
		}
		if (schemaBK == null){
			this.schemaBK = svecchiamentoContext.getSchemaBK();
		}
		
		connectionBK = this.datasourceBK.getConnection();
		connectionBK.setAutoCommit(false);

		sveccDAOBK = new SvecchiamentoDAO(connectionBK, schemaBK);	
	}
	
	
	
	
	public void writeFileTRA(ArrayList<Transazione> listaTRA) {
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"TRA.csv");
			String headerFile = "TRA_KTRAKTRA, TRA_CSOCCSOC, TRA_KGTWKGTW, TRA_GTRADTRA, TRA_GTRADPAG, TRA_FTRAFESI, " +
					"TRA_CTRAIDBA, TRA_CTRAAUBA, TRA_DTRAINIP, TRA_ETRAECON, TRA_CTRANSMS, TRA_DTRADENO, TRA_DTRAINDI, TRA_CTRACCAP, " +
					"TRA_DTRACITT, TRA_CTRAPROV, TRA_FTRABOLL, TRA_FTRACONT, TRA_FTRAAMMI, TRA_FTRAFSMS, TRA_FTRAPOST, TRA_GTRADALL, " +
					"TRA_CTRACORD, TRA_ITRAITOT, TRA_ITRACOTR, TRA_ITRACENT, TRA_ITRACOGW, TRA_ITRACONT, TRA_KTRAKEST, TRA_CTRAREND, TRA_DTRANOTE, " +
					"TRA_PQUAPKEY, TRA_CTRAQUAD, TRA_FTRASCAC, TRA_FTRASCAA, TRA_FTRARIVE, TRA_GTRARIVE, TRA_TTRASTOR, TRA_NTRATENT, TRA_FTRARIVA," +
					"TRA_CTRAOPIN, TRA_CTRAOPT1, TRA_CTRAOPT2, TRA_CTRAKPOF, TRA_GTRAGAGG, TRA_CTRACOPE, TRA_GTRAACCR, TRA_CTRANRFO";
			
			fileWriter.append(headerFile.toString());
			for (Transazione transazione: listaTRA){
				fileWriter.append("\n");
				fileWriter.append(transazione.getChiaveTransazione()+delimiter);
				fileWriter.append(transazione.getCodiceSocieta()+delimiter);
				fileWriter.append(transazione.getChiaveGatewayDiPagamento()+delimiter);
				fileWriter.append(dateformat2.format(transazione.getDataInizioTransazione())+delimiter);
				fileWriter.append(dateformat2.format(transazione.getDataEffettivoPagamentoSuGateway())+delimiter);
				fileWriter.append(transazione.getFlagEsitoTransazione()+delimiter);
				fileWriter.append(transazione.getCodiceIdentificativoBanca()+delimiter);
				fileWriter.append(transazione.getCodiceAutorizzazioneBanca()+delimiter);
				fileWriter.append(transazione.getIndirizzoIpTerminalePagamento()+delimiter);
				fileWriter.append(transazione.getEmailContribuente()+delimiter);
				fileWriter.append(transazione.getNumeroSmsContribuente()+delimiter);
				fileWriter.append(transazione.getDenominazioneContribuentePerInvioPostaOrdinaria()+delimiter);
				fileWriter.append(transazione.getIndirizzoContribuentePerInvioPostaOrdinaria()+delimiter);
				fileWriter.append(transazione.getCapContribuentePerInvioPostaOrdinaria()+delimiter);
				fileWriter.append(transazione.getCittaContribuentePerInvioPostaOrdinaria()+delimiter);
				fileWriter.append(transazione.getProvinciaContribuentePerInvioPostaOrdinaria()+delimiter);
				fileWriter.append(transazione.getInvioNotificaBollettiniPerEmail()+delimiter);
				fileWriter.append(transazione.getInvioAutorizzazioneBancaPerEmailContribuente()+delimiter);
				fileWriter.append(transazione.getInvioAutorizzazioneBancaPerEmailAmministratore()+delimiter);
				fileWriter.append(transazione.getInvioNotificaAutorizzazioneBancaPerSms()+delimiter);
				fileWriter.append(transazione.getInvioNotificaPerPostaOrdinaria()+delimiter);
				fileWriter.append(dateformat2.format(transazione.getDataAllineamentoBatchTransazione())+delimiter);
				fileWriter.append(transazione.getCodiceOrdineGateway()+delimiter);
				fileWriter.append(String.valueOf(transazione.getImportoTotaleTransazione())+delimiter);
				fileWriter.append(String.valueOf(transazione.getImportoCostoTransazione())+delimiter);
				fileWriter.append(String.valueOf(transazione.getImportoCostoTransazioneEnte())+delimiter);
				fileWriter.append(String.valueOf(transazione.getImportoCostoGateway())+delimiter);
				fileWriter.append(String.valueOf(transazione.getImportoCostoSpeseDiNotifica())+delimiter);
				fileWriter.append(transazione.getChiaveTransazioneSistemaEsterno()+delimiter);
				fileWriter.append(transazione.getStatoRendicontazione()+delimiter);
				fileWriter.append(transazione.getNoteGeneriche()+delimiter);
				fileWriter.append(Long.toString(transazione.getChiaveQuadratura())+delimiter);
				fileWriter.append(transazione.getStatoQuadratura()+delimiter);
				fileWriter.append(transazione.getInvioNotificaStatoPagamentoEmailContribuente()+delimiter);
				fileWriter.append(transazione.getInvioNotificaStatoPagamentoEmailAmministratore()+delimiter);
				fileWriter.append(transazione.getStatoRiversamento()+delimiter);
				fileWriter.append(dateformat2.format(transazione.getDataRiversamento())+delimiter);
				fileWriter.append((CharSequence)transazione.getTipoStorno()+delimiter);
				fileWriter.append(String.valueOf(transazione.getNumeroTentativiPagamento())+delimiter);
				fileWriter.append(transazione.getFlagRiversamentoAutomatico()+delimiter);
				fileWriter.append(transazione.getOperatoreInserimento()+delimiter);
				fileWriter.append(transazione.getCampoOpzionale1()+delimiter);
				fileWriter.append(transazione.getCampoOpzionale2()+delimiter);
				fileWriter.append(transazione.getChiaveFlussoRendicontazioneRnincaext()+delimiter);
				fileWriter.append(dateformat2.format(transazione.getDataUltimoAggiornamento())+delimiter);
				fileWriter.append(transazione.getOpertoreUltimoAggiornamento()+delimiter);
				fileWriter.append(dateformat2.format(transazione.getDataAccredito()));
			}
			fileWriter.close();
		}catch (Exception e ) {
			e.printStackTrace();
		}
	}
	
	
	public void writeFileTDT(ArrayList<TransazioneIV> listaTDT){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"TDT.csv");
			String headerFile = "TDT_KTDTKTDT, TDT_KTRAKTRA, TDT_CSOCCSOC, TDT_CUTECUTE, TDT_KANEKENT_CON, TDT_KANEKENT_ENT, " +
				"TDT_CTSECTSE, TDT_CISECISE, TDT_CSERCSER, TDT_TBOLTBOL, TDT_NTDTNCCP, TDT_DTDTDINT, TDT_CTDTCBOL, TDT_CTDTADOC, " +
				"TDT_CTDTNDOC, TDT_TTDTTDOC, TDT_PTDTRATA, TDT_GTDTSCAD, TDT_CTDTCFIS, TDT_DTDTDENO, TDT_DTDTINDI, TDT_CTDTENTE, TDT_DTDTCITT, " +
				"TDT_CTDTPROV, TDT_DTDTCCAP, TDT_GTDTSANZ, TDT_DTDTTARG, TDT_KRENKREN, TDT_ITDTTOTA, TDT_ITDTIMP1, TDT_ITDTIMP2, " +
				"TDT_ITDTIMP3, TDT_ITDTIMP4, TDT_ITDTIMP5, TDT_ITDTIMP6, TDT_ITDTIMP7, TDT_ITDTIMP8, TDT_CTDTMESE, TDT_CTDTANNO, " +
				"TDT_CTDTMVAL, TDT_CTDTRIDU, TDT_CTDTCATE, TDT_DTDTNOTE, TDT_FTDTPAGA, TDT_PSCAPKEY, TDT_FTDTESTE, TDT_GTDTGAGG, TDT_CTDTCOPE, TDT_FTDTFSZR," +
				"TDT_CTDTCODT, TDT_CTDTIBAN, TDT_ITDTIMBL, TDT_DTDTSMBD, TDT_CTDTTBDE, TDT_CTDTPDRE, TDT_PTDTPIUR, TDT_CTDTCIUR, TDT_PTDTPQUN, TDT_CTDTIBAN2";

			fileWriter.append(headerFile);
			for (TransazioneIV transazioniiv: listaTDT){
				fileWriter.append("\n");
				fileWriter.append(transazioniiv.getChiaveTransazioneDettaglio()+delimiter);
				fileWriter.append(transazioniiv.getChiaveTransazioneGenerale()+delimiter);
				fileWriter.append(transazioniiv.getCodiceSocieta()+delimiter);
				fileWriter.append(transazioniiv.getCodiceUtente()+delimiter);
				fileWriter.append(transazioniiv.getChiaveEnteCon()+delimiter);
				fileWriter.append(transazioniiv.getChiaveEnteEnt()+delimiter);
				fileWriter.append(transazioniiv.getCodiceTipologiaServizio()+delimiter);
				fileWriter.append(transazioniiv.getCodiceImpostaServizio()+delimiter);
				fileWriter.append(transazioniiv.getCodiceServizio()+delimiter);
				fileWriter.append(transazioniiv.getTipoBollettino()+delimiter);
				fileWriter.append(transazioniiv.getNumeroContoCorrente()+delimiter);
				fileWriter.append(transazioniiv.getDescrizioneIntestatarioContoCorrente()+delimiter);
				fileWriter.append(transazioniiv.getCodiceBollettinoPremarcatoMav()+delimiter);
				fileWriter.append(transazioniiv.getAnnoDocumento()+delimiter);
				fileWriter.append(transazioniiv.getNumeroDocumento()+delimiter);
				fileWriter.append(transazioniiv.getTipoDocumentoHost()+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getProgressivoRata())+delimiter);
				fileWriter.append(dateformat2.format(transazioniiv.getDataScadenzaRata())+delimiter);
				fileWriter.append(transazioniiv.getCodiceFiscale()+delimiter);
				fileWriter.append(transazioniiv.getDenominazione()+delimiter);
				fileWriter.append(transazioniiv.getIndirizzo()+delimiter);
				fileWriter.append(transazioniiv.getCodiceEnteComuneDomicilioFiscale()+delimiter);
				fileWriter.append(transazioniiv.getCitta()+delimiter);
				fileWriter.append(transazioniiv.getProvincia()+delimiter);
				fileWriter.append(transazioniiv.getCap()+delimiter);
				fileWriter.append(dateformat2.format(transazioniiv.getDataSanzione())+delimiter);
				fileWriter.append(transazioniiv.getTarga()+delimiter);
				fileWriter.append(transazioniiv.getChiaveSpedizione()+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getImportoTotaleBollettino())+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getImportoOneri())+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getImportoResiduoTotale())+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getImportoResiduoCompenso())+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getImportoResiduoMora())+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getImportoResiduoSpese())+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getImportoResiduoAltreSpese())+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getImportoResiduoTributo())+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getImportoResiduoNotifica())+delimiter);
				fileWriter.append(transazioniiv.getMeseScadenzaBolloAuto()+delimiter);
				fileWriter.append(transazioniiv.getAnnoScadenzaBolloAuto()+delimiter);
				fileWriter.append(transazioniiv.getMesiValiditaBolloAuto()+delimiter);
				fileWriter.append(transazioniiv.getMesiRiduzioneBolloAuto()+delimiter);
				fileWriter.append(transazioniiv.getCategoriaBolloAuto()+delimiter);
				fileWriter.append(transazioniiv.getNotePremarcato()+delimiter);
				fileWriter.append(transazioniiv.getFlagTipoPagamento()+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getChiavePagamentoScartato())+delimiter);
				fileWriter.append(transazioniiv.getFlagResidenzaEstero()+delimiter);
				fileWriter.append(dateformat2.format(transazioniiv.getDataultimoAggiornamento())+delimiter);
				fileWriter.append(transazioniiv.getOpertoreUltimoAggiornamento()+delimiter);
				fileWriter.append(transazioniiv.getFlagSanzioneRidotta()+delimiter);
				fileWriter.append(transazioniiv.getCodiceTessera()+delimiter);
				fileWriter.append(transazioniiv.getCodiceIban()+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getImportoMarcaDaBolloDigitale())+delimiter);
				fileWriter.append(transazioniiv.getSegnaturaMarcaDaBolloDigitale()+delimiter);
				fileWriter.append(transazioniiv.getTipoBolloDaErogare()+delimiter);
				fileWriter.append(transazioniiv.getProvinciaResidenza()+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getProgBollettino())+delimiter);
				fileWriter.append(transazioniiv.getCodiceIUR()+delimiter);
				fileWriter.append(String.valueOf(transazioniiv.getProgQuadratura())+delimiter);
				fileWriter.append(transazioniiv.getCodiceIbanPostale());		
			}
			fileWriter.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeFileQUN(ArrayList<QuadraturaNodo> listaQUN){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"QUN.csv");
			String headerFile = "QUN_PQUNPKEY, QUN_CSOCCSOC, QUN_CUTECUTE, QUN_KGTWKGTW, QUN_DQUNSUPP, " +
				"QUN_GQUNDINI, QUN_GQUNDEND, QUN_NQUNTRAN, QUN_IQUNSQUA, QUN_CQUNQUAD, QUN_FQUNELAB, " +
				"QUN_GQUNCHIU, QUN_GQUNGAGG, QUN_CQUNCOPE, QUN_CQUNVERS, QUN_CQUNFLUS, QUN_GQUNFLUS, " +
				"QUN_GQUNIURE, QUN_GQUNREGO, QUN_CQUNTIDU_MITT, QUN_CQUNCIDU_MITT, QUN_DQUNDENO_MITT, " +
				"QUN_CQUNTIDU_RICE, QUN_CQUNCIDU_RICE, QUN_DQUNDENO_RICE, QUN_NQUNNTOT, QUN_IQUNITOT, QUN_KANEKENT, QUN_NQUNNREC";

			fileWriter.append(headerFile);
			for (QuadraturaNodo quadraturaNodo: listaQUN){
				fileWriter.append("\n");
				fileWriter.append(Long.toString(quadraturaNodo.getKeyQuadratura())+delimiter);
				fileWriter.append(quadraturaNodo.getCodSocieta()+delimiter);
				fileWriter.append(quadraturaNodo.getCodUtente()+delimiter);
				fileWriter.append(quadraturaNodo.getKeyGateway()+delimiter);
				fileWriter.append(quadraturaNodo.getNomeFileTxt()+delimiter);
				fileWriter.append(dateformat2.format(quadraturaNodo.getDtInizioRend())+delimiter);
				fileWriter.append(dateformat2.format(quadraturaNodo.getDtFineRend())+delimiter);
				fileWriter.append(Long.toString(quadraturaNodo.getNumTransazioni())+delimiter);
				fileWriter.append(String.valueOf(quadraturaNodo.getImpSquadratura())+delimiter);
				fileWriter.append(quadraturaNodo.getStatoSquadratura()+delimiter);
				fileWriter.append(quadraturaNodo.getMovimentoElab()+delimiter);
				fileWriter.append(dateformat2.format(quadraturaNodo.getDtChiusuraFlusso())+delimiter);
				fileWriter.append(dateformat2.format(quadraturaNodo.getDtUltAggior())+delimiter);
				fileWriter.append(quadraturaNodo.getCodOperatore()+delimiter);
				fileWriter.append(quadraturaNodo.getVersOggetto()+delimiter);
				fileWriter.append(quadraturaNodo.getIdFlusso()+delimiter);
				fileWriter.append(dateformat2.format(quadraturaNodo.getDtflusso())+delimiter);
				fileWriter.append(quadraturaNodo.getIdUnivocoRegol()+delimiter);
				fileWriter.append(dateformat2.format(quadraturaNodo.getDtregol())+delimiter);
				fileWriter.append(quadraturaNodo.getTipoIdUnivocoMitt()+delimiter);
				fileWriter.append(quadraturaNodo.getCodIdUnivocoMitt()+delimiter);
				fileWriter.append(quadraturaNodo.getDenomMitt()+delimiter);
				fileWriter.append(quadraturaNodo.getTipoIdUnivocoRice()+delimiter);
				fileWriter.append(quadraturaNodo.getCodIdUnivocoRice()+delimiter);
				fileWriter.append(quadraturaNodo.getDenomRice()+delimiter);
				fileWriter.append(String.valueOf(quadraturaNodo.getNumTotPagamenti())+delimiter);
				fileWriter.append(String.valueOf(quadraturaNodo.getImpTotPagamenti())+delimiter);
				fileWriter.append(quadraturaNodo.getKeyEnte()+delimiter);
				fileWriter.append(String.valueOf(quadraturaNodo.getNumTransazioniRecuperate()));
			}
			fileWriter.close();
		} catch (Exception e){
			e.printStackTrace();
		}

	}
	
	
	public void writeFileREN(ArrayList<FlussiRen> listaREN) {
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"REN.csv");
			String headerFile = "REN_KRENKREN, REN_TBOLTFLU, REN_CSOCCSOC, REN_CUTECUTE, REN_KANEKENT, REN_GRENCREA, REN_PRENPROG, " +
			"REN_CTSECTSE, REN_CISECISE, REN_KGTWKGTW, REN_NRENNCCP, REN_DRENFILE, REN_FRENMAIL, REN_FRENIFTP, " +
			"REN_KCNBKCNB, REN_GRENGAGG, REN_CRENCOPE, REN_CRENIDPSP";
			
			fileWriter.append(headerFile.toString());
			for (FlussiRen flussiRen: listaREN){
				fileWriter.append("\n");
				fileWriter.append(flussiRen.getChiaveRendicontazione()+delimiter);
				fileWriter.append(flussiRen.getTipologiaFlusso()+delimiter);
				fileWriter.append(flussiRen.getCodiceSocieta()+delimiter);
				fileWriter.append(flussiRen.getCodiceUtente()+delimiter);
				fileWriter.append(flussiRen.getChiaveEnte()+delimiter);
				fileWriter.append(dateformat2.format(flussiRen.getDataCreazioneFlusso())+delimiter);
				fileWriter.append(String.valueOf(flussiRen.getProgressivoFlusso())+delimiter);
				fileWriter.append(flussiRen.getCodiceTiplogiaServizio()+delimiter);
				fileWriter.append(flussiRen.getCodiceImpostaServizio()+delimiter);
				fileWriter.append(flussiRen.getChiaveGateway()+delimiter);
				fileWriter.append(flussiRen.getNumeroContoCorrentePostale()+delimiter);
				fileWriter.append(flussiRen.getNomeFile()+delimiter);
				fileWriter.append(flussiRen.getFlagInvioMail()+delimiter);
				fileWriter.append(flussiRen.getFlagInvioFtp()+delimiter);
				fileWriter.append(flussiRen.getChiaveFlussoContabilita()+delimiter);
				fileWriter.append(dateformat2.format(flussiRen.getDataUltimoAggiornamento())+delimiter);
				fileWriter.append(flussiRen.getOperatoreUltimoAggiornamento()+delimiter);
				fileWriter.append(flussiRen.getIdPSP());	
			}			
			fileWriter.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void writeFileRPT(ArrayList<NodoSpcRpt> listaRPT){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"RPT.csv");
			String headerFile = "RPT_PRPTPKEY, RPT_KTRAKTRA, RPT_CSOCCSOC, RPT_CUTECUTE, "+
				"RPT_KRPTKIUV, RPT_CODTCODT, RPT_KKEYKKEY, RPT_INODIMPO, RPT_GRPTGINS, "+
	            "RPT_CRPTRPT, RPT_CRPTRPTFIR, RPT_CRPTRPTESITO, RPT_CRPTRT, RPT_CRPTRTESITO, "+
	            "RPT_CRPTRTFIR, RPT_PQUNPKEY, RPT_CRPTQUAD, RPT_CRPTRR, RPT_CRPTER, RPT_CRPTCCCP, "+
	            "RPT_CRPTCIDD, RPT_CRPTIDPSP, RPT_CRPTINTPSP, RPT_CRPTCANPSP, RPT_CRPTPROT, RPT_CRPTERRC, "+
	            "RPT_CRPTESIT, RPT_CRPTISIP, RPT_CRPTNPRT, RPT_CRPTMISC, RPT_CRPTMITD, RPT_CRPTMIUD";
			
			fileWriter.append(headerFile.toString());
			for (NodoSpcRpt nodoSpcRpt: listaRPT){
				fileWriter.append("\n");
				fileWriter.append(String.valueOf(nodoSpcRpt.getId())+delimiter);
				fileWriter.append(nodoSpcRpt.getChiaveTra()+delimiter);
				fileWriter.append(nodoSpcRpt.getCodSocieta()+delimiter);
				fileWriter.append(nodoSpcRpt.getCodUtente()+delimiter);
				fileWriter.append(nodoSpcRpt.getCodiceIuv()+delimiter);
				fileWriter.append(nodoSpcRpt.getCodiceTabella()+delimiter);
				fileWriter.append(nodoSpcRpt.getChiaveTabella()+delimiter);
				fileWriter.append(String.valueOf(nodoSpcRpt.getImporto())+delimiter);
				fileWriter.append(dateformat2.format(nodoSpcRpt.getDataIuv())+delimiter);
				fileWriter.append(nodoSpcRpt.getRpt()+delimiter);
				fileWriter.append(nodoSpcRpt.getRptFirma()+delimiter);
				fileWriter.append(nodoSpcRpt.getRptEsito()+delimiter);
				fileWriter.append(nodoSpcRpt.getRt()+delimiter);
				fileWriter.append(nodoSpcRpt.getRtEsito()+delimiter);
				fileWriter.append(nodoSpcRpt.getRtFirma()+delimiter);
				fileWriter.append(String.valueOf(nodoSpcRpt.getIdQuadratura())+delimiter);
				fileWriter.append(nodoSpcRpt.getStatoQuadratura()+delimiter);
				fileWriter.append("null"+delimiter);
				fileWriter.append("null"+delimiter);
				fileWriter.append(nodoSpcRpt.getCodContestoPagamento()+delimiter);
				fileWriter.append(nodoSpcRpt.getIdDominio()+delimiter);
				fileWriter.append(nodoSpcRpt.getIdIntermediarioPSP()+delimiter);
				fileWriter.append(nodoSpcRpt.getIdPSP()+delimiter);
				fileWriter.append(nodoSpcRpt.getIdCanalePSP()+delimiter);
				fileWriter.append(nodoSpcRpt.getStatoProtocollo()+delimiter);
				fileWriter.append(nodoSpcRpt.getCodiceErroreComunicazione()+delimiter);
				fileWriter.append(nodoSpcRpt.getEsitoComunicazione()+delimiter);
				fileWriter.append(nodoSpcRpt.getIdTemporaneo()+delimiter);
				fileWriter.append(nodoSpcRpt.getNumeroprotocolloRT()+delimiter);
				fileWriter.append(nodoSpcRpt.getIdSessioneCarrello()+delimiter);
				fileWriter.append(nodoSpcRpt.getIdentificativoTipoDovuto()+delimiter);
				fileWriter.append(nodoSpcRpt.getIdentificativoUnivocoDovuto());
			}
			fileWriter.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void writeFileMIN(ArrayList<ModuloIntegrazionePagamentiNodo> listaMIN) {
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"MIN.csv");
			String headerFile = "MIN_KTRAKTRA, MIN_CSOCCSOC, MIN_CUTECUTE, MIN_KANEKENT, MIN_KMIPPORT, MIN_CMIPSTAT, " +
				"MIN_KMIPOPER, MIN_FMIPNOTI, MIN_CMIPPREQ, MIN_CMIPPDAT, MIN_CMIPUESI, MIN_CMIPPORT, MIN_DMIPOPT1, MIN_DMIPOPT2, MIN_GMIPGAGG";
			fileWriter.append(headerFile);
			for (ModuloIntegrazionePagamentiNodo moduloIntPagamentiNodo: listaMIN){
				fileWriter.append("\n");
				fileWriter.append(moduloIntPagamentiNodo.getChiaveTransazione()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getCodiceSocieta()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getCodiceUtente()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getChiaveEnte()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getIdPortale()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getStatoPagamentoCUP()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getNumeroOperazione()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getCommitNotifica()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getXmlPaymentRequest()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getXmlPaymentData()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getUltimoEsitoNotifica()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getCodicePortaleEsterno()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getParametriOpzionali1()+delimiter);
				fileWriter.append(moduloIntPagamentiNodo.getParametriOpzionali2()+delimiter);
				fileWriter.append(dateformat2.format(moduloIntPagamentiNodo.getDataUltimoAggiornamento().getTime()));			
			}
			fileWriter.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeFileMIP(ArrayList<ModuloIntegrazionePagamenti> listaMIP) {
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"MIP.csv");
			String headerFile = "MIP_KTRAKTRA, MIP_CSOCCSOC, MIP_CUTECUTE, MIP_KANEKENT, MIP_KMIPPORT, MIP_CMIPSTAT," +
				" MIP_KMIPOPER, MIP_FMIPNOTI, MIP_CMIPPREQ, MIP_CMIPPDAT, MIP_CMIPUESI, MIP_CMIPPORT, MIP_DMIPOPT1, MIP_DMIPOPT2, MIP_GMIPGAGG";

			fileWriter.append(headerFile);
			for (ModuloIntegrazionePagamenti moduloIntegrazionePagamenti: listaMIP){
				fileWriter.append("\n");
				fileWriter.append(moduloIntegrazionePagamenti.getChiaveTransazione()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getCodiceSocieta()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getCodiceUtente()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getChiaveEnte()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getIdPortale()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getStatoPagamentoCUP()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getNumeroOperazione()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getCommitNotifica()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getXmlPaymentRequest()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getXmlPaymentData()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getUltimoEsitoNotifica()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getCodicePortaleEsterno()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getParametriOpzionali1()+delimiter);
				fileWriter.append(moduloIntegrazionePagamenti.getParametriOpzionali2()+delimiter);
				fileWriter.append(dateformat2.format(moduloIntegrazionePagamenti.getDataUltimoAggiornamento().getTime()));
		
			}
			fileWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void writeFileMPS(ArrayList<ModuloIntegrazionePagamentiPaymentStatus> listaMPS){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"MPS.csv");
			String headerFile = "MPS_KTRAKTRA, MPS_NMPSGRUP, MPS_NMPSCORR, MPS_CMPSMODA, MPS_KMPSPORT, MPS_KMPSOPER, " +
				"MPS_CMPSPSTS, MPS_FMPSFESI, MPS_CMPSPDAT, MPS_CMPSCMSG, MPS_CMPSESIT, MPS_CMPSCODE, MPS_DMPSRESP, " +
				"MPS_DMPSOPT1, MPS_DMPSOPT2, MPS_GMPSGAGG, MPS_CMPSCOPE";
		
			fileWriter.append(headerFile);

			for (ModuloIntegrazionePagamentiPaymentStatus moduloIntegrazionePagamentiStatus: listaMPS){
				fileWriter.append("\n");
				fileWriter.append(moduloIntegrazionePagamentiStatus.getChiaveTransazione()+delimiter);
				fileWriter.append(String.valueOf(moduloIntegrazionePagamentiStatus.getGruppoTentativiNotifica())+delimiter);
				fileWriter.append(String.valueOf(moduloIntegrazionePagamentiStatus.getNumeroTentativoNotifica())+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getModalitaNotifica()+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getIdPortale()+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getNumeroOperazione()+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getXmlPaymentStatus()+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getEsitoTransazione()+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getXmlPaymentData()+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getXmlCommitMessage()+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getEsitoNotifica()+delimiter);			
				fileWriter.append(moduloIntegrazionePagamentiStatus.getS2SResponseHtmlStatusCode()+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getS2SResponseMessage()+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getParametriOpzionali1()+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getParametriOpzionali2()+delimiter);
				fileWriter.append(dateformat2.format(moduloIntegrazionePagamentiStatus.getDataUltimoAggiornamento().getTime())+delimiter);
				fileWriter.append(moduloIntegrazionePagamentiStatus.getOperatoreUltimoAggiornamento());
			}
			fileWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void writeFileTFR(ArrayList<TransazioneFreccia> listaTFR) {
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"TFR.csv");
			String headerFile = "TFR_KTFRKTFR, TFR_KTRAKTRA, TFR_CSOCCSOC, TFR_CUTECUTE, TFR_KANEKENT_CON, TFR_KANEKENT_ENT, TFR_CTSECTSE, " +
				"TFR_CISECISE, TFR_CSERCSER, TFR_TBOLTBOL, TFR_DTFRDINT, TFR_DTFRDENO, TFR_DTFRCCAP, TFR_DTFRINDI, " +
				"TFR_CTFRPROV, TFR_DTFRCITT, TFR_CTFRCFIS, TFR_CTFRCSIA, TFR_CTFRIDPG, TFR_CTFRNDOC, TFR_ITFRTOTA, TFR_CTFRIBAN, " +
				"TFR_CTFRNOTE, TFR_GTFRSCAD, TFR_PTFRRATA, TFR_ITFRIMP1, TFR_ITFRIMP2, TFR_ITFRIMP3, TFR_ITFRIMP4, TFR_ITFRIMP5, " +
				"TFR_ITFRIMP6, TFR_ITFRIMP7, TFR_ITFRIMP8, TFR_CTFRCIMP, TFR_CTFRCINT, TFR_CTFRCCOM, TFR_CTFRESEN, " +
				"TFR_CTFRDIVI, TFR_CTFRFREC, TFR_KRENKREN, TFR_PSCAPKEY, TFR_GTFRGAGG, TFR_CTFRCOPE, TFR_PTFRPIUR, TFR_CTFRCIUR, TFR_PTFRPQUN";

			fileWriter.append(headerFile.toString());
			for (TransazioneFreccia transazioneFreccia: listaTFR){
				fileWriter.append("\n");
				fileWriter.append(transazioneFreccia.getChiaveTransazioneDettaglio()+delimiter);
				fileWriter.append(transazioneFreccia.getChiaveTransazioneGenerale()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceSocieta()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceUtente()+delimiter);
				fileWriter.append(transazioneFreccia.getChiaveEnteCon()+delimiter);
				fileWriter.append(transazioneFreccia.getChiaveEnteEnt()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceTipologiaServizio()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceImpostaServizio()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceServizio()+delimiter);
				fileWriter.append(transazioneFreccia.getTipoBollettino()+delimiter);
				fileWriter.append(transazioneFreccia.getDescrizioneIntestatarioContocorrente()+delimiter);
				fileWriter.append(transazioneFreccia.getDenominazioneContribuente()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceCap()+delimiter);
				fileWriter.append(transazioneFreccia.getIndirizzo()+delimiter);
				fileWriter.append(transazioneFreccia.getProvincia()+delimiter);
				fileWriter.append(transazioneFreccia.getCitta()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceFiscale()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceSia()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceIdentificativoPagamento()+delimiter);
				fileWriter.append(transazioneFreccia.getNumeroDocumento()+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getImportoTotaleBollettino())+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceIban()+delimiter);
				fileWriter.append(transazioneFreccia.getMotivoDelPagamento()+delimiter);
				fileWriter.append(dateformat2.format(transazioneFreccia.getDataScadenza())+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getProgressivoRata())+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getImportoOneri())+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getImportoResiduoTotale())+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getImportoResiduoCompenso())+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getImportoResiduoMora())+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getImportoResiduoSpese())+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getImportoResiduoAltreSpese())+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getImportoResiduoTributo())+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getImportoResiduoNotifica())+delimiter);
				fileWriter.append(transazioneFreccia.getCinImporto()+delimiter);
				fileWriter.append(transazioneFreccia.getCinIntermedio()+delimiter);
				fileWriter.append(transazioneFreccia.getCinComplessivo()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceEsenzione()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceDivisa()+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceFreccia()+delimiter);
				fileWriter.append(transazioneFreccia.getChiaveSpedizione()+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getChiavePagamentoScartato())+delimiter);
				fileWriter.append(dateformat2.format(transazioneFreccia.getDataUltimoAggiornamento()));
				fileWriter.append(transazioneFreccia.getOpertoreUltimoAggiornamento()+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getProgBollettino())+delimiter);
				fileWriter.append(transazioneFreccia.getCodiceIUR()+delimiter);
				fileWriter.append(String.valueOf(transazioneFreccia.getProgQuadratura()));	
			}
			fileWriter.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeFileTIC(ArrayList<TransazioneIci> listaTIC){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"TIC.csv");
			String headerFile = "TIC_KTICKTIC, TIC_KTRAKTRA, TIC_CSOCCSOC, TIC_CUTECUTE, TIC_KANEKENT, TIC_CTSECTSE, " +
				"TIC_CISECISE, TIC_CSERCSER, TIC_TBOLTBOL, TIC_NTICNCCP, TIC_DTICDINT, TIC_CTICAREG, TIC_CTICNREG," +
				"TIC_CTICCFIS, TIC_DTICDENO, TIC_DTICDOMI, TIC_DTICINDI, TIC_FTICESTE, TIC_ITICIMOV, TIC_CTICVALU, " +
				"TIC_ITICAGRI, TIC_ITICFABR, TIC_ITICABIT, TIC_ITICALTR, TIC_ITICDCOM, TIC_ITICDSTA, " +
				"TIC_DTICCOUB, TIC_CTICCCAP, TIC_CTICPRCO, TIC_CTICAIMP, TIC_NTICNFAB, TIC_FTICFRAT, TIC_CTICNPRV, TIC_GTICDPRV, " +
				"TIC_FTICRAVV, TIC_KRENKREN, TIC_ITICIMPO, TIC_ITICRIDU, TIC_ITICDETR, TIC_ITICIMPS, TIC_ITICSOPR, " +
				"TIC_ITICPENA, TIC_ITICINTE, TIC_PSCAPKEY, TIC_GTICGAGG, TIC_CTICCOPE, TIC_PTICPIUR, TIC_CTICCIUR, TIC_PTICPQUN";
		
			fileWriter.append(headerFile);
			for (TransazioneIci transazioneIci: listaTIC){
				fileWriter.append("\n");
				fileWriter.append(transazioneIci.getChiaveTransazioneIci()+delimiter);
				fileWriter.append(transazioneIci.getChiaveTransazioneGenerale()+delimiter);
				fileWriter.append(transazioneIci.getCodiceSocieta()+delimiter);
				fileWriter.append(transazioneIci.getCodiceUtente()+delimiter); 
				fileWriter.append(transazioneIci.getChiaveEnte()+delimiter); 
				fileWriter.append(transazioneIci.getCodiceTipologiaServizio()+delimiter);
				fileWriter.append(transazioneIci.getCodiceImpostaServizio()+delimiter);
				fileWriter.append(transazioneIci.getCodiceServizio()+delimiter);
				fileWriter.append(transazioneIci.getTipoBollettino()+delimiter);
				fileWriter.append(transazioneIci.getNumeroContoCorrente()+delimiter);
				fileWriter.append(transazioneIci.getDescrizioneIntestatarioContoCorrente()+delimiter);
				fileWriter.append(transazioneIci.getAnnoRegistrazione()+delimiter);
				fileWriter.append(transazioneIci.getNumeroRegistrazione()+delimiter);
				fileWriter.append(transazioneIci.getCodiceFiscale()+delimiter);
				fileWriter.append(transazioneIci.getDenominazione()+delimiter);
				fileWriter.append(transazioneIci.getDomicilioFiscale()+delimiter);
				fileWriter.append(transazioneIci.getIndirizzoDomicilioFiscale()+delimiter);
				fileWriter.append(transazioneIci.getFlagResidenzaEstero()+delimiter) ;
				fileWriter.append(String.valueOf(transazioneIci.getImportoMovimento())+delimiter);
				fileWriter.append(transazioneIci.getValuta()+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getImportoTerreniAgricoli())+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getImportoAreeFabbricabili())+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getImportoAbitazionePrincipale())+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getImportoAltriFabbricati())+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getImportoDetrazioneComunale())+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getImportoDetrazioneStatale())+delimiter);
				fileWriter.append(transazioneIci.getComuneDiUbicazioneImmobile()+delimiter);
				fileWriter.append(transazioneIci.getCapComuneUbicazioneImmobile()+delimiter);
				fileWriter.append(transazioneIci.getCodiceProvinciaComuneUbicazioneImmobile()+delimiter);
				fileWriter.append(transazioneIci.getAnnoImposta()+delimiter);
				fileWriter.append(transazioneIci.getNumeroFabbricati()+delimiter);
				fileWriter.append(transazioneIci.getFlagRata()+delimiter);
				fileWriter.append(transazioneIci.getNumeroProvvedimento()+delimiter);
				fileWriter.append(dateformat2.format(transazioneIci.getDataProvvedimento())+delimiter);
				fileWriter.append(transazioneIci.getFlagRavvedimento()+delimiter);
				fileWriter.append(transazioneIci.getChiaveSpedizione()+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getImponibileIciPerIscop())+delimiter);
			    fileWriter.append(String.valueOf(transazioneIci.getRiduzionePerIscop())+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getDetrazionePerIscop())+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getImpostaIciSanzioni())+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getSopratassaIciSanzioni())+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getPenaPecuniariaIciSanzioni())+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getInteressiIciSanzioni())+delimiter); 
				fileWriter.append(Long.toString(transazioneIci.getChiavePagamentoScartato())+delimiter);
				fileWriter.append(dateformat.format(transazioneIci.getDataultimoAggiornamento()));
				fileWriter.append(transazioneIci.getOpertoreUltimoAggiornamento()+delimiter);
				fileWriter.append(String.valueOf(transazioneIci.getProgBollettino())+delimiter);
				fileWriter.append(transazioneIci.getCodiceIUR()+delimiter);
				fileWriter.append(Long.toString(transazioneIci.getProgQuadratura()));			
			}
			fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public void writeFileMDC(ArrayList<MovimentoDiCassa> listaMDC){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"MDC.csv");
			String headerFile = "MDC_PMDCPKEY, MDC_CSOCCSOC, MDC_CUTECUTE, MDC_KANEKENT, MDC_PGDCPKEY, MDC_CMDCCONT, MDC_CMDCSTAT, MDC_IMDCDOCN, MDC_CMDCCLIE," +
				"MDC_DMDCIMPO, MDC_CMDCREND, MDC_CMDCREGO, MDC_IMDCPRDO, MDC_CMDCNBOL, MDC_GMDCDMOV, MDC_GMDCDVAL, MDC_CMDCESEC, " +
				"MDC_CMDCCRIF, MDC_CMDCCAUS, MDC_CMDCCOPE, MDC_GMDCDREG, MDC_CMDCNREN, MDC_CMDCTANO, MDC_DMDCSQUA, MDC_GMDCDREN";

			fileWriter.append(headerFile);
			for (MovimentoDiCassa movimentoDiCasa: listaMDC ){
				fileWriter.append("\n");
				fileWriter.append(String.valueOf(movimentoDiCasa.getId())+delimiter);
				fileWriter.append(movimentoDiCasa.getCodiceSocieta()+delimiter);
				fileWriter.append(movimentoDiCasa.getCutecute()+delimiter);
				fileWriter.append(movimentoDiCasa.getSiglaProvincia()+delimiter); 
				fileWriter.append(Long.toString(movimentoDiCasa.getIdGiornale())+delimiter); 
				fileWriter.append(movimentoDiCasa.getConto()+delimiter);
				fileWriter.append(movimentoDiCasa.getStatoSospeso()+delimiter);
				fileWriter.append(movimentoDiCasa.getNumDocumento()+delimiter);
				fileWriter.append(movimentoDiCasa.getCliente()+delimiter);
				fileWriter.append(String.valueOf(movimentoDiCasa.getImporto())+delimiter);
				fileWriter.append(movimentoDiCasa.getRendicontato()+delimiter);
				fileWriter.append(movimentoDiCasa.getRegolarizzato()+delimiter);
				fileWriter.append(String.valueOf(movimentoDiCasa.getProgressivoDoc())+delimiter);
				fileWriter.append(movimentoDiCasa.getNumBolletta()+delimiter);
				fileWriter.append(dateformat2.format(movimentoDiCasa.getDataMovimento().getTime())+delimiter);
				fileWriter.append(dateformat2.format(movimentoDiCasa.getDataValuta().getTime())+delimiter);
				fileWriter.append(movimentoDiCasa.getTipoEsecuzione()+delimiter);
				fileWriter.append(movimentoDiCasa.getCodiceRiferimento()+delimiter);
				fileWriter.append(movimentoDiCasa.getCausale()+delimiter);
				fileWriter.append(movimentoDiCasa.getOperatoreReg()+delimiter);
				fileWriter.append(dateformat2.format(movimentoDiCasa.getDataRegolarizzazione().getTime())+delimiter);
				fileWriter.append(dateformat2.format(movimentoDiCasa.getDataRendicontazione().getTime())+delimiter);
				fileWriter.append(movimentoDiCasa.getTipoAnomalia()+delimiter);
				fileWriter.append(movimentoDiCasa.getSquadratura()+delimiter);
				fileWriter.append(movimentoDiCasa.getNota());	
			}
			fileWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void writeFileGDC(ArrayList<GiornaleDiCassa> listaGDC){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"GDC.csv");
			String headerFile = "GDC_PGDCPKEY, GDC_CSOCCSOC, GDC_CUTECUTE, GDC_KANEKENT, GDC_CGDCPROV, GDC_CGDCIDFL, GDC_CGDCESER, " +
				"GDC_GGDCDTCR, GDC_GGDCDTIN, GDC_GGDCDTFI";

			fileWriter.append(headerFile);
			for (GiornaleDiCassa giornaleDiCassa: listaGDC ){
				fileWriter.append("\n");
				fileWriter.append(String.valueOf(giornaleDiCassa.getId())+delimiter);
				fileWriter.append(giornaleDiCassa.getCodSocieta()+delimiter);
				fileWriter.append(giornaleDiCassa.getCodUtente()+delimiter);
				fileWriter.append(giornaleDiCassa.getCodEnte()+delimiter);
				fileWriter.append(giornaleDiCassa.getProvenienza()+delimiter);
				fileWriter.append(giornaleDiCassa.getIdFlusso()+delimiter);
				fileWriter.append(String.valueOf(giornaleDiCassa.getEsercizio())+delimiter);
				fileWriter.append(dateformat2.format(giornaleDiCassa.getDataGiornale().getTime())+delimiter);
				fileWriter.append(dateformat2.format(giornaleDiCassa.getDataGiornaleDa().getTime())+delimiter);
				fileWriter.append(dateformat2.format(giornaleDiCassa.getDataGiornaleA().getTime()));	
			}
			fileWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void writeFileRMT(ArrayList<AssociazioneTransazioni> listaRMT){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"RMT.csv");
			String headerFile = "RMT_PMDCPKEY, RMT_PRPTPKEY";

			fileWriter.append(headerFile);
			for (AssociazioneTransazioni collegamentoTransazioni: listaRMT ){
				fileWriter.append("\n");
				fileWriter.append(String.valueOf(collegamentoTransazioni.getIdMdc())+delimiter);
				fileWriter.append(String.valueOf(collegamentoTransazioni.getIdNodoScript()));
			}
			fileWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void writeFileRMF(ArrayList<AssociazioneFlussi> listaRMF){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"RMF.csv");
			String headerFile = "RMF_PMDCPKEY, RMF_PQUNPKEY";

			fileWriter.append(headerFile);
			for (AssociazioneFlussi collegamentoFlussi: listaRMF ){
				fileWriter.append("\n");
				fileWriter.append(String.valueOf(collegamentoFlussi.getIdMdc())+delimiter);
				fileWriter.append(String.valueOf(collegamentoFlussi.getIdQuadratura()));
			}
			fileWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeFileATM(ArrayList<TransazioneAtm> listaATM){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"ATM.csv");
			String headerFile = "ATM_KATMKATM, ATM_KTRAKTRA, ATM_FATMFOFF, ATM_FATMFACC, ATM_CATMCRIC, "+
				"ATM_GATMGRIC, ATM_CATMCSIS, ATM_CATMCMET, ATM_GATMGORD, ATM_CATMCTRA, ATM_GATMGTRA, "+
				"ATM_GATMGAGG, ATM_CATMCOPE, ATM_CATMCORD, ATM_CATMIDBA";

			fileWriter.append(headerFile);
			for (TransazioneAtm transazioneAtm: listaATM ){
				fileWriter.append("\n");
				fileWriter.append(transazioneAtm.getChiaveTransazioneInterna()+delimiter);
				fileWriter.append(transazioneAtm.getChiaveTransazione()+delimiter);
				fileWriter.append(transazioneAtm.getFlagOffline()+delimiter);
				fileWriter.append(transazioneAtm.getFlagAcconto()+delimiter);
				fileWriter.append(transazioneAtm.getIdRicevuta()+delimiter);
				fileWriter.append(dateformat2.format(transazioneAtm.getDataRicevuta())+delimiter);
				fileWriter.append(transazioneAtm.getSistema()+delimiter);
				fileWriter.append(transazioneAtm.getMetodo()+delimiter);
				fileWriter.append(dateformat2.format(transazioneAtm.getDataOrdine())+delimiter);
				fileWriter.append(transazioneAtm.getIdTransazione()+delimiter);
				fileWriter.append(dateformat2.format(transazioneAtm.getDataTransazione())+delimiter);
				fileWriter.append(dateformat2.format(transazioneAtm.getDataUltimoAggiornamento())+delimiter);
				fileWriter.append(transazioneAtm.getOperatoreUltimoAggiornamento()+delimiter);
				fileWriter.append(transazioneAtm.getIdOrdine()+delimiter);
				fileWriter.append(transazioneAtm.getIdAutBanca());
			}
			fileWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeFilePOS(ArrayList<POS> listaPOS){
		try {
			FileWriter fileWriter = new FileWriter(outputDir+"POS.csv");
			String headerFile = "POS_KTRAKTRA, POS_CPOSTEID, POS_CPOSAQID, POS_CPOSAUTH, POS_CPOSOPER, "+
				"POS_CPOSDALL, POS_GPOSGAGG, POS_CPOSCOPE";

			fileWriter.append(headerFile);
			for (POS pos: listaPOS ){
				fileWriter.append("\n");
				fileWriter.append(pos.getChiaveTransazione()+delimiter);
				fileWriter.append(pos.getTerminalID()+delimiter);
				fileWriter.append(pos.getAcquirerID()+delimiter);
				fileWriter.append(pos.getAuthorizationCode()+delimiter);
				fileWriter.append(pos.getOperationNumber()+delimiter);
				fileWriter.append(pos.getInformazioniAll()+delimiter);
				fileWriter.append(dateformat2.format(pos.getDataAggiornamento().getTime())+delimiter);
				fileWriter.append(pos.getOperatoreAggiornamento());
			}
			fileWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void invioMailErrori(StringBuffer bodyEmail){
		   
		   try{
				if (svecchiamentoContext.getEmailAdminErrori() == null) {
					printRow(myPrintingKeySV_SYSOUT, "Mail amministratore non configurata nel file di configurazione");
					throw new Exception();
				}
				if (svecchiamentoContext.getWsEmailSenderEndpointURL() == null) {
					printRow(myPrintingKeySV_SYSOUT, "WsEmailSenderEndpointURL non configurato nel file di configurazione");
					throw new Exception();
				}
				String emailAdmin = svecchiamentoContext.getEmailAdminErrori();
				
				System.out.println("Email Amministratore: "+emailAdmin);
				   
			   if(emailAdmin.equals("")){
				   System.out.println("Non è stato possibile inviare la mail di errori all'amministratore");
			   }else{
				   //Invio la mail
				   //Chiamo l'email sender
				   if(!sendMail(emailAdmin, bodyEmail)){
					   throw new Exception("Si è verificato un errore con l'invio della mail");
				   }
			   }
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	   }
	   
	   public boolean sendMail(String emailAdmin, StringBuffer bodyEmail) { 
		   EMailSenderResponse emsRes = null;
			try {
				String endPoint = svecchiamentoContext.getWsEmailSenderEndpointURL();
				String oggetto = "";
				if (svecchiamentoContext.getWsEmailSenderEndpointURL() != null) {
					oggetto = svecchiamentoContext.getWsEmailSenderEndpointURL();
				}
					
				EMailSender emailSender = new EMailSender(endPoint);
					
				emsRes = emailSender.sendEMail(emailAdmin, "", "", oggetto, bodyEmail.toString(), "", "");
				
			} catch (Exception e) {
				try {
					throw new Exception("errore nella creazione dell'email", e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			if (emsRes == null)
				return false;
			else
				return emsRes.getValue().equalsIgnoreCase("OK");		
			
		}
	   
	   private void bodyEmailErrori(){
		   for (String error: errors){
			   sbEmailBody.append(error+"<br>");
		   }
	   }
	
}