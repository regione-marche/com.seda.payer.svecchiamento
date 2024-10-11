package com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.components;

import java.io.FileNotFoundException;
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
import com.seda.payer.core.bean.AbilitaCanalePagamentoTipoServizioEnte;
import com.seda.payer.core.bean.AnagEnte;
import com.seda.payer.core.bean.AnagProvCom;
import com.seda.payer.core.bean.AnagServizi;
import com.seda.payer.core.bean.ApplProf;
import com.seda.payer.core.bean.Bollettino;
import com.seda.payer.core.bean.CanalePagamento;
import com.seda.payer.core.bean.CartaPagamento;
import com.seda.payer.core.bean.Company;
import com.seda.payer.core.bean.ConfigUtenteTipoServizio;
import com.seda.payer.core.bean.ConfigUtenteTipoServizioEnte;
import com.seda.payer.core.bean.Ente;
import com.seda.payer.core.bean.FlussiRen;
import com.seda.payer.core.bean.GatewayPagamento;
import com.seda.payer.core.bean.ModuloIntegrazionePagamenti;
import com.seda.payer.core.bean.ModuloIntegrazionePagamentiNodo;
import com.seda.payer.core.bean.ModuloIntegrazionePagamentiPaymentStatus;
import com.seda.payer.core.bean.NodoSpcRpt;
import com.seda.payer.core.bean.POS;
import com.seda.payer.core.bean.PyUser;
import com.seda.payer.core.bean.QuadraturaNodo;
import com.seda.payer.core.bean.TipologiaServizio;
import com.seda.payer.core.bean.Transazione;
import com.seda.payer.core.bean.TransazioneAtm;
import com.seda.payer.core.bean.TransazioneFreccia;
import com.seda.payer.core.bean.TransazioneIV;
import com.seda.payer.core.bean.TransazioneIci;
import com.seda.payer.core.bean.User;
import com.seda.payer.core.exception.DaoException;
import com.seda.payer.core.riconciliazionemt.bean.GiornaleDiCassa;
import com.seda.payer.core.riconciliazionemt.bean.MovimentoDiCassa;
import com.seda.payer.svecchiamento.model.AssociazioneFlussi;
import com.seda.payer.svecchiamento.model.AssociazioneTransazioni;
import com.seda.payer.svecchiamento.model.AssociazioneUtenteAppl;
import com.seda.payer.svecchiamento.model.SeUser;
import com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.config.SvecchiamentoArchivioOperativoContext;
import com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.config.SvecchiamentoArchivioOperativoResponse;
import com.seda.payer.svecchiamento.util.EMailSender;
import com.seda.payer.svecchiamento.dao.SvecchiamentoDAO;

public class SvecchiamentoArchivioOperativoCore {

	private static String myPrintingKeySV_SYSOUT = "SYSOUT";
	private static final SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
	
	private SvecchiamentoArchivioOperativoContext svecchiamentoContext;

	DataSource datasource;
	DataSource datasourceBK;
	DataSource datasourceSE;
	private ClassPrinting classPrinting;
	String schema;
	String schemaBK;
	String schemaSE;

	private Connection connection;
	private Connection connectionBK;
	private Connection connectionSE;
	private java.sql.Date dataSvecchiamento;

	SvecchiamentoDAO sveccDAO = null;
	SvecchiamentoDAO sveccDAOBK = null;
	SvecchiamentoDAO sveccDAOSE = null;
	SvecchiamentoArchivioOperativoBL sveccBL = null;
	SvecchiamentoArchivioOperativoBL sveccBLBK = null;
	
	String cutecute;
	String codSocPartenza;
	String codSocDestinazione;
	
    private StringBuffer sbEmailBody = new StringBuffer();
	
	ArrayList<String> errors = new ArrayList<String>();
	
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

	String lineSeparator = "============================================================================================";

	public SvecchiamentoArchivioOperativoCore() {
		super();
		welcome();
	}

	public SvecchiamentoArchivioOperativoResponse run(String[] params, ClassPrinting classPrinting ,Logger logger) throws BapException {

		this.classPrinting = classPrinting;

		SvecchiamentoArchivioOperativoResponse svecchiamentoOutputResponse = new SvecchiamentoArchivioOperativoResponse();
		svecchiamentoOutputResponse.setCode("00");
		svecchiamentoOutputResponse.setMessage("");
		
		try {
			preProcess(params);
			processSvecchiamentoArchivioOperativo();
 			printRow( myPrintingKeySV_SYSOUT, "Elaborazione terminata ");
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

	private void processSvecchiamentoArchivioOperativo() throws DaoException, SQLException {
		printRow(myPrintingKeySV_SYSOUT, lineSeparator);
		printRow(myPrintingKeySV_SYSOUT, "Process " + "Svecchiamento archivio operativo "+ "");
		printRow(myPrintingKeySV_SYSOUT, lineSeparator);
		
		try {
			printRow(myPrintingKeySV_SYSOUT, "Aggiornamento tabelle di configurazione nel DB di Backup iniziato");
			allineaTabelleConfigurazione();
			printRow(myPrintingKeySV_SYSOUT, "Aggiornamento tabelle di configurazione nel DB di Backup eseguito");
			printRow(myPrintingKeySV_SYSOUT, "Inserimento transazioni nel DB di Backup");
			printRow(myPrintingKeySV_SYSOUT, "codSocPartenza "+codSocPartenza);
			printRow(myPrintingKeySV_SYSOUT, "codSocDestinazione "+codSocDestinazione);
			boolean result = svecchiamentoTransazioni();
			if(result) {
				System.out.println("Commit!");
				//commit archivio backup
				connectionBK.commit();
				connectionBK.setAutoCommit(true);
				connectionBK.close();
				//commit archivio operativo
				connection.commit();
				connection.setAutoCommit(true);
				connection.close();
				//commit db sicurezza
				connectionSE.commit();
				connectionSE.setAutoCommit(true);
				connectionSE.close();
				
			} else {
				//System.out.println("Rollback!");
				//rollback archivio backup
				connectionBK.rollback();
				connectionBK.setAutoCommit(true);
				connectionBK.close();
				//rollback archivio operativo
				connection.rollback();
				connection.setAutoCommit(true);
				connection.close();
				//rollback db sicurezza
				connectionSE.rollback();
				connectionSE.setAutoCommit(true);
				connectionSE.close();
				//invio mail di errore
				bodyEmailErrori();
				invioMailErrori(sbEmailBody);		
			}
			for(String error: errors){
				System.out.println(error);
			}

		} catch (Exception e) {
			//System.out.println("Rollback!");
			//rollback archivio backup
			connectionBK.rollback();
			connectionBK.setAutoCommit(true);
			connectionBK.close();
			//rollback archivio operativo
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
			//invio mail di errore
			bodyEmailErrori();
			invioMailErrori(sbEmailBody);	
		}
	}

	private boolean svecchiamentoTransazioni() {
		boolean result = true;

		//recupero delle transazioni dal db operativo e copia nel db di backup
		try {

			listaTRA = sveccDAO.getListTRA(dataSvecchiamento, codSocPartenza);
			int countTRA = listaTRA.size();

			printRow(myPrintingKeySV_SYSOUT, "codiceSocietà insert in TRA = " + codSocDestinazione);
			for(Transazione transazione: listaTRA){
				try {
					transazione.setCodiceSocieta(codSocDestinazione);
					sveccDAOBK.insertTRA(transazione);
					countTRA--;
				} catch(Exception e) {
					errors.add("Inserimento TRA "+transazione.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			//se ci sono stati errori durante l'inserimento delle transazioni ritorna perché ci saranno troppi errori a catena
			if(countTRA > 0) {
				rollback();
				return false;
			}
			
			listaREN = sveccDAO.getListREN(dataSvecchiamento, codSocPartenza);
			int countREN = listaREN.size();
			for(FlussiRen flussiRen: listaREN){
				try {
					if(cutecute == null)
						cutecute = flussiRen.getCodiceUtente();
					flussiRen.setCodiceSocieta(codSocDestinazione);
					sveccDAOBK.insertREN(flussiRen);
					countREN--;
				} catch(Exception e) {
					errors.add("Inserimento REN "+flussiRen.getChiaveRendicontazione()+" - "+e.getMessage());
				}
			}
			if(countREN > 0) result = false;
			
			listaRPT = sveccDAO.getListRPT(dataSvecchiamento, codSocPartenza);
			int countRPT = listaRPT.size();
			for(NodoSpcRpt nodoSpcRpt: listaRPT){
				try {
					nodoSpcRpt.setCodSocieta(codSocDestinazione);
					sveccDAOBK.insertRPT(nodoSpcRpt);
					countRPT--;
				} catch(Exception e) {
					errors.add("Inserimento RPT "+nodoSpcRpt.getId()+" - "+e.getMessage());
				}
			}
			if(countRPT > 0) result = false;
			
			listaQUN = sveccDAO.getListQUN(dataSvecchiamento, codSocPartenza);
			int countQUN = listaQUN.size();
			for(QuadraturaNodo quadraturaNodo: listaQUN){	
				try {
					quadraturaNodo.setCodSocieta(codSocDestinazione);
					sveccDAOBK.insertQUN(quadraturaNodo);
					countQUN--;
				} catch(Exception e) {
					errors.add("Inserimento QUN "+quadraturaNodo.getKeyQuadratura()+" - "+e.getMessage());
				}
			}
			if(countQUN > 0) result = false;
			
			listaTFR = sveccDAO.getListTFR(dataSvecchiamento, codSocPartenza);
			int countTFR = listaTFR.size();
			for(TransazioneFreccia transazioneFreccia: listaTFR){				
				try {
					transazioneFreccia.setCodiceSocieta(codSocDestinazione);
					sveccBLBK.insertTFR(transazioneFreccia);
					countTFR--;
				} catch(Exception e) {
					errors.add("Inserimento TFR "+transazioneFreccia.getChiaveTransazioneDettaglio()+" - "+e.getMessage());
				}
			}
			if(countTFR > 0) result = false;
			
			listaTDT = sveccDAO.getListTDT(dataSvecchiamento, codSocPartenza);
			int countTDT = listaTDT.size();
			for(TransazioneIV transazioneIV: listaTDT){
				try {
					transazioneIV.setCodiceSocieta(codSocDestinazione);
					sveccBLBK.insertTDT(transazioneIV);
					countTDT--;
				} catch(Exception e) {
					errors.add("Inserimento TDT "+transazioneIV.getChiaveTransazioneDettaglio()+" - "+e.getMessage());
				}
			}
			if(countTDT > 0) result = false;
			
			listaTIC = sveccDAO.getListTIC(dataSvecchiamento, codSocPartenza);
			int countTIC = listaTIC.size();
			for(TransazioneIci transazioneIci: listaTIC){
				try {
					transazioneIci.setCodiceSocieta(codSocDestinazione);
					sveccBLBK.insertTIC(transazioneIci);
					countTIC--;
				} catch(Exception e) {
					errors.add("Inserimento TIC "+transazioneIci.getChiaveTransazioneIci()+" - "+e.getMessage());
				}
			}
			if(countTIC > 0) result = false;
			
			listaMIN = sveccDAO.getListMIN(dataSvecchiamento, codSocPartenza);
			int countMIN = listaMIN.size();
			for(ModuloIntegrazionePagamentiNodo moduloIntegrazionePagamentiNodo: listaMIN){
				try {
					moduloIntegrazionePagamentiNodo.setCodiceSocieta(codSocDestinazione);
					sveccBLBK.insertMIN(moduloIntegrazionePagamentiNodo);
					countMIN--;
				} catch(Exception e) {
					errors.add("Inserimento MIN "+moduloIntegrazionePagamentiNodo.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			if(countMIN > 0) result = false;
			
			listaMIP = sveccDAO.getListMIP(dataSvecchiamento, codSocPartenza);
			int countMIP = listaMIP.size();
			for(ModuloIntegrazionePagamenti moduloIntegrazionePagamenti: listaMIP){
				try {
					moduloIntegrazionePagamenti.setCodiceSocieta(codSocDestinazione);
					sveccBLBK.insertMIP(moduloIntegrazionePagamenti);
					countMIP--;
				} catch(Exception e) {
					errors.add("Inserimento MIP "+moduloIntegrazionePagamenti.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			if(countMIP > 0) result = false;
			
			listaMPS = sveccDAO.getListMPS(dataSvecchiamento, codSocPartenza);
			int countMPS = listaMPS.size();
			for(ModuloIntegrazionePagamentiPaymentStatus moduloIntegrazionePagamentiPaymentStatus: listaMPS){
				try {
					sveccDAOBK.insertMPS(moduloIntegrazionePagamentiPaymentStatus);
					countMPS--;
				} catch(Exception e) {
					errors.add("Inserimento MPS "+moduloIntegrazionePagamentiPaymentStatus.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			if(countMPS > 0) result = false;
			
			listaATM = sveccDAO.getListATM(dataSvecchiamento, codSocPartenza);
			int countATM = listaATM.size();
			for(TransazioneAtm transazioneAtm: listaATM){
				try {
					sveccBLBK.insertATM(transazioneAtm);
					countATM--;
				} catch(Exception e) {
					errors.add("Inserimento ATM "+transazioneAtm.getChiaveTransazioneInterna()+" - "+e.getMessage());
				}
			}
			if(countATM > 0) result = false;
			
			listaPOS = sveccDAO.getListPOS(dataSvecchiamento, codSocPartenza);
			int countPOS = listaPOS.size();
			for(POS pos: listaPOS){
				try {					
					sveccBLBK.insertPOS(pos);
					countPOS--;
				} catch(Exception e) {
					errors.add("Inserimento POS "+pos.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			if(countPOS > 0) result = false;
			
			listaGDC = sveccDAO.getListGDC(dataSvecchiamento, codSocPartenza);
			int countGDC = listaGDC.size();
			for(GiornaleDiCassa giornaleDiCassa: listaGDC){
				try {
					giornaleDiCassa.setCodSocieta(codSocDestinazione);
					sveccDAOBK.insertGDC(giornaleDiCassa);
					countGDC--;
				} catch(Exception e) {
					errors.add("Inserimento GDC "+giornaleDiCassa.getId()+" - "+e.getMessage());
				}
			}
			if(countGDC > 0) result = false;
			
			listaMDC = sveccDAO.getListMDC(dataSvecchiamento, codSocPartenza);
			int countMDC = listaMDC.size();
			for(MovimentoDiCassa movimentoDiCassa: listaMDC){
				try {
					movimentoDiCassa.setCodiceSocieta(codSocDestinazione);
					sveccDAOBK.insertMDC(movimentoDiCassa);
					countMDC--;
				} catch(Exception e) {
					errors.add("Inserimento MDC "+movimentoDiCassa.getId()+" - "+e.getMessage());
				}
			}
			if(countMDC > 0) result = false;
			
			listaRMT = sveccDAO.getListRMT(dataSvecchiamento, codSocPartenza);
			int countRMT = listaRMT.size();
			for(AssociazioneTransazioni associazioneTransazioni: listaRMT){
				try {
					sveccDAOBK.insertRMT(associazioneTransazioni);
					countRMT--;
				} catch(Exception e) {
					errors.add("Inserimento RMT "+associazioneTransazioni.getIdMdc()+" - "+e.getMessage());
				}
			}
			if(countRMT > 0) result = false;
			
			listaRMF = sveccDAO.getListRMF(dataSvecchiamento, codSocPartenza);
			int countRMF = listaRMF.size();
			for(AssociazioneFlussi associazioneFlussi: listaRMF){
				try {
					sveccDAOBK.insertRMF(associazioneFlussi);
					countRMF--;
				} catch(Exception e) {
					errors.add("Inserimento RMF "+associazioneFlussi.getIdMdc()+" - "+e.getMessage());
				}
			}
			if(countRMF > 0) result = false;
			
		} catch(Exception e) {
			errors.add("Errore svecchiamento transazioni "+e.getMessage());
			e.printStackTrace();
		}
		//eliminazione delle transazioni dal db operativo
		if(errors.isEmpty() && result) {
			printRow(myPrintingKeySV_SYSOUT, "Cancellazione transazioni nell'Archivio Operativo");
			
			for(AssociazioneFlussi associazioneFlussi: listaRMF){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteRMF");
					sveccDAO.deleteRMF(associazioneFlussi);
				} catch(Exception e) {
					errors.add("Cancellazione RMF "+associazioneFlussi.getIdMdc()+" - "+e.getMessage());
				}
			}
			for(AssociazioneTransazioni associazioneTransazioni: listaRMT){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteRMT");
					sveccDAO.deleteRMT(associazioneTransazioni);
				} catch(Exception e) {
					errors.add("Cancellazione RMT "+associazioneTransazioni.getIdMdc()+" - "+e.getMessage());
				}
			}
			for(MovimentoDiCassa movimentoDiCassa: listaMDC){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteMDC");
					sveccDAO.deleteMDC(movimentoDiCassa);
				} catch(Exception e) {
					errors.add("Cancellazione MDC "+movimentoDiCassa.getId()+" - "+e.getMessage());
				}
			}
			for(GiornaleDiCassa giornaleDiCassa: listaGDC){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteGDC");
					sveccDAO.deleteGDC(giornaleDiCassa);
				} catch(Exception e) {
					errors.add("Cancellazione GDC "+giornaleDiCassa.getId()+" - "+e.getMessage());
				}
			}
			for(POS pos: listaPOS){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deletePOS");
					sveccDAO.deletePOS(pos);
				} catch(Exception e) {
					errors.add("Cancellazione POS "+pos.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			for(TransazioneAtm transazioneAtm: listaATM){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteATM");
					sveccDAO.deleteATM(transazioneAtm);
				} catch(Exception e) {
					errors.add("Cancellazione ATM "+transazioneAtm.getChiaveTransazioneInterna()+" - "+e.getMessage());
				}
			}
			for(ModuloIntegrazionePagamentiPaymentStatus moduloIntegrazionePagamentiPaymentStatus: listaMPS){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteMPS");
					sveccDAO.deleteMPS(moduloIntegrazionePagamentiPaymentStatus);
				} catch(Exception e) {
					errors.add("Cancellazione MPS "+moduloIntegrazionePagamentiPaymentStatus.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			for(ModuloIntegrazionePagamenti moduloIntegrazionePagamenti: listaMIP){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteMIP");
					sveccDAO.deleteMIP(moduloIntegrazionePagamenti);
				} catch(Exception e) {
					errors.add("Cancellazione MIP "+moduloIntegrazionePagamenti.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			for(ModuloIntegrazionePagamentiNodo moduloIntegrazionePagamentiNodo: listaMIN){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteMIN");
					sveccDAO.deleteMIN(moduloIntegrazionePagamentiNodo);
				} catch(Exception e) {
					errors.add("Cancellazione MIN "+moduloIntegrazionePagamentiNodo.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			for(TransazioneIci transazioneIci: listaTIC){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteTIC");
					sveccDAO.deleteTIC(transazioneIci);
				} catch(Exception e) {
					errors.add("Cancellazione TIC "+transazioneIci.getChiaveTransazioneIci()+" - "+e.getMessage());
				}
			}
			for(TransazioneIV transazioneIV: listaTDT){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteTDT");
					sveccDAO.deleteTDT(transazioneIV);
				} catch(Exception e) {
					errors.add("Cancellazione TDT "+transazioneIV.getChiaveTransazioneDettaglio()+" - "+e.getMessage());
				}
			}
			for(TransazioneFreccia transazioneFreccia: listaTFR){				
				try {
					sveccDAO.deleteTFR(transazioneFreccia);
				} catch(Exception e) {
					errors.add("Cancellazione TFR "+transazioneFreccia.getChiaveTransazioneDettaglio()+" - "+e.getMessage());
				}
			}
			for(QuadraturaNodo quadraturaNodo: listaQUN){	
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteQUN");
					sveccDAO.deleteQUN(quadraturaNodo);
				} catch(Exception e) {
					errors.add("Cancellazione QUN "+quadraturaNodo.getKeyQuadratura()+" - "+e.getMessage());
				}
			}
			for(NodoSpcRpt nodoSpcRpt: listaRPT){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteRPT");
					sveccDAO.deleteRPT(nodoSpcRpt);
				} catch(Exception e) {
					errors.add("Cancellazione RPT "+nodoSpcRpt.getId()+" - "+e.getMessage());
				}
			}
			for(FlussiRen flussiRen: listaREN){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteREN");
					sveccDAO.deleteREN(flussiRen);
				} catch(Exception e) {
					errors.add("Cancellazione REN "+flussiRen.getChiaveRendicontazione()+" - "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "Cancellazione dati in tabelle Archivio Operativo eseguita");
			
			//eliminazione del trigger PYTRAT3
			try {
				printRow(myPrintingKeySV_SYSOUT, "Inizio eleiminazione Trigger PYTRAT3");
				sveccDAO.dropTrigger();
			} catch(Exception e) {
				errors.add("Cancellazione TRIGGER PYTRAT3 "+e.getMessage());
			}

			for(Transazione transazione: listaTRA){
				try {
					printRow(myPrintingKeySV_SYSOUT, "Cancellazione deleteTRA");
					sveccDAO.deleteTRA(transazione);
				} catch(Exception e) {
					errors.add("Cancellazione TRA "+transazione.getChiaveTransazione()+" - "+e.getMessage());
				}
			}
			
			//ricreo il trigger PYTRAT3 cancellato prima
			printRow(myPrintingKeySV_SYSOUT, "Ricreazione Trigger PYTRAT3");
			try {
				sveccDAO.createTrigger(schema);
			} catch(Exception e) {
				e.printStackTrace();
				errors.add("Creazione TRIGGER PYTRAT3 "+e.getMessage());
			}
			printRow(myPrintingKeySV_SYSOUT, "Cancellazione terminata correttamente");

		} else {
			rollback();
		}
		
		if(errors.isEmpty()) {
			return true;
		}else {
			
			printRow(myPrintingKeySV_SYSOUT, "Si è verificato un errore. Annullamento operazione di svecchiamento.");
			return false;
		}	
	}

	private void allineaTabelleConfigurazione() {
		// svuotare le tabelle di configurazione sul DB di backup
		// prima recupero i dati e poi cancello una riga alla volta tramite chiave
		// alcune righe potrebbero non essere cancellabili perché collegate alle transazioni
		// queste righe verranno aggiornate successivamente
		printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in DBSVEC");
		try {
			ArrayList<PyUser> listaUSR = sveccDAOBK.getListUSR();
			for(PyUser user: listaUSR){
				//printRow(myPrintingKeySV_SYSOUT, "sveccBLBK.deleteUSR per user" + user);
				sveccBLBK.deleteUSR(user);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaUSR");
			ArrayList<AssociazioneUtenteAppl> listaMNA = sveccDAOBK.getListMNA();
			for(AssociazioneUtenteAppl associazioneUtenteApplser: listaMNA){
				sveccDAOBK.deleteMNA(associazioneUtenteApplser);
			}
			ArrayList<ApplProf> listaPRF = sveccDAOBK.getListPRF();
			for(ApplProf applProf: listaPRF){
				sveccBLBK.deletePRF(applProf);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaPRF");
			ArrayList<GatewayPagamento> listaGTW = sveccDAOBK.getListGTW();
			
			for(GatewayPagamento gatewayPagamento: listaGTW){
				printRow(myPrintingKeySV_SYSOUT, "sveccBLBK.deleteGTW per" + gatewayPagamento.getChiaveGateway());
				sveccBLBK.deleteGTW(gatewayPagamento);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaGTW");
			ArrayList<ConfigUtenteTipoServizioEnte> listaCFE = sveccDAOBK.getListCFE();
			for(ConfigUtenteTipoServizioEnte configUtenteTipoServizioEnte: listaCFE){
				sveccBLBK.deleteCFE(configUtenteTipoServizioEnte);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaCFE");
			ArrayList<ConfigUtenteTipoServizio> listaCFS = sveccDAOBK.getListCFS();
			for(ConfigUtenteTipoServizio configUtenteTipoServizio: listaCFS){
				sveccBLBK.deleteCFS(configUtenteTipoServizio);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaCFS");
			
			
			ArrayList<Bollettino> listaBOL = sveccDAOBK.getListBOL();
			for(Bollettino bollettino: listaBOL){
				sveccBLBK.deleteBOL(bollettino);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaBOL");
			ArrayList<AbilitaCanalePagamentoTipoServizioEnte> listaCES = sveccDAOBK.getListCES();
			for(AbilitaCanalePagamentoTipoServizioEnte AbilitaCanalePagamentoTipoServizioEnte: listaCES){
				sveccBLBK.deleteCES(AbilitaCanalePagamentoTipoServizioEnte);
			}
			
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaCES");
			
			ArrayList<CanalePagamento> listaCAN = sveccDAOBK.getListCAN();
			for(CanalePagamento canalePagamento: listaCAN){
				sveccBLBK.deleteCAN(canalePagamento);
			}
					printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaCAN");
					
			ArrayList<CartaPagamento> listaCAR = sveccDAOBK.getListCAR();
			for(CartaPagamento cartaPagamento: listaCAR){
				sveccBLBK.deleteCAR(cartaPagamento);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaCAR");
			
			ArrayList<AnagServizi> listaSER = sveccDAOBK.getListSER();
			for(AnagServizi anagServizi: listaSER){
				sveccBLBK.deleteSER(anagServizi);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaSER");
			
			ArrayList<Ente> listaENT = sveccDAOBK.getListENT();
			for(Ente ente: listaENT){
				sveccBLBK.deleteENT(ente);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaENT");
			
			
			ArrayList<User> listaUTE = sveccDAOBK.getListUTE();
			for(User user: listaUTE){
				sveccBLBK.deleteUTE(user);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaUTE");
			
			ArrayList<AnagEnte> listaANE = sveccDAOBK.getListANE();
			for(AnagEnte anagEnte: listaANE){
				sveccBLBK.deleteANE(anagEnte);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle in listaANE");
			
			
			ArrayList<AnagProvCom> listaAPC = sveccDAOBK.getListAPC();
			for(AnagProvCom anagProvCom: listaAPC){
				sveccBLBK.deleteAPC(anagProvCom);
			}
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle terminata in listaAPC");
			
			ArrayList<TipologiaServizio> listaTSE = sveccDAOBK.getListTSE();
			for(TipologiaServizio tipologiaServizio: listaTSE){
				sveccBLBK.deleteTSE(tipologiaServizio);
			}
			
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle terminata in listaTSE");
			
			ArrayList<Company> listaSOC = sveccDAOBK.getListSOC();
			for(Company company: listaSOC){
				sveccBLBK.deleteSOC(company);
			}
			
			printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle terminata in listaSOC");
			
		} catch (Exception e) {
			System.out.println("Errore svuotamento tabelle configurazione");
			System.out.println(e);
			System.out.println(" ");
		}
		printRow(myPrintingKeySV_SYSOUT, "cancellazione tabelle terminata in DBSVEC");
		
		// inserimento o aggiornamento dentro il DB backup
		printRow(myPrintingKeySV_SYSOUT, "inserimento dati in DBSVEC");
		try {
			ArrayList<Company> listaSOC = sveccDAO.getListSOC();
			for(Company company: listaSOC){
				try {
					if(company.getCompanyCode().equals(codSocPartenza)) {
						company.setCompanyCode(codSocDestinazione);
					}
					sveccBLBK.insertSOC(company);
				} catch(Exception e) {
					errors.add("Inserimento SOC: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati listaSOC in DBSVEC fatto");
			ArrayList<TipologiaServizio> listaTSE = sveccDAO.getListTSE();
			for(TipologiaServizio tipologiaServizio: listaTSE){
				try {
					if(tipologiaServizio.getCompany().getCompanyCode().equals(codSocPartenza))
						tipologiaServizio.getCompany().setCompanyCode(codSocDestinazione);
					sveccBLBK.insertTSE(tipologiaServizio);
				} catch(Exception e) {
					errors.add("Inserimento TSE: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaTSE fatto");
			ArrayList<AnagProvCom> listaAPC = sveccDAO.getListAPC();
			for(AnagProvCom anagProvCom: listaAPC){
				try {
					sveccBLBK.insertAPC(anagProvCom);
				} catch(Exception e) {
					errors.add("Inserimento APC: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaAPC fatto");
			ArrayList<AnagEnte> listaANE = sveccDAO.getListANE();
			for(AnagEnte anagEnte: listaANE){
				try {
					boolean updated = sveccBLBK.updateANE(anagEnte);
					if (!updated) sveccDAOBK.insertANE(anagEnte);
				} catch(Exception e) {
					errors.add("Inserimento ANE: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaANE fatto");
			ArrayList<User> listaUTE = sveccDAO.getListUTE();
			for(User user: listaUTE){
				try {
					if(user.getCompany().getCompanyCode().equals(codSocPartenza))
						user.getCompany().setCompanyCode(codSocDestinazione);
					sveccBLBK.insertUTE(user);
				} catch(Exception e) {
					errors.add("Inserimento UTE: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati listaUTE fatto");
			ArrayList<Ente> listaENT = sveccDAO.getListENT();
			for(Ente ente: listaENT){
				try {
					if(ente.getUser().getCompany().getCompanyCode().equals(codSocPartenza))
						ente.getUser().getCompany().setCompanyCode(codSocDestinazione);
					sveccBLBK.insertENT(ente);
				} catch(Exception e) {
					errors.add("Inserimento ENT: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaUTE fatto");
			ArrayList<AnagServizi> listaSER = sveccDAO.getListSER();
			for(AnagServizi anagServizi: listaSER){
				try {
					sveccBLBK.insertSER(anagServizi);
				} catch(Exception e) {
					errors.add("Inserimento SER: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaSER fatto");
			ArrayList<CartaPagamento> listaCAR = sveccDAO.getListCAR();
			for(CartaPagamento cartaPagamento: listaCAR){
				try {
					sveccBLBK.insertCAR(cartaPagamento);
				} catch(Exception e) {
					errors.add("Inserimento CAR: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaCAR fatto");
			ArrayList<CanalePagamento> listaCAN = sveccDAO.getListCAN();
			for(CanalePagamento canalePagamento: listaCAN){
				try {
					sveccBLBK.insertCAN(canalePagamento);
				} catch(Exception e) {
					errors.add("Inserimento CAN: "+e.getMessage());
				}
			}
			ArrayList<AbilitaCanalePagamentoTipoServizioEnte> listaCES = sveccDAO.getListCES();
			for(AbilitaCanalePagamentoTipoServizioEnte abilitaCanalePagamentoTipoServizioEnte: listaCES){
				try {
					if(abilitaCanalePagamentoTipoServizioEnte.getEnte().getUser().getCompany().getCompanyCode().equals(codSocPartenza))
						abilitaCanalePagamentoTipoServizioEnte.getEnte().getUser().getCompany().setCompanyCode(codSocDestinazione);
					sveccBLBK.insertCES(abilitaCanalePagamentoTipoServizioEnte);
				} catch(Exception e) {
					errors.add("Inserimento CES: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaCES fatto");
			ArrayList<Bollettino> listaBOL = sveccDAO.getListBOL();
			for(Bollettino Bollettino: listaBOL){
				try {
					sveccBLBK.insertBOL(Bollettino);
				} catch(Exception e) {
					errors.add("Inserimento BOL: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaBOL fatto");
			ArrayList<ConfigUtenteTipoServizio> listaCFS = sveccDAO.getListCFS();
			for(ConfigUtenteTipoServizio configUtenteTipoServizio: listaCFS){
				try {
					if(configUtenteTipoServizio.getUser().getCompany().getCompanyCode().equals(codSocPartenza))
						configUtenteTipoServizio.getUser().getCompany().setCompanyCode(codSocDestinazione);
					sveccBLBK.insertCFS(configUtenteTipoServizio);
				} catch(Exception e) {
					errors.add("Inserimento CFS: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaCFS fatto");
			ArrayList<ConfigUtenteTipoServizioEnte> listaCFE = sveccDAO.getListCFE();
			for(ConfigUtenteTipoServizioEnte configUtenteTipoServizioEnte: listaCFE){
				try {
					if(configUtenteTipoServizioEnte.getEnte().getUser().getCompany().getCompanyCode().equals(codSocPartenza))
						configUtenteTipoServizioEnte.getEnte().getUser().getCompany().setCompanyCode(codSocDestinazione);
					sveccBLBK.insertCFE(configUtenteTipoServizioEnte);
				} catch(Exception e) {
					errors.add("Inserimento CFE: "+e.getMessage());
				}
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaCFE fatto");
			ArrayList<GatewayPagamento> listaGTW = sveccDAO.getListGTW();
			for(GatewayPagamento gatewayPagamento: listaGTW){
				try {
					if(gatewayPagamento.getUser().getCompany().getCompanyCode().equals(codSocPartenza))
						gatewayPagamento.getUser().getCompany().setCompanyCode(codSocDestinazione);
					boolean updated = sveccBLBK.updateGTW(gatewayPagamento);
					if (!updated) sveccDAOBK.insertGTW(gatewayPagamento);
				} catch(Exception e) {
					errors.add("Inserimento GTW: "+e.getMessage());
				}
			}
			ArrayList<ApplProf> listaPRF = sveccDAO.getListPRF();
			for(ApplProf gatewayPagamento: listaPRF){
				try {
					sveccBLBK.insertPRF(gatewayPagamento);
				} catch(Exception e) {
					errors.add("Inserimento PRF: "+e.getMessage());
				}
			}
			ArrayList<PyUser> listaUSR = sveccDAO.getListUSR();
			ArrayList<String> listaSEUSR = new ArrayList<String>(); // lista degli user da aggiungere nella tabella sicurezza
			for(PyUser user: listaUSR){
				try {
					if(user.getCodiceSocieta().equals(codSocPartenza))
						user.setCodiceSocieta(codSocDestinazione);
					boolean updated = sveccBLBK.updateUSR(user);
					if (!updated) sveccDAOBK.insertUSR(user);
				} catch(Exception e) {
					errors.add("Inserimento USR: "+e.getMessage());
				}
				if(!"PYCO".equals(user.getUserProfile()) && !listaSEUSR.contains(user.getUserName())) {
					listaSEUSR.add(user.getUserName());
				}			
			}
			printRow(myPrintingKeySV_SYSOUT, "inserimento dati in listaUSR fatto");
			
			ArrayList<AssociazioneUtenteAppl> listaMNA = sveccDAO.getListMNA();
			for(AssociazioneUtenteAppl associazioneUtenteAppl: listaMNA){
				try {
					sveccDAOBK.insertMNA(associazioneUtenteAppl);
				} catch(Exception e) {
					errors.add("Inserimento MNA: "+e.getMessage());
				}
			}
			// db security
			try {
				sveccDAOSE.insertSESOC(codSocDestinazione, "Svecchiamento2");
			} catch(Exception e) {
				errors.add("Inserimento SESOC: "+e.getMessage());
			}
			for(String user: listaSEUSR){
				try {
					SeUser seUser = sveccDAOSE.selectSEUSR(user, codSocPartenza);
					if(seUser != null) {
						SeUser seUser2 = sveccDAOSE.selectSEUSR(user, codSocDestinazione);
						if(seUser2 != null){
							sveccDAOSE.updateSEUSR(seUser, codSocDestinazione);
						}else {
							sveccDAOSE.insertSEUSR(seUser, codSocDestinazione);
						}
					}
				} catch(Exception e) {
					errors.add("Inserimento SEUSR: "+e.getMessage());
				}
			}

		} catch(Exception e) {
			printRow(myPrintingKeySV_SYSOUT, "Errore in fase di allineamento" + e.getMessage());
			e.printStackTrace();
			errors.add("Aggiornamento tabelle di configurazione: "+e.getMessage());
		} catch (Throwable e) {
			printRow(myPrintingKeySV_SYSOUT, "Errore in fase di allineamento" + e.getMessage());
			e.printStackTrace();
			errors.add("Aggiornamento tabelle di configurazione: "+e.getMessage());
		}

	}

	private void welcome() {
		StringBuffer w = new StringBuffer("");    	
		w.append("" +" Svecchiamento archivio operativo "+ "\n");
		w.append(System.getProperties().get("java.specification.vendor") + " ");
		w.append(System.getProperties().get("java.version") + "\n");
		w.append("JAVA HOME : "+System.getProperties().get("java.home") + "\n");
		w.append("(C) Copyright 2024 Maggioli Spa"  + "\n");
		w.append("\n");
		printRow(myPrintingKeySV_SYSOUT,w.toString());
		w=null;

		printRow(myPrintingKeySV_SYSOUT,lineSeparator);
		printRow(myPrintingKeySV_SYSOUT,"Avvio " + "Svecchiamento archivio operativo " + "");
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
		svecchiamentoContext = new SvecchiamentoArchivioOperativoContext();
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
		
		if (svecchiamentoContext.getCodSocPartenza() == null) {
			printRow(myPrintingKeySV_SYSOUT, "CodSocPartenza  non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getCodSocDestinazione() == null) {
			printRow(myPrintingKeySV_SYSOUT, "CodSocDestinazione non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcDriver() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCDriver non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcUrl() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCUrl non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcUser() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCUSer non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcPassword() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCPassword non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getSchema() == null) {
			printRow(myPrintingKeySV_SYSOUT, "Datasource Schema non configurato");
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
		if (svecchiamentoContext.getJdbcDriverSE() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCDriverSE non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcUrlSE() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCUrlSE non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcUserSE() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCUSerSE non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getJdbcPasswordSE() == null) {
			printRow(myPrintingKeySV_SYSOUT, "JDBCPasswordSE non configurato");
			throw new Exception();
		}
		if (svecchiamentoContext.getSchemaSE() == null) {
			printRow(myPrintingKeySV_SYSOUT, "DatasourceSE Schema non configurato");
			throw new Exception();
		}
		
		if (this.codSocPartenza == null) {
			this.codSocPartenza = svecchiamentoContext.getCodSocPartenza().trim();
		}
		if (this.codSocDestinazione == null) {
			this.codSocDestinazione = svecchiamentoContext.getCodSocDestinazione().trim();
		}
		if (this.datasource == null) {
			DataSourceFactoryImpl dataSourceFactory = new DataSourceFactoryImpl();
			Properties dsProperties = new Properties();
			dsProperties.put(DAOHelper.JDBC_DRIVER, svecchiamentoContext.getJdbcDriver());
			dsProperties.put(DAOHelper.JDBC_URL, svecchiamentoContext.getJdbcUrl());
			dsProperties.put(DAOHelper.JDBC_USER, svecchiamentoContext.getJdbcUser());
			dsProperties.put(DAOHelper.JDBC_PASSWORD, svecchiamentoContext.getJdbcPassword());
			//dsProperties.put("autocommit", "true");
			dataSourceFactory.setProperties(dsProperties);
			this.datasource = dataSourceFactory.getDataSource();
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
		if (this.datasourceSE == null) {
			DataSourceFactoryImpl dataSourceFactory = new DataSourceFactoryImpl();
			Properties dsProperties = new Properties();
			dsProperties.put(DAOHelper.JDBC_DRIVER, svecchiamentoContext.getJdbcDriverSE());
			dsProperties.put(DAOHelper.JDBC_URL, svecchiamentoContext.getJdbcUrlSE());
			dsProperties.put(DAOHelper.JDBC_USER, svecchiamentoContext.getJdbcUserSE());
			dsProperties.put(DAOHelper.JDBC_PASSWORD, svecchiamentoContext.getJdbcPasswordSE());
			//dsProperties.put("autocommit", "true");
			dataSourceFactory.setProperties(dsProperties);
			this.datasourceSE = dataSourceFactory.getDataSource();
		}
		if (schema == null) {
			this.schema = svecchiamentoContext.getSchema();
		}
		if (schemaBK == null){
			this.schemaBK = svecchiamentoContext.getSchemaBK();
		}
		if (schemaSE == null){
			this.schemaSE = svecchiamentoContext.getSchemaSE();
		}

		connection = this.datasource.getConnection();
		connection.setAutoCommit(false);
		
		connectionBK = this.datasourceBK.getConnection();
		connectionBK.setAutoCommit(false);
		
		connectionSE = this.datasourceSE.getConnection();
		connectionSE.setAutoCommit(false);

		sveccDAO = new SvecchiamentoDAO(connection, schema);
		sveccDAOBK = new SvecchiamentoDAO(connectionBK, schemaBK);
		sveccDAOSE = new SvecchiamentoDAO(connectionSE, schemaSE);
		
		sveccBL = new SvecchiamentoArchivioOperativoBL (connection, schema);
		sveccBLBK = new SvecchiamentoArchivioOperativoBL (connectionBK, schemaBK);
	}

	private void rollback() {
		printRow(myPrintingKeySV_SYSOUT, "Rollback!");
		for(AssociazioneFlussi associazioneFlussi: listaRMF){
			try {
				sveccDAOBK.deleteRMF(associazioneFlussi);
			} catch(Exception e) {
				errors.add("Rollback RMF "+associazioneFlussi.getIdMdc()+" - "+e.getMessage());
			}
		}
		for(AssociazioneTransazioni associazioneTransazioni: listaRMT){
			try {
				sveccDAOBK.deleteRMT(associazioneTransazioni);
			} catch(Exception e) {
				errors.add("Rollback RMT "+associazioneTransazioni.getIdMdc()+" - "+e.getMessage());
			}
		}
		for(MovimentoDiCassa movimentoDiCassa: listaMDC){
			try {
				sveccDAOBK.deleteMDC(movimentoDiCassa);
			} catch(Exception e) {
				errors.add("Rollback MDC "+movimentoDiCassa.getId()+" - "+e.getMessage());
			}
		}
		for(GiornaleDiCassa giornaleDiCassa: listaGDC){
			try {
				sveccDAOBK.deleteGDC(giornaleDiCassa);
			} catch(Exception e) {
				errors.add("Rollback GDC "+giornaleDiCassa.getId()+" - "+e.getMessage());
			}
		}
		for(POS pos: listaPOS){
			try {
				sveccDAOBK.deletePOS(pos);
			} catch(Exception e) {
				errors.add("Rollback POS "+pos.getChiaveTransazione()+" - "+e.getMessage());
			}
		}
		for(TransazioneAtm transazioneAtm: listaATM){
			try {
				sveccDAOBK.deleteATM(transazioneAtm);
			} catch(Exception e) {
				errors.add("Rollback ATM "+transazioneAtm.getChiaveTransazioneInterna()+" - "+e.getMessage());
			}
		}
		for(ModuloIntegrazionePagamentiPaymentStatus moduloIntegrazionePagamentiPaymentStatus: listaMPS){
			try {
				sveccDAOBK.deleteMPS(moduloIntegrazionePagamentiPaymentStatus);
			} catch(Exception e) {
				errors.add("Rollback MPS "+moduloIntegrazionePagamentiPaymentStatus.getChiaveTransazione()+" - "+e.getMessage());
			}
		}
		for(ModuloIntegrazionePagamenti moduloIntegrazionePagamenti: listaMIP){
			try {
				sveccDAOBK.deleteMIP(moduloIntegrazionePagamenti);
			} catch(Exception e) {
				errors.add("Rollback MIP "+moduloIntegrazionePagamenti.getChiaveTransazione()+" - "+e.getMessage());
			}
		}
		for(ModuloIntegrazionePagamentiNodo moduloIntegrazionePagamentiNodo: listaMIN){
			try {
				sveccDAOBK.deleteMIN(moduloIntegrazionePagamentiNodo);
			} catch(Exception e) {
				errors.add("Rollback MIN "+moduloIntegrazionePagamentiNodo.getChiaveTransazione()+" - "+e.getMessage());
			}
		}
		for(TransazioneIci transazioneIci: listaTIC){
			try {
				sveccDAOBK.deleteTIC(transazioneIci);
			} catch(Exception e) {
				errors.add("Rollback TIC "+transazioneIci.getChiaveTransazioneIci()+" - "+e.getMessage());
			}
		}
		for(TransazioneIV transazioneIV: listaTDT){
			try {
				sveccDAOBK.deleteTDT(transazioneIV);
			} catch(Exception e) {
				errors.add("Rollback TDT "+transazioneIV.getChiaveTransazioneDettaglio()+" - "+e.getMessage());
			}
		}
		for(TransazioneFreccia transazioneFreccia: listaTFR){				
			try {
				sveccDAOBK.deleteTFR(transazioneFreccia);
			} catch(Exception e) {
				errors.add("Rollback TFR "+transazioneFreccia.getChiaveTransazioneDettaglio()+" - "+e.getMessage());
			}
		}
		for(QuadraturaNodo quadraturaNodo: listaQUN){	
			try {
				sveccDAOBK.deleteQUN(quadraturaNodo);
			} catch(Exception e) {
				errors.add("Rollback QUN "+quadraturaNodo.getKeyQuadratura()+" - "+e.getMessage());
			}
		}
		for(NodoSpcRpt nodoSpcRpt: listaRPT){
			try {
				sveccDAOBK.deleteRPT(nodoSpcRpt);
			} catch(Exception e) {
				errors.add("Rollback RPT "+nodoSpcRpt.getId()+" - "+e.getMessage());
			}
		}
		for(FlussiRen flussiRen: listaREN){
			try {
				sveccDAOBK.deleteREN(flussiRen);
			} catch(Exception e) {
				errors.add("Rollback REN "+flussiRen.getChiaveRendicontazione()+" - "+e.getMessage());
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
				errors.add("Rollback TRA "+transazione.getChiaveTransazione()+" - "+e.getMessage());
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
				System.out.println("cutecute: "+cutecute);
			emsRes = emailSender.sendEMail(emailAdmin, "", "", oggetto, bodyEmail.toString(), "", cutecute);
			
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

