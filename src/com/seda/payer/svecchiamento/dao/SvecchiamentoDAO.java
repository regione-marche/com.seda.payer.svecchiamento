package com.seda.payer.svecchiamento.dao;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import com.seda.data.helper.HelperException;
import com.seda.data.procedure.reflection.DriverType;
import com.seda.data.procedure.reflection.ProcedureReflectorException;
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
import com.seda.payer.core.handler.BaseDaoHandler;
import com.seda.payer.core.riconciliazionemt.bean.GiornaleDiCassa;
import com.seda.payer.core.riconciliazionemt.bean.MovimentoDiCassa;
import com.seda.payer.svecchiamento.model.AssociazioneFlussi;
import com.seda.payer.svecchiamento.model.AssociazioneTransazioni;
import com.seda.payer.svecchiamento.model.AssociazioneUtenteAppl;
import com.seda.payer.svecchiamento.model.SeUser;

//inizio LP 20240820 - PGNTBOLDER-1
//public class SvecchiamentoDAO {

//	String schema; //LP 20240820 - PGNTBOLDER-1
//	Connection connection; //LP 20240820 - PGNTBOLDER-1
public class SvecchiamentoDAO extends BaseDaoHandler {
//fine LP 20240820 - PGNTBOLDER-1
	
	protected CallableStatement callableStatementTRAList = null;
	protected CallableStatement callableStatementTRADel = null;
	protected CallableStatement callableStatementTRAIns = null;
	protected CallableStatement callableStatementRENList = null;
	protected CallableStatement callableStatementRENIns = null;
	protected CallableStatement callableStatementRENDel = null;
	protected CallableStatement callableStatementTFRList = null;
	protected CallableStatement callableStatementTFRDel = null;
	protected CallableStatement callableStatementTDTList = null;
	protected CallableStatement callableStatementTDTDel = null;
	protected CallableStatement callableStatementTICList = null;
	protected CallableStatement callableStatementTICDel = null;
	protected CallableStatement callableStatementQUNList = null;
	protected CallableStatement callableStatementQUNIns = null;
	protected CallableStatement callableStatementQUNDel = null;
	protected CallableStatement callableStatementRPTList = null;
	protected CallableStatement callableStatementRPTIns = null;
	protected CallableStatement callableStatementRPTDel = null;
	protected CallableStatement callableStatementMINList = null;
	protected CallableStatement callableStatementMINDel = null;
	protected CallableStatement callableStatementMINIns = null;
	protected CallableStatement callableStatementMIPList = null;
	protected CallableStatement callableStatementMIPDel = null;
	protected CallableStatement callableStatementMIPIns = null;
	protected CallableStatement callableStatementMPSList = null;
	protected CallableStatement callableStatementMPSDel = null;
	protected CallableStatement callableStatementMPSIns = null;
	protected CallableStatement callableStatementGDCList = null;
	protected CallableStatement callableStatementGDCIns = null;
	protected CallableStatement callableStatementGDCDel = null;
	protected CallableStatement callableStatementMDCList = null;
	protected CallableStatement callableStatementMDCIns = null;
	protected CallableStatement callableStatementMDCDel = null;
	protected CallableStatement callableStatementRMTList = null;
	protected CallableStatement callableStatementRMTIns = null;
	protected CallableStatement callableStatementRMTDel = null;
	protected CallableStatement callableStatementRMFList = null;
	protected CallableStatement callableStatementRMFIns = null;
	protected CallableStatement callableStatementRMFDel = null;
	protected CallableStatement callableStatementATMList = null;
	protected CallableStatement callableStatementATMDel = null;
	protected CallableStatement callableStatementPOSList = null;
	protected CallableStatement callableStatementPOSDel = null;
	protected CallableStatement callableStatementMNAList = null;
	protected CallableStatement callableStatementMNADel = null;
	protected CallableStatement callableStatementMNAIns = null;
	protected CallableStatement callableStatementUSRIns = null;
	protected CallableStatement callableStatementUSRLst = null;
	protected CallableStatement callableStatementSOCLst = null;
	protected CallableStatement callableStatementUTELst = null;
	protected CallableStatement callableStatementPRFList = null;
	protected CallableStatement callableStatementAPCList = null;
	protected CallableStatement callableStatementANEList = null;
	protected CallableStatement callableStatementANEIns = null;
	protected CallableStatement callableStatementENTList = null;
	protected CallableStatement callableStatementBOLList = null;
	protected CallableStatement callableStatementCFEList = null;
	protected CallableStatement callableStatementCFSList = null;
	protected CallableStatement callableStatementCANList = null;
	protected CallableStatement callableStatementCESList = null;
	protected CallableStatement callableStatementTSEList = null;
	protected CallableStatement callableStatementGTWList = null;
	protected CallableStatement callableStatementGTWIns = null;
	protected CallableStatement callableStatementCARList = null;
	protected CallableStatement callableStatementSERList = null;
	protected CallableStatement callableStatementSEUSRIns = null;
	protected CallableStatement callableStatementSEUSRSel = null;
	protected CallableStatement callableStatementSEUSRUpdate = null;
	protected CallableStatement callableStatementSESOCIns = null;
	protected CallableStatement callableStatementDropTrigger = null;
	
	public SvecchiamentoDAO(Connection connection, String schema) {
		//inizio LP 20240820 - PGNTBOLDER-1
		//super();
		//this.connection = connection;
		//this.schema = schema;
		super(connection, schema);
		//fine LP 20240820 - PGNTBOLDER-1
	}

	public ArrayList<Company> getListSOC() throws DaoException {
		ArrayList<Company> result = new ArrayList<Company>();
		try {
			if (callableStatementSOCLst == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementSOCLst = Helper.prepareCall(connection, schema, "PYSOCSP_LST_ALL");
				callableStatementSOCLst = prepareCall("PYSOCSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementSOCLst.execute()) {
				ResultSet data = callableStatementSOCLst.getResultSet();
				while (data.next())
					result.add(new Company(data));
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<User> getListUTE() throws DaoException {
		ArrayList<User> result = new ArrayList<User>();	
		try {
			if (callableStatementUTELst == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementUTELst = Helper.prepareCall(connection, schema, "PYUTESP_LST_ALL");
				callableStatementUTELst = prepareCall("PYUTESP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementUTELst.execute()) {
				ResultSet data = callableStatementUTELst.getResultSet();
				while (data.next())
					result.add(new User(data));
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<PyUser> getListUSR() throws DaoException {
		ArrayList<PyUser> result = new ArrayList<PyUser>();	
		try {
			if (callableStatementUSRLst == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementUSRLst = Helper.prepareCall(connection, schema, "PYUSRSP_LST_ALL");
				callableStatementUSRLst = prepareCall("PYUSRSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementUSRLst.execute()) {
				ResultSet data = callableStatementUSRLst.getResultSet();
				while (data.next())
					result.add(PyUser.getBean(data, false));
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void insertUSR(PyUser pyUser) throws DaoException, SQLException {
		try {
			if (callableStatementUSRIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementUSRIns = Helper.prepareCall(connection, schema, "PYUSRSP_INS2");
				callableStatementUSRIns = prepareCall("PYUSRSP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementUSRIns.setLong(1, pyUser.getChiaveUtente());
			callableStatementUSRIns.setString(2, pyUser.getUserName());
			callableStatementUSRIns.setString(3, pyUser.getUserProfile());
			callableStatementUSRIns.setString(4, pyUser.getFlagAttivazione());
			callableStatementUSRIns.setString(5, pyUser.getCodiceSocieta());
			callableStatementUSRIns.setString(6, pyUser.getCodiceUtente());
			callableStatementUSRIns.setString(7, pyUser.getChiaveEnteConsorzio());
			callableStatementUSRIns.setString(8, pyUser.getChiaveEnteConsorziato());
			callableStatementUSRIns.setString(9, pyUser.getDownloadFlussiRendicontazione());
			callableStatementUSRIns.setString(10, pyUser.getInvioFlussiRendicontazioneViaFtp());
			callableStatementUSRIns.setString(11, pyUser.getInvioFlussiRendicontazioneViaEmail());
			callableStatementUSRIns.setString(12, pyUser.getAzioniPerTransazioniOK());
			callableStatementUSRIns.setString(13, pyUser.getAzioniPerTransazioniKO());
			callableStatementUSRIns.setString(14, pyUser.getAzioniPerRiconciliazioneManuale());
			callableStatementUSRIns.setString(15, pyUser.getAttivazioneEstrattoContoManager());
			callableStatementUSRIns.setString(16, pyUser.getAbilitazioneProfiloRiversamento());
			callableStatementUSRIns.setString(17, pyUser.getAbilitazioneMultiUtente());
			callableStatementUSRIns.setString(18, pyUser.getOperatoreUltimoAggiornamento());
			callableStatementUSRIns.setString(19, pyUser.getListaTipologieServizio());
			callableStatementUSRIns.setString(20, pyUser.getMailContoGestione().equals("") ? "N" : pyUser.getMailContoGestione());
			callableStatementUSRIns.setString(21, pyUser.getEntePertinenza());
			callableStatementUSRIns.setString(22, pyUser.getGruppoAgenzia());
			callableStatementUSRIns.setString(23, pyUser.getAssociazioniProvvisorieRiconciliazionemt());
			callableStatementUSRIns.setString(24, pyUser.getAssociazioniDefinitiveRiconciliazionemt());
			callableStatementUSRIns.setString(25, pyUser.getMail());
			callableStatementUSRIns.setString(26, pyUser.getMailPec());
			callableStatementUSRIns.setString(27, pyUser.getPinCodeMail());
			callableStatementUSRIns.setString(28, pyUser.getPinCodePec());
			callableStatementUSRIns.setString(29, pyUser.getFlagValidazioneMail());
			callableStatementUSRIns.setString(30, pyUser.getFlagValidazionePec());
			callableStatementUSRIns.registerOutParameter(31, Types.INTEGER);
			callableStatementUSRIns.executeUpdate();
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}
	
	public void insertMNA(AssociazioneUtenteAppl associazioneUtenteAppl) throws DaoException, SQLException {
		try {
			if (callableStatementMNAIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMNAIns = Helper.prepareCall(connection, schema, "PYMNASP_INS");
				callableStatementMNAIns = prepareCall("PYMNASP_INS");
				//fine LP PGNTBOLDER-1
			}
			callableStatementMNAIns.setLong(1, associazioneUtenteAppl.getChiaveUtente());
			callableStatementMNAIns.setString(2, associazioneUtenteAppl.getCodiceApplicazione());
			callableStatementMNAIns.registerOutParameter(3, Types.INTEGER);
			callableStatementMNAIns.executeUpdate();
			
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public ArrayList<AssociazioneUtenteAppl> getListMNA() throws DaoException {
		ArrayList<AssociazioneUtenteAppl> result = new ArrayList<AssociazioneUtenteAppl>();	
		try {
			if (callableStatementMNAList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMNAList = Helper.prepareCall(connection, schema, "PYMNASP_LST_ALL");
				callableStatementMNAList = prepareCall("PYMNASP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementMNAList.execute()) {
				ResultSet data = callableStatementMNAList.getResultSet();
				while (data.next())
					result.add(new AssociazioneUtenteAppl(data));
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void deleteMNA(AssociazioneUtenteAppl associazioneUtenteAppl) throws DaoException {
		try	{
			if (callableStatementMNADel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMNADel = Helper.prepareCall(connection, schema, "PYMNASP_DEL");
				callableStatementMNADel = prepareCall("PYMNASP_DEL");
				//fine LP PGNTBOLDER-1
			}
			callableStatementMNADel.setLong(1, associazioneUtenteAppl.getChiaveUtente());
			callableStatementMNADel.setString(2, associazioneUtenteAppl.getCodiceApplicazione());
			callableStatementMNADel.execute();
		} catch(Exception e){
			System.out.println("Eliminazione MNA: "+e.getMessage());
		}
	}

	public ArrayList<ApplProf> getListPRF() throws DaoException {
		ArrayList<ApplProf> result = new ArrayList<ApplProf>();		
		try {
			if (callableStatementPRFList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementPRFList = Helper.prepareCall(connection, schema, "PYPRFSP_LST_ALL");
				callableStatementPRFList = prepareCall("PYPRFSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementPRFList.execute()) {
				ResultSet data = callableStatementPRFList.getResultSet();
				while (data.next())
					result.add(new ApplProf(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<AnagProvCom> getListAPC() throws DaoException {
		ArrayList<AnagProvCom> result = new ArrayList<AnagProvCom>();
		try {
			if (callableStatementAPCList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementAPCList = Helper.prepareCall(connection, schema, "PYAPCSP_LST_ALL");
				callableStatementAPCList = prepareCall("PYAPCSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementAPCList.execute()) {
				ResultSet data = callableStatementAPCList.getResultSet();
				while (data.next())
					result.add(new AnagProvCom(data));
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<AnagEnte> getListANE() throws DaoException {
		ArrayList<AnagEnte> result = new ArrayList<AnagEnte>();
		try {
			if (callableStatementANEList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementANEList = Helper.prepareCall(connection, schema, "PYANESP_LST_ALL");
				callableStatementANEList = prepareCall("PYANESP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementANEList.execute()) {
				ResultSet data = callableStatementANEList.getResultSet();
				while (data.next())
					result.add(new AnagEnte(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void insertANE(AnagEnte anagEnte) throws DaoException, SQLException {
		try {
			if (callableStatementANEIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementANEIns = Helper.prepareCall(connection, schema, "PYANESP_INS2");
				callableStatementANEIns = prepareCall("PYANESP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementANEIns.setString(1, anagEnte.getChiaveEnte()); 
			callableStatementANEIns.setString(2, anagEnte.getCodiceEnte()); 
			callableStatementANEIns.setString(3, anagEnte.getTipoUfficio());
			callableStatementANEIns.setString(4, anagEnte.getCodiceUfficio());
			callableStatementANEIns.setString(5, anagEnte.getCodiceTipoEnte());
			callableStatementANEIns.setString(6, anagEnte.getDescrizioneEnte());
			callableStatementANEIns.setString(7, anagEnte.getDescrizioneUfficio());
			callableStatementANEIns.setString(8,anagEnte.getAnagProvCom().getCodiceBelfiore());
			callableStatementANEIns.setString(9,anagEnte.getCodiceRuoliErariali());			
			callableStatementANEIns.setDate(10, anagEnte.getDataDecorrenza());
			callableStatementANEIns.setString(11, anagEnte.getUfficioStatale());
			callableStatementANEIns.setString(12, anagEnte.getCodiceOperatore());	
			callableStatementANEIns.executeUpdate();
			
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public void insertGTW(GatewayPagamento gatewayPagamento) throws DaoException, SQLException {
		try {
			if (callableStatementGTWIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementGTWIns = Helper.prepareCall(connection, schema, "PYGTWSP_INS2");
				callableStatementGTWIns = prepareCall("PYGTWSP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementGTWIns.setString(1, gatewayPagamento.getUser().getCompany().getCompanyCode());
			callableStatementGTWIns.setString(2, gatewayPagamento.getUser().getUserCode());
			callableStatementGTWIns.setString(3, gatewayPagamento.getCanale().getChiaveCanalePagamento());
			callableStatementGTWIns.setString(4, gatewayPagamento.getDescrizioneGateway());
			callableStatementGTWIns.setString(5, gatewayPagamento.getPathImgLogo());		
			callableStatementGTWIns.setString(6, gatewayPagamento.getUrlSitoWebGateway());
			callableStatementGTWIns.setString(7, gatewayPagamento.getTipoGateway());
			callableStatementGTWIns.setString(8, gatewayPagamento.getEmailNotificaAdmin());
			callableStatementGTWIns.setString(9, gatewayPagamento.getUrlApiEndpoint());
			callableStatementGTWIns.setString(10, gatewayPagamento.getApiUser());
			callableStatementGTWIns.setString(11, gatewayPagamento.getApiPassword());
			callableStatementGTWIns.setString(12, gatewayPagamento.getApiSignature());
			callableStatementGTWIns.setString(13, gatewayPagamento.getUrlApiImage());
			callableStatementGTWIns.setString(14, gatewayPagamento.getApiVersion());
			callableStatementGTWIns.setString(15, gatewayPagamento.getUrlApiRedirect());
			callableStatementGTWIns.setString(16, gatewayPagamento.getUrlApiCancel());
			callableStatementGTWIns.setString(17, gatewayPagamento.getCodiceNegozio());
			callableStatementGTWIns.setString(18, gatewayPagamento.getCodiceMacAvvio());
			callableStatementGTWIns.setString(19, gatewayPagamento.getCodiceMacEsito());
			callableStatementGTWIns.setString(20, gatewayPagamento.getTipoAutorizzazione());
			callableStatementGTWIns.setString(21, gatewayPagamento.getTipoContabilizzazione());
			callableStatementGTWIns.setString(22, gatewayPagamento.getOpzioniAggiuntive());
			callableStatementGTWIns.setString(23, gatewayPagamento.getCarta().getCodiceCartaPagamento());
			callableStatementGTWIns.setString(24, gatewayPagamento.getFlagAttivazione());
			callableStatementGTWIns.setString(25, gatewayPagamento.getCodiceSIAAziendaDestinataria());
			callableStatementGTWIns.setString(26, gatewayPagamento.getCodiceCINBancaMittente());
			callableStatementGTWIns.setString(27, gatewayPagamento.getCodiceABIBancaMittente());
			callableStatementGTWIns.setString(28, gatewayPagamento.getCodiceCABBancaMittente());
			callableStatementGTWIns.setString(29, gatewayPagamento.getCodiceContoCorrente());
			callableStatementGTWIns.setInt(30, gatewayPagamento.getDeltaGiorniDataContabile());
			callableStatementGTWIns.setBigDecimal(31, gatewayPagamento.getImportoScostamento());
			callableStatementGTWIns.setString(32, gatewayPagamento.getCodiceOperatore());
			callableStatementGTWIns.setString(33, gatewayPagamento.getChiaveGateway());
			callableStatementGTWIns.executeUpdate();
			
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public ArrayList<Ente> getListENT() throws DaoException {
		ArrayList<Ente> result = new ArrayList<Ente>();
		try {
			if (callableStatementENTList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementENTList = Helper.prepareCall(connection, schema, "PYENTSP_LST_ALL");
				callableStatementENTList = prepareCall("PYENTSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementENTList.execute()) {
				ResultSet data = callableStatementENTList.getResultSet();
				while (data.next())
					result.add(new Ente(data));
			}	
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<Bollettino> getListBOL() throws DaoException {
		ArrayList<Bollettino> result = new ArrayList<Bollettino>();
		try {
			if (callableStatementBOLList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementBOLList = Helper.prepareCall(connection, schema, "PYBOLSP_LST");
				callableStatementBOLList = prepareCall("PYBOLSP_LST");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementBOLList.execute()) {
				ResultSet data = callableStatementBOLList.getResultSet();
				while (data.next())
					result.add(new Bollettino(data));
			}	
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<ConfigUtenteTipoServizioEnte> getListCFE() throws DaoException{
		ArrayList<ConfigUtenteTipoServizioEnte> result = new ArrayList<ConfigUtenteTipoServizioEnte>();
		try {
			if (callableStatementCFEList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementCFEList = Helper.prepareCall(connection, schema, "PYCFESP_LST_ALL");
				callableStatementCFEList = prepareCall("PYCFESP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementCFEList.execute()) {
				ResultSet data = callableStatementCFEList.getResultSet();
				while (data.next())
					result.add(new ConfigUtenteTipoServizioEnte(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<ConfigUtenteTipoServizio> getListCFS() throws DaoException{
		ArrayList<ConfigUtenteTipoServizio> result = new ArrayList<ConfigUtenteTipoServizio>();		
		try {
			if (callableStatementCFSList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementCFSList = Helper.prepareCall(connection, schema, "PYCFSSP_LST_ALL");
				callableStatementCFSList = prepareCall("PYCFSSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementCFSList.execute()) {
				ResultSet data = callableStatementCFSList.getResultSet();
				while (data.next())
					result.add(new ConfigUtenteTipoServizio(data));
			}	
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<CanalePagamento> getListCAN() throws DaoException{
		ArrayList<CanalePagamento> result = new ArrayList<CanalePagamento>();
		try {
			if (callableStatementCANList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementCANList = Helper.prepareCall(connection, schema, "PYCANSP_LST_ALL");
				callableStatementCANList = prepareCall("PYCANSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementCANList.execute()) {
				ResultSet data = callableStatementCANList.getResultSet();
				while (data.next())
					result.add(new CanalePagamento(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<AbilitaCanalePagamentoTipoServizioEnte> getListCES() throws DaoException{
		ArrayList<AbilitaCanalePagamentoTipoServizioEnte> result = new ArrayList<AbilitaCanalePagamentoTipoServizioEnte>();
		try {
			if (callableStatementCESList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementCESList = Helper.prepareCall(connection, schema, "PYCESSP_LST_ALL");
				callableStatementCESList = prepareCall("PYCESSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementCESList.execute()) {
				ResultSet data = callableStatementCESList.getResultSet();
				while (data.next())
					result.add(new AbilitaCanalePagamentoTipoServizioEnte(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<TipologiaServizio> getListTSE() throws DaoException{
		ArrayList<TipologiaServizio> result = new ArrayList<TipologiaServizio>();
		try {
			if (callableStatementTSEList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementTSEList = Helper.prepareCall(connection, schema, "PYTSESP_LST_ALL");
				callableStatementTSEList = prepareCall("PYTSESP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementTSEList.execute()) {
				ResultSet data = callableStatementTSEList.getResultSet();
				while (data.next())
					result.add(new TipologiaServizio(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<GatewayPagamento> getListGTW() throws DaoException{
		ArrayList<GatewayPagamento> result = new ArrayList<GatewayPagamento>();
		try {
			if (callableStatementGTWList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementGTWList = Helper.prepareCall(connection, schema, "PYGTWSP_LST_ALL");
				callableStatementGTWList = prepareCall("PYGTWSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementGTWList.execute()) {
				ResultSet data = callableStatementGTWList.getResultSet();
				while (data.next())
					result.add(new GatewayPagamento(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<CartaPagamento> getListCAR() throws DaoException{
		ArrayList<CartaPagamento> result = new ArrayList<CartaPagamento>();
		try {
			if (callableStatementCARList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementCARList = Helper.prepareCall(connection, schema, "PYCARSP_LST_ALL");
				callableStatementCARList = prepareCall("PYCARSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementCARList.execute()) {
				ResultSet data = callableStatementCARList.getResultSet();
				while (data.next())
					result.add(new CartaPagamento(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<Transazione> getListTRA(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<Transazione> result = new ArrayList<Transazione>();
		try {
			if (callableStatementTRAList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementTRAList = Helper.prepareCall(connection, schema, "PYTRASP_LST_ALL");
				callableStatementTRAList = prepareCall("PYTRASP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			callableStatementTRAList.setDate(1, dataTransazione);
			callableStatementTRAList.setString(2, codSoc);
			if (callableStatementTRAList.execute()) {
				ResultSet data = callableStatementTRAList.getResultSet();
				while (data.next())
					result.add(Transazione.getBean_ExtendedKPOF(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void deleteTRA(Transazione transazione) throws DaoException {
		try	{
			if (callableStatementTRADel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementTRADel = Helper.prepareCall(connection, schema, "PYTRASP_DEL2");
				callableStatementTRADel = prepareCall("PYTRASP_DEL2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementTRADel.setString(1, transazione.getChiaveTransazione());
			callableStatementTRADel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<AnagServizi> getListSER() throws DaoException{
		ArrayList<AnagServizi> result = new ArrayList<AnagServizi>();
		try {
			if (callableStatementSERList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementSERList = Helper.prepareCall(connection, schema, "PYSERSP_LST_ALL");
				callableStatementSERList = prepareCall("PYSERSP_LST_ALL");
				//fine LP PGNTBOLDER-1
			}
			if (callableStatementSERList.execute()) {
				ResultSet data = callableStatementSERList.getResultSet();
				while (data.next())
					result.add(new AnagServizi(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}	
		return result;
	}

	public ArrayList<TransazioneIV> getListTDT(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<TransazioneIV> result = new ArrayList<TransazioneIV>();
		try {
			if (callableStatementTDTList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementTDTList = Helper.prepareCall(connection, schema, "PYTDTSP_LST_BY_DTRA");
				callableStatementTDTList = prepareCall("PYTDTSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementTDTList.setDate(1, dataTransazione);
			callableStatementTDTList.setString(2, codSoc);	
			if (callableStatementTDTList.execute()) {
				ResultSet data = callableStatementTDTList.getResultSet();
				while (data.next())
					result.add(TransazioneIV.getBean(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void deleteTDT(TransazioneIV transazioneIV) throws DaoException	{
		try {
			if (callableStatementTDTDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementTDTDel = Helper.prepareCall(connection, schema, "PYTDTSP_DEL");
				callableStatementTDTDel = prepareCall("PYTDTSP_DEL");
				//fine LP PGNTBOLDER-1
			}
			callableStatementTDTDel.setString(1, transazioneIV.getChiaveTransazioneDettaglio());
			callableStatementTDTDel.registerOutParameter(2, Types.INTEGER);
			callableStatementTDTDel.executeUpdate();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<QuadraturaNodo> getListQUN(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<QuadraturaNodo> result = new ArrayList<QuadraturaNodo>();
		try {
			if (callableStatementQUNList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementQUNList = Helper.prepareCall(connection, schema, "PYQUNSP_LST_BY_DTRA");
				callableStatementQUNList = prepareCall("PYQUNSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementQUNList.setDate(1, dataTransazione);
			callableStatementQUNList.setString(2, codSoc);	
			if (callableStatementQUNList.execute()) {
				ResultSet data = callableStatementQUNList.getResultSet();
				while (data.next())
					result.add(new QuadraturaNodo(data));	
			}
		
		} catch (SQLException x) {
			throw new DaoException(x); 
			
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void insertQUN(QuadraturaNodo quadraturaNodo) throws DaoException{
		try {
			if (callableStatementQUNIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementQUNIns = Helper.prepareCall(connection, schema, "PYQUNSP_INS");
				callableStatementQUNIns = prepareCall("PYQUNSP_INS");
				//fine LP PGNTBOLDER-1
			}
			callableStatementQUNIns.setLong(1, quadraturaNodo.getKeyQuadratura());	
			callableStatementQUNIns.setString(2, quadraturaNodo.getCodSocieta());		
			callableStatementQUNIns.setString(3, quadraturaNodo.getCodUtente());	
			callableStatementQUNIns.setString(4, quadraturaNodo.getKeyGateway());		
			callableStatementQUNIns.setString(5, quadraturaNodo.getNomeFileTxt());				
			callableStatementQUNIns.setTimestamp(6, quadraturaNodo.getDtInizioRend());			
			callableStatementQUNIns.setTimestamp(7, quadraturaNodo.getDtFineRend());				
			callableStatementQUNIns.setLong(8, quadraturaNodo.getNumTransazioni());		
			callableStatementQUNIns.setBigDecimal(9, quadraturaNodo.getImpSquadratura());				
			callableStatementQUNIns.setString(10, quadraturaNodo.getStatoSquadratura());	
			callableStatementQUNIns.setString(11, quadraturaNodo.getMovimentoElab());				
			callableStatementQUNIns.setTimestamp(12, quadraturaNodo.getDtChiusuraFlusso());			
			callableStatementQUNIns.setString(13, quadraturaNodo.getCodOperatore());			
			callableStatementQUNIns.setString(14, quadraturaNodo.getVersOggetto());			
			callableStatementQUNIns.setString(15, quadraturaNodo.getIdFlusso());		
			callableStatementQUNIns.setTimestamp(16, quadraturaNodo.getDtflusso());			
			callableStatementQUNIns.setString(17, quadraturaNodo.getIdUnivocoRegol());
			callableStatementQUNIns.setDate(18, quadraturaNodo.getDtregol());		
			callableStatementQUNIns.setString(19, quadraturaNodo.getTipoIdUnivocoMitt());
			callableStatementQUNIns.setString(20, quadraturaNodo.getCodIdUnivocoMitt());			
			callableStatementQUNIns.setString(21, quadraturaNodo.getDenomMitt());				
			callableStatementQUNIns.setString(22, quadraturaNodo.getTipoIdUnivocoRice());			
			callableStatementQUNIns.setString(23, quadraturaNodo.getCodIdUnivocoRice());			
			callableStatementQUNIns.setString(24, quadraturaNodo.getDenomRice());			
			callableStatementQUNIns.setLong(25, quadraturaNodo.getNumTotPagamenti());			
			callableStatementQUNIns.setBigDecimal(26, quadraturaNodo.getImpTotPagamenti());			
			callableStatementQUNIns.setString(27, quadraturaNodo.getKeyEnte());	
			callableStatementQUNIns.setLong(28, quadraturaNodo.getNumTransazioniRecuperate());				
			callableStatementQUNIns.execute();		
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public void deleteQUN(QuadraturaNodo quadraturaNodo) throws DaoException {
		try	{
			if (callableStatementQUNDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementQUNDel = Helper.prepareCall(connection, schema, "PYQUNSP_DEL");
				callableStatementQUNDel = prepareCall("PYQUNSP_DEL");
				//fine LP PGNTBOLDER-1
			}
			callableStatementQUNDel.setLong(1, quadraturaNodo.getKeyQuadratura());
			callableStatementQUNDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<FlussiRen> getListREN(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<FlussiRen> result = new ArrayList<FlussiRen>();
		try {
			if (callableStatementRENList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRENList = Helper.prepareCall(connection, schema, "PYRENSP_LST_BY_DTRA");
				callableStatementRENList = prepareCall("PYRENSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRENList.setDate(1, dataTransazione);
			callableStatementRENList.setString(2, codSoc);
			if (callableStatementRENList.execute()) {
				ResultSet data = callableStatementRENList.getResultSet();
				while (data.next())
					result.add(FlussiRen.getBean_Extended(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void deleteREN(FlussiRen flussiRen) throws DaoException {		
		try	{
			if (callableStatementRENDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRENDel = Helper.prepareCall(connection, schema, "PYRENSP_DEL");
				callableStatementRENDel = prepareCall("PYRENSP_DEL");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRENDel.setString(1, flussiRen.getChiaveRendicontazione());
			callableStatementRENDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<NodoSpcRpt> getListRPT(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<NodoSpcRpt> result = new ArrayList<NodoSpcRpt>();
		try {
			if (callableStatementRPTList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRPTList = Helper.prepareCall(connection, schema, "PYRPTSP_LST_BY_DTRA");
				callableStatementRPTList = prepareCall("PYRPTSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRPTList.setDate(1, dataTransazione);
			callableStatementRPTList.setString(2, codSoc);
			if (callableStatementRPTList.execute()) {
				ResultSet data = callableStatementRPTList.getResultSet();
				while (data.next())
					result.add( NodoSpcRpt.getBean_Extended(data));			
			}
			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void deleteRPT(NodoSpcRpt nodoSpcRpt) throws DaoException {		
		try	{
			if (callableStatementRPTDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRPTDel = Helper.prepareCall(connection, schema, "PYRPTSP_DEL");
				callableStatementRPTDel = prepareCall("PYRPTSP_DEL");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRPTDel.setString(1, String.valueOf(nodoSpcRpt.getId()));
			callableStatementRPTDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<ModuloIntegrazionePagamentiNodo> getListMIN(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<ModuloIntegrazionePagamentiNodo> result = new ArrayList<ModuloIntegrazionePagamentiNodo>();
		try {
			if (callableStatementMINList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMINList = Helper.prepareCall(connection, schema, "PYMINSP_LST_BY_DTRA");
				callableStatementMINList = prepareCall("PYMINSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementMINList.setDate(1, dataTransazione);
			callableStatementMINList.setString(2, codSoc);
			if (callableStatementMINList.execute()) {
				ResultSet data = callableStatementMINList.getResultSet();
				while (data.next())
					result.add(new ModuloIntegrazionePagamentiNodo(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void deleteMIN(ModuloIntegrazionePagamentiNodo moduloIntegrazionePagamenti) throws DaoException {		
		try	{
			if (callableStatementMINDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMINDel = Helper.prepareCall(connection, schema, "PYMINSP_DEL");
				callableStatementMINDel = prepareCall("PYMINSP_DEL");
				//fine LP PGNTBOLDER-1
			}	
			callableStatementMINDel.setString(1, moduloIntegrazionePagamenti.getChiaveTransazione());
			callableStatementMINDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<ModuloIntegrazionePagamentiPaymentStatus> getListMPS(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<ModuloIntegrazionePagamentiPaymentStatus> result = new ArrayList<ModuloIntegrazionePagamentiPaymentStatus>();	
		try {
			if (callableStatementMPSList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMPSList = Helper.prepareCall(connection, schema, "PYMPSSP_LST_BY_DTRA");
				callableStatementMPSList = prepareCall("PYMPSSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementMPSList.setDate(1, dataTransazione);
			callableStatementMPSList.setString(2, codSoc);
			if (callableStatementMPSList.execute()) {
				ResultSet data = callableStatementMPSList.getResultSet();
				while (data.next())
					result.add(new ModuloIntegrazionePagamentiPaymentStatus(data));			
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void insertMPS(ModuloIntegrazionePagamentiPaymentStatus moduloIntPagamentiStatus) throws DaoException{
		try {
			if (callableStatementMPSIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMPSIns = Helper.prepareCall(connection, schema, "PYMPSSP_INS2");
				callableStatementMPSIns = prepareCall("PYMPSSP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementMPSIns.setString(1, moduloIntPagamentiStatus.getChiaveTransazione());	
			callableStatementMPSIns.setInt(2, moduloIntPagamentiStatus.getGruppoTentativiNotifica());		
			callableStatementMPSIns.setInt(3, moduloIntPagamentiStatus.getNumeroTentativoNotifica());			
			callableStatementMPSIns.setString(4, moduloIntPagamentiStatus.getModalitaNotifica());
			callableStatementMPSIns.setString(5, moduloIntPagamentiStatus.getEsitoTransazione());
			callableStatementMPSIns.setString(6, moduloIntPagamentiStatus.getIdPortale());	
			callableStatementMPSIns.setString(7, moduloIntPagamentiStatus.getNumeroOperazione());	
			callableStatementMPSIns.setString(8, moduloIntPagamentiStatus.getXmlPaymentStatus());			
			callableStatementMPSIns.setString(9, moduloIntPagamentiStatus.getXmlPaymentData());	
			callableStatementMPSIns.setString(10, moduloIntPagamentiStatus.getXmlCommitMessage());				
			callableStatementMPSIns.setString(11, moduloIntPagamentiStatus.getEsitoNotifica());
			callableStatementMPSIns.setString(12, moduloIntPagamentiStatus.getS2SResponseHtmlStatusCode());		
			callableStatementMPSIns.setString(13, moduloIntPagamentiStatus.getS2SResponseMessage());		
			callableStatementMPSIns.setString(14, moduloIntPagamentiStatus.getParametriOpzionali1());			
			callableStatementMPSIns.setString(15, moduloIntPagamentiStatus.getParametriOpzionali2());				
			callableStatementMPSIns.setString(16, moduloIntPagamentiStatus.getOperatoreUltimoAggiornamento());			
							
			callableStatementMPSIns.execute();		
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public void deleteMPS(ModuloIntegrazionePagamentiPaymentStatus moduloIntPagamentiStatus) throws DaoException {		
		try	{
			if (callableStatementMPSDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMPSDel = Helper.prepareCall(connection, schema, "PYMPSSP_DEL");
				callableStatementMPSDel = prepareCall("PYMPSSP_DEL");
				//fine LP PGNTBOLDER-1
			}
			callableStatementMPSDel.setString(1, moduloIntPagamentiStatus.getChiaveTransazione());
			callableStatementMPSDel.setLong(2, moduloIntPagamentiStatus.getGruppoTentativiNotifica());
			callableStatementMPSDel.setLong(3, moduloIntPagamentiStatus.getNumeroTentativoNotifica());
			callableStatementMPSDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<ModuloIntegrazionePagamenti> getListMIP(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<ModuloIntegrazionePagamenti> result = new ArrayList<ModuloIntegrazionePagamenti>();
		try {
			if (callableStatementMIPList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMIPList = Helper.prepareCall(connection, schema, "PYMIPSP_LST_BY_DTRA");
				callableStatementMIPList = prepareCall("PYMIPSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementMIPList.setDate(1, dataTransazione);
			callableStatementMIPList.setString(2, codSoc);
			if (callableStatementMIPList.execute()) {
				ResultSet data = callableStatementMIPList.getResultSet();
				while (data.next())
					result.add(new ModuloIntegrazionePagamenti(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void deleteMIP(ModuloIntegrazionePagamenti moduloIntegrazionePagamenti) throws DaoException {
		try	{
			if (callableStatementMIPDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMIPDel = Helper.prepareCall(connection, schema, "PYMIPSP_DEL");
				callableStatementMIPDel = prepareCall("PYMIPSP_DEL");
				//fine LP PGNTBOLDER-1
			}
			callableStatementMIPDel.setString(1, moduloIntegrazionePagamenti.getChiaveTransazione());
			callableStatementMIPDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<TransazioneFreccia> getListTFR(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<TransazioneFreccia> result = new ArrayList<TransazioneFreccia>();
		try {
			if (callableStatementTFRList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementTFRList = Helper.prepareCall(connection, schema, "PYTFRSP_LST_BY_DTRA");
				callableStatementTFRList = prepareCall("PYTFRSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementTFRList.setDate(1, dataTransazione);	
			callableStatementTFRList.setString(2, codSoc);	
			if (callableStatementTFRList.execute()) {
				ResultSet data = callableStatementTFRList.getResultSet();
				while (data.next())
					result.add(TransazioneFreccia.getBean(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void deleteTFR(TransazioneFreccia transazioneFreccia) throws DaoException {		
		try	{
			if (callableStatementTFRDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementTFRDel = Helper.prepareCall(connection, schema, "PYTFRSP_DEL");
				callableStatementTFRDel = prepareCall("PYTFRSP_DEL");
				//fine LP PGNTBOLDER-1
			}	
			callableStatementTFRDel.setString(1, transazioneFreccia.getChiaveTransazioneDettaglio());
			callableStatementTFRDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<TransazioneIci> getListTIC(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<TransazioneIci> result = new ArrayList<TransazioneIci>();
		try {
			if (callableStatementTICList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementTICList = Helper.prepareCall(connection, schema, "PYTICSP_LST_BY_DTRA");
				callableStatementTICList = prepareCall("PYTICSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementTICList.setDate(1, dataTransazione);	
			callableStatementTICList.setString(2, codSoc);
			if (callableStatementTICList.execute()) {
				ResultSet data = callableStatementTICList.getResultSet();
				while (data.next())
					result.add(TransazioneIci.getBean(data));			
			}
			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void deleteTIC(TransazioneIci transazioneIci) throws DaoException {
		try	{
			if (callableStatementTICDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementTICDel = Helper.prepareCall(connection, schema, "PYTICSP_DEL");
				callableStatementTICDel = prepareCall("PYTICSP_DEL");
				//fine LP PGNTBOLDER-1
			}		
			callableStatementTICDel.setString(1, transazioneIci.getChiaveTransazioneIci());
			callableStatementTICDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<MovimentoDiCassa> getListMDC(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<MovimentoDiCassa> result = new ArrayList<MovimentoDiCassa>();
		try {
			if (callableStatementMDCList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMDCList = Helper.prepareCall(connection, schema, "PYMDCSP_LST_BY_DTRA");
				callableStatementMDCList = prepareCall("PYMDCSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementMDCList.setDate(1, dataTransazione);		
			callableStatementMDCList.setString(2, codSoc);	
			if (callableStatementMDCList.execute()) {
				ResultSet data = callableStatementMDCList.getResultSet();
				while (data.next())
					result.add(new MovimentoDiCassa(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void insertMDC(MovimentoDiCassa movimentoDiCassa) throws DaoException{
		try {
			if (callableStatementMDCIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMDCIns = Helper.prepareCall(connection, schema, "PYMDCSP_INS2");
				callableStatementMDCIns = prepareCall("PYMDCSP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementMDCIns.setLong(1, movimentoDiCassa.getId());	
			callableStatementMDCIns.setString(2, movimentoDiCassa.getCodiceSocieta());
			callableStatementMDCIns.setString(3, movimentoDiCassa.getCutecute());
			callableStatementMDCIns.setString(4, movimentoDiCassa.getSiglaProvincia());
			callableStatementMDCIns.setLong(5, movimentoDiCassa.getIdGiornale());
			callableStatementMDCIns.setString(6, movimentoDiCassa.getConto());
			callableStatementMDCIns.setString(7, movimentoDiCassa.getStatoSospeso());
			callableStatementMDCIns.setString(8, movimentoDiCassa.getNumDocumento());
			callableStatementMDCIns.setString(9, movimentoDiCassa.getCliente());
			callableStatementMDCIns.setBigDecimal(10, movimentoDiCassa.getImporto());
			callableStatementMDCIns.setString(11, movimentoDiCassa.getRendicontato());
			callableStatementMDCIns.setString(12, movimentoDiCassa.getRegolarizzato());
			callableStatementMDCIns.setLong(13, movimentoDiCassa.getProgressivoDoc());
			callableStatementMDCIns.setString(14, movimentoDiCassa.getNumBolletta());
			callableStatementMDCIns.setTimestamp(15,new java.sql.Timestamp( movimentoDiCassa.getDataMovimento().getTimeInMillis()));		
			callableStatementMDCIns.setTimestamp(16,new java.sql.Timestamp( movimentoDiCassa.getDataValuta().getTimeInMillis()));	
			callableStatementMDCIns.setString(17, movimentoDiCassa.getTipoEsecuzione());
			callableStatementMDCIns.setString(18, movimentoDiCassa.getCodiceRiferimento());
			callableStatementMDCIns.setString(19, movimentoDiCassa.getCausale());
			callableStatementMDCIns.setString(20, movimentoDiCassa.getOperatoreReg());
			callableStatementMDCIns.setTimestamp(21,new java.sql.Timestamp( movimentoDiCassa.getDataRegolarizzazione().getTimeInMillis()));
			callableStatementMDCIns.setString(22, movimentoDiCassa.getNota());
			callableStatementMDCIns.setString(23, movimentoDiCassa.getTipoAnomalia());
			callableStatementMDCIns.setString(24, movimentoDiCassa.getSquadratura());
			callableStatementMDCIns.setTimestamp(25,new java.sql.Timestamp( movimentoDiCassa.getDataRendicontazione().getTimeInMillis()));
			callableStatementMDCIns.execute();		
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public void deleteMDC(MovimentoDiCassa movimentoDiCassa) throws DaoException {		
		try	{
			if (callableStatementMDCDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementMDCDel = Helper.prepareCall(connection, schema, "PYMDCSP_DEL");
				callableStatementMDCDel = prepareCall("PYMDCSP_DEL");
				//fine LP PGNTBOLDER-1
			}
			//callableStatementMDCDel = Helper.prepareCall(connection, schema, "PYMDCSP_DEL"); //LP PGNTBOLDER-1
			callableStatementMDCDel.setString(1, String.valueOf(movimentoDiCassa.getId()));
			callableStatementMDCDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<GiornaleDiCassa> getListGDC(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<GiornaleDiCassa> result = new ArrayList<GiornaleDiCassa>();
		try {
			if (callableStatementGDCList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementGDCList = Helper.prepareCall(connection, schema, "PYGDCSP_LST_BY_DTRA");
				callableStatementGDCList = prepareCall("PYGDCSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementGDCList.setDate(1, dataTransazione);	
			callableStatementGDCList.setString(2, codSoc);	
			if (callableStatementGDCList.execute()) {
				ResultSet data = callableStatementGDCList.getResultSet();
				while (data.next())
					result.add(new GiornaleDiCassa(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void insertGDC(GiornaleDiCassa giornaleDiCassa) throws DaoException{
		try {
			if (callableStatementGDCIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementGDCIns = Helper.prepareCall(connection, schema, "PYGDCSP_INS2");
				callableStatementGDCIns = prepareCall("PYGDCSP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementGDCIns.setLong(1, giornaleDiCassa.getId());	
			callableStatementGDCIns.setString(2, giornaleDiCassa.getCodSocieta());
			callableStatementGDCIns.setString(3, giornaleDiCassa.getCodUtente());
			callableStatementGDCIns.setString(4, giornaleDiCassa.getCodEnte());			
			callableStatementGDCIns.setString(5, giornaleDiCassa.getProvenienza());
			callableStatementGDCIns.setString(6, giornaleDiCassa.getIdFlusso());		
			callableStatementGDCIns.setInt(7, giornaleDiCassa.getEsercizio());
			callableStatementGDCIns.setTimestamp(8, new java.sql.Timestamp(giornaleDiCassa.getDataGiornale().getTimeInMillis()));
			callableStatementGDCIns.setTimestamp(9, new java.sql.Timestamp(giornaleDiCassa.getDataGiornaleDa().getTimeInMillis()));
			callableStatementGDCIns.setTimestamp(10, new java.sql.Timestamp(giornaleDiCassa.getDataGiornaleA().getTimeInMillis()));	
			callableStatementGDCIns.execute();
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public void deleteGDC(GiornaleDiCassa giornaleDiCassa) throws DaoException {		
		try	{
			if (callableStatementGDCDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementGDCDel = Helper.prepareCall(connection, schema, "PYGDCSP_DEL");
				callableStatementGDCDel = prepareCall("PYGDCSP_DEL");
				//fine LP PGNTBOLDER-1
			}	
			callableStatementGDCDel.setString(1, String.valueOf(giornaleDiCassa.getId()));
			callableStatementGDCDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public void deleteATM(TransazioneAtm transazioneAtm) throws DaoException {		
		try	{
			if (callableStatementATMDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementATMDel = Helper.prepareCall(connection, schema, "PYATMSP_DEL");
				callableStatementATMDel = prepareCall("PYATMSP_DEL");
				//fine LP PGNTBOLDER-1
			}	
			callableStatementATMDel.setString(1, String.valueOf(transazioneAtm.getChiaveTransazioneInterna()));
			callableStatementATMDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public void deletePOS(POS pos) throws DaoException {		
		try	{
			if (callableStatementPOSDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementPOSDel = Helper.prepareCall(connection, schema, "PYPOSSP_DEL");
				callableStatementPOSDel = prepareCall("PYPOSSP_DEL");
				//fine LP PGNTBOLDER-1
			}	
			callableStatementPOSDel.setString(1, String.valueOf(pos.getChiaveTransazione()));
			callableStatementPOSDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<AssociazioneTransazioni> getListRMT(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<AssociazioneTransazioni> result = new ArrayList<AssociazioneTransazioni>();
		try {
			if (callableStatementRMTList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRMTList = Helper.prepareCall(connection, schema, "PYRMTSP_LST_BY_DTRA");
				callableStatementRMTList = prepareCall("PYRMTSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRMTList.setDate(1, dataTransazione);
			callableStatementRMTList.setString(2, codSoc);
			if (callableStatementRMTList.execute()) {
				ResultSet data = callableStatementRMTList.getResultSet();
				while (data.next())
					result.add(new AssociazioneTransazioni(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void insertRMT(AssociazioneTransazioni collegamentoTransazione) throws DaoException{
		try {
			if (callableStatementRMTIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRMTIns = Helper.prepareCall(connection, schema, "PYRMTSP_INS2");
				callableStatementRMTIns = prepareCall("PYRMTSP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRMTIns.setLong(1, collegamentoTransazione.getIdMdc());	
			callableStatementRMTIns.setLong(2, collegamentoTransazione.getIdNodoScript());				
			callableStatementRMTIns.execute();		
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public void deleteRMT(AssociazioneTransazioni collegamentoTransazione) throws DaoException {
		try	{
			if (callableStatementRMTDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRMTDel = Helper.prepareCall(connection, schema, "PYRMTSP_DEL");
				callableStatementRMTDel = prepareCall("PYRMTSP_DEL");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRMTDel.setString(1, String.valueOf(collegamentoTransazione.getIdMdc()));
			callableStatementRMTDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<AssociazioneFlussi> getListRMF(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<AssociazioneFlussi> result = new ArrayList<AssociazioneFlussi>();
		try {
			if (callableStatementRMFList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRMFList = Helper.prepareCall(connection, schema, "PYRMFSP_LST_BY_DTRA");
				callableStatementRMFList = prepareCall("PYRMFSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRMFList.setDate(1, dataTransazione);	
			callableStatementRMFList.setString(2, codSoc);
			if (callableStatementRMFList.execute()) {
				ResultSet data = callableStatementRMFList.getResultSet();
				while (data.next())
					result.add(new AssociazioneFlussi(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}	

	public void insertRMF(AssociazioneFlussi collegamentoFlussi) throws DaoException{
		try {
			if (callableStatementRMFIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRMFIns = Helper.prepareCall(connection, schema, "PYRMFSP_INS2");
				callableStatementRMFIns = prepareCall("PYRMFSP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRMFIns.setLong(1, collegamentoFlussi.getIdMdc());	
			callableStatementRMFIns.setLong(2, collegamentoFlussi.getIdQuadratura());						
			callableStatementRMFIns.execute();		
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public void deleteRMF(AssociazioneFlussi collegamentoFlussi) throws DaoException {
		try	{
			if (callableStatementRMFDel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRMFDel = Helper.prepareCall(connection, schema, "PYRMFSP_DEL");
				callableStatementRMFDel = prepareCall("PYRMFSP_DEL");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRMFDel.setString(1, String.valueOf(collegamentoFlussi.getIdMdc()));
			callableStatementRMFDel.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public boolean insertREN(FlussiRen fr) throws DaoException {
		try {
			if (callableStatementRENIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRENIns = Helper.prepareCall(connection, schema, "PYRENSP_INS2");
				callableStatementRENIns = prepareCall("PYRENSP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRENIns.setString(1,fr.getChiaveRendicontazione());
			callableStatementRENIns.setString(2, fr.getTipologiaFlusso());
			callableStatementRENIns.setString(3, fr.getCodiceSocieta());
			callableStatementRENIns.setString(4, fr.getCodiceUtente());
			callableStatementRENIns.setString(5, fr.getChiaveEnte());
			callableStatementRENIns.setTimestamp(6, new java.sql.Timestamp(fr.getDataCreazioneFlusso().getTime()));
			callableStatementRENIns.setInt(7, fr.getProgressivoFlusso());
			callableStatementRENIns.setString(8, fr.getCodiceTiplogiaServizio());
			callableStatementRENIns.setString(9, fr.getCodiceImpostaServizio());
			callableStatementRENIns.setString(10, fr.getChiaveGateway());
			callableStatementRENIns.setString(11, fr.getNumeroContoCorrentePostale());
			callableStatementRENIns.setString(12, fr.getNomeFile());
			callableStatementRENIns.setString(13, fr.getFlagInvioMail());
			callableStatementRENIns.setString(14, fr.getFlagInvioFtp());
			callableStatementRENIns.setString(15, fr.getOperatoreUltimoAggiornamento());
			callableStatementRENIns.setString(16, fr.getChiaveFlussoContabilita());
			callableStatementRENIns.registerOutParameter(17, Types.INTEGER);
			callableStatementRENIns.execute();
			int numrighe =  callableStatementRENIns.getInt(17);
			if (numrighe == 1) return true;
			else return false;
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public boolean insertRPT (NodoSpcRpt nodoSpcRpt) throws DaoException {
		try	{
			if (callableStatementRPTIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementRPTIns = Helper.prepareCall(connection, schema, "PYRPTSP_INS2");
				callableStatementRPTIns = prepareCall("PYRPTSP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementRPTIns.setString(1, nodoSpcRpt.getId().toString());
			callableStatementRPTIns.setString(2, nodoSpcRpt.getChiaveTra());
			callableStatementRPTIns.setString(3, nodoSpcRpt.getCodSocieta());
			callableStatementRPTIns.setString(4, nodoSpcRpt.getCodUtente());
			callableStatementRPTIns.setString(5, nodoSpcRpt.getCodiceIuv());
			callableStatementRPTIns.setString(6, nodoSpcRpt.getCodiceTabella());
			callableStatementRPTIns.setString(7, nodoSpcRpt.getChiaveTabella());
			callableStatementRPTIns.setBigDecimal(8, nodoSpcRpt.getImporto());
			callableStatementRPTIns.setString(9, nodoSpcRpt.getRpt());
			callableStatementRPTIns.setString(10, nodoSpcRpt.getRptEsito());
			callableStatementRPTIns.setString(11, nodoSpcRpt.getRptFirma());
			callableStatementRPTIns.setString(12, nodoSpcRpt.getRt());
			callableStatementRPTIns.setString(13, nodoSpcRpt.getRtEsito());
			callableStatementRPTIns.setString(14, nodoSpcRpt.getRtFirma());
			callableStatementRPTIns.setString(15, nodoSpcRpt.getCodContestoPagamento());
			callableStatementRPTIns.setString(16, nodoSpcRpt.getIdDominio());
			callableStatementRPTIns.setString(17, nodoSpcRpt.getIdPSP());
			callableStatementRPTIns.setString(18, nodoSpcRpt.getIdIntermediarioPSP());
			callableStatementRPTIns.setString(19, nodoSpcRpt.getIdCanalePSP());
			callableStatementRPTIns.setTimestamp(20, new java.sql.Timestamp(nodoSpcRpt.getDataIuv().getTime()));
			callableStatementRPTIns.setString(21, nodoSpcRpt.getIdSessioneCarrello());
			callableStatementRPTIns.setString(22, nodoSpcRpt.getIdentificativoTipoDovuto());
			callableStatementRPTIns.setString(23, nodoSpcRpt.getIdentificativoUnivocoDovuto());
			callableStatementRPTIns.setString(24, nodoSpcRpt.getIdQuadratura()==BigInteger.ZERO?null:nodoSpcRpt.getIdQuadratura().toString());
			callableStatementRPTIns.setString(25, nodoSpcRpt.getStatoQuadratura());
			callableStatementRPTIns.setString(26, nodoSpcRpt.getStatoProtocollo());
			callableStatementRPTIns.setString(27, nodoSpcRpt.getCodiceErroreComunicazione());
			callableStatementRPTIns.setString(28, nodoSpcRpt.getEsitoComunicazione());
			callableStatementRPTIns.setString(29, nodoSpcRpt.getIdTemporaneo());
			callableStatementRPTIns.setString(30, nodoSpcRpt.getNumeroprotocolloRT());
			callableStatementRPTIns.registerOutParameter(31, Types.INTEGER);
			callableStatementRPTIns.execute();
			int numrighe =  callableStatementRPTIns.getInt(31);
			if (numrighe == 1) return true;
			else return false;
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public ArrayList<TransazioneAtm> getListATM(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<TransazioneAtm> result = new ArrayList<TransazioneAtm>();
		try {
			if (callableStatementATMList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementATMList = Helper.prepareCall(connection, schema, "PYATMSP_LST_BY_DTRA");
				callableStatementATMList = prepareCall("PYATMSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementATMList.setDate(1, dataTransazione);		
			callableStatementATMList.setString(2, codSoc);
			if (callableStatementATMList.execute()) {
				ResultSet data = callableStatementATMList.getResultSet();
				while (data.next())
					result.add(TransazioneAtm.getBean(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<POS> getListPOS(Date dataTransazione, String codSoc) throws DaoException{
		ArrayList<POS> result = new ArrayList<POS>();
		try {
			if (callableStatementPOSList == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementPOSList = Helper.prepareCall(connection, schema, "PYPOSSP_LST_BY_DTRA");
				callableStatementPOSList = prepareCall("PYPOSSP_LST_BY_DTRA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementPOSList.setDate(1, dataTransazione);
			callableStatementPOSList.setString(2, codSoc);
			if (callableStatementPOSList.execute()) {
				ResultSet data = callableStatementPOSList.getResultSet();
				while (data.next())
					result.add(POS.getBean(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		return result;
	}

	public void insertTRA(Transazione transazione) throws DaoException, SQLException {
		try {
			if (callableStatementTRAIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementTRAIns = Helper.prepareCall(connection, schema, "PYTRASP_INS2");
				callableStatementTRAIns = prepareCall("PYTRASP_INS2");
				//fine LP PGNTBOLDER-1
			}
			callableStatementTRAIns.setString(1, transazione.getChiaveTransazione());
			callableStatementTRAIns.setString(2, transazione.getCodiceSocieta());
			callableStatementTRAIns.setString(3, transazione.getChiaveGatewayDiPagamento());
			callableStatementTRAIns.setTimestamp(4, new java.sql.Timestamp(transazione.getDataInizioTransazione().getTime()));
			callableStatementTRAIns.setTimestamp(5, new java.sql.Timestamp(transazione.getDataEffettivoPagamentoSuGateway().getTime()));
			callableStatementTRAIns.setString(6, transazione.getFlagEsitoTransazione());
			callableStatementTRAIns.setString(7, transazione.getCodiceIdentificativoBanca());
			callableStatementTRAIns.setString(8, transazione.getCodiceAutorizzazioneBanca());
			callableStatementTRAIns.setString(9, transazione.getIndirizzoIpTerminalePagamento());
			callableStatementTRAIns.setString(10, transazione.getEmailContribuente());
			callableStatementTRAIns.setString(11, transazione.getNumeroSmsContribuente());
			callableStatementTRAIns.setString(12, transazione.getDenominazioneContribuentePerInvioPostaOrdinaria());
			callableStatementTRAIns.setString(13, transazione.getIndirizzoContribuentePerInvioPostaOrdinaria());
			callableStatementTRAIns.setString(14, transazione.getCapContribuentePerInvioPostaOrdinaria());
			callableStatementTRAIns.setString(15, transazione.getCittaContribuentePerInvioPostaOrdinaria());
			callableStatementTRAIns.setString(16, transazione.getProvinciaContribuentePerInvioPostaOrdinaria());
			callableStatementTRAIns.setString(17, transazione.getInvioNotificaBollettiniPerEmail());
			callableStatementTRAIns.setString(18, transazione.getInvioAutorizzazioneBancaPerEmailContribuente());
			callableStatementTRAIns.setString(19, transazione.getInvioAutorizzazioneBancaPerEmailAmministratore());
			callableStatementTRAIns.setString(20, transazione.getInvioNotificaAutorizzazioneBancaPerSms());
			callableStatementTRAIns.setString(21, transazione.getInvioNotificaPerPostaOrdinaria());
			callableStatementTRAIns.setTimestamp(22, new java.sql.Timestamp(transazione.getDataAllineamentoBatchTransazione().getTime()));
			callableStatementTRAIns.setString(23, transazione.getCodiceOrdineGateway());
			callableStatementTRAIns.setBigDecimal(24, transazione.getImportoTotaleTransazione());
			callableStatementTRAIns.setBigDecimal(25, transazione.getImportoCostoTransazione());
			callableStatementTRAIns.setBigDecimal(26, transazione.getImportoCostoGateway());
			callableStatementTRAIns.setBigDecimal(27, transazione.getImportoCostoSpeseDiNotifica());
			callableStatementTRAIns.setString(28, transazione.getChiaveTransazioneSistemaEsterno());
			callableStatementTRAIns.setString(29, transazione.getStatoRendicontazione());
			callableStatementTRAIns.setString(30, transazione.getNoteGeneriche());
			callableStatementTRAIns.setLong(31, transazione.getChiaveQuadratura());
			callableStatementTRAIns.setString(32, transazione.getStatoQuadratura());
			callableStatementTRAIns.setString(33, transazione.getInvioNotificaStatoPagamentoEmailContribuente());
			callableStatementTRAIns.setString(34, transazione.getInvioNotificaStatoPagamentoEmailAmministratore());
			callableStatementTRAIns.setString(35, transazione.getStatoRiversamento());
			callableStatementTRAIns.setDate(36, new java.sql.Date(transazione.getDataRiversamento().getTime()));
			callableStatementTRAIns.setString(37, transazione.getOpertoreUltimoAggiornamento());
			callableStatementTRAIns.setBigDecimal(38, transazione.getImportoCostoTransazioneEnte());
			callableStatementTRAIns.setString(39, transazione.getTipoStorno());
			callableStatementTRAIns.setInt(40, transazione.getNumeroTentativiPagamento());
			callableStatementTRAIns.setString(41, transazione.getFlagRiversamentoAutomatico());
			callableStatementTRAIns.setString(42, transazione.getOperatoreInserimento());
			callableStatementTRAIns.setString(43, transazione.getCampoOpzionale1());
			callableStatementTRAIns.setString(44, transazione.getCampoOpzionale2());
			callableStatementTRAIns.setString(45, transazione.getCampoOpzionale2());
			callableStatementTRAIns.setTimestamp(46, new java.sql.Timestamp(transazione.getDataAccredito().getTime()));
			callableStatementTRAIns.setString(47, transazione.getNumeroRiferimentoOrdineGateway());
			callableStatementTRAIns.executeUpdate();
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public boolean insertSEUSR(SeUser bean, String codSoc) throws Exception {
		if(bean == null) return false;
		try {	
			if (callableStatementSEUSRIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementSEUSRIns = Helper.prepareCall(connection, schema, "SEUSRSP_INS_PIVA");	//connessione al db SEC00DB0
				callableStatementSEUSRIns = prepareCall("SEUSRSP_INS_PIVA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementSEUSRIns.setString(1, bean.getUsername());
			callableStatementSEUSRIns.setString(2, bean.getTipologiaUtente());
			callableStatementSEUSRIns.setString(3, bean.getNome());
			callableStatementSEUSRIns.setString(4, bean.getCognome());
			callableStatementSEUSRIns.setString(5, bean.getSesso());
			callableStatementSEUSRIns.setString(6, bean.getCodiceFiscale());
			callableStatementSEUSRIns.setDate(7, new java.sql.Date(bean.getDataNascita().getTime()));
			callableStatementSEUSRIns.setString(8, bean.getProvincia());
			callableStatementSEUSRIns.setString(9, bean.getCodbelfNascita());
			callableStatementSEUSRIns.setString(10, bean.getFlagesteroNascita());
			callableStatementSEUSRIns.setString(11, bean.getTipoDocumento());
			callableStatementSEUSRIns.setString(12, bean.getNumeroDocumento());
			callableStatementSEUSRIns.setString(13, bean.getEnteRilascio());
			callableStatementSEUSRIns.setDate(14, new java.sql.Date(bean.getDataRilascio().getTime()));
			callableStatementSEUSRIns.setString(15, bean.getIndirizzoResidenza());
			callableStatementSEUSRIns.setString(16, bean.getProvinciaResidenza());
			callableStatementSEUSRIns.setString(17, bean.getCodbelfResidenza());
			callableStatementSEUSRIns.setString(18, bean.getCapcomuneResidenza());
			callableStatementSEUSRIns.setString(19, bean.getFlagesteroResidenza());
			callableStatementSEUSRIns.setString(20, bean.getIndirizzoDomicilio());
			callableStatementSEUSRIns.setString(21, bean.getProvinciaDomicilio());
			callableStatementSEUSRIns.setString(22, bean.getCodbelfDomicilio());
			callableStatementSEUSRIns.setString(23, bean.getCapcomuneDomicilio());
			callableStatementSEUSRIns.setString(24, bean.getFlagesteroDomicilio());
			callableStatementSEUSRIns.setString(25, bean.getEmailPersonaFisica());
			callableStatementSEUSRIns.setString(26, bean.getEmailPECPersonaFisica());
			callableStatementSEUSRIns.setString(27, bean.getCellularePersonaFisica());
			callableStatementSEUSRIns.setString(28, bean.getTelefonoPersonaFisica());
			callableStatementSEUSRIns.setString(29, bean.getFaxPersonaFisica());
			callableStatementSEUSRIns.setString(30, bean.getPartitaIVA());
			callableStatementSEUSRIns.setString(31, bean.getRagioneSociale());
			callableStatementSEUSRIns.setString(32, bean.getCodiceClassificazioneMerceologica());
			callableStatementSEUSRIns.setString(33, bean.getNumeroAutorizzazione());
			callableStatementSEUSRIns.setString(34, bean.getIndirizzoSedeLegale());
			callableStatementSEUSRIns.setString(35, bean.getProvinciaSedeLegale());
			callableStatementSEUSRIns.setString(36, bean.getCodbelfSedeLegale());
			callableStatementSEUSRIns.setString(37, bean.getCapcomuneSedeLegale());
			callableStatementSEUSRIns.setString(38, bean.getFlagesteroSedeLegale());
			callableStatementSEUSRIns.setString(39, bean.getIndirizzoSedeOperativa());
			callableStatementSEUSRIns.setString(40, bean.getProvinciaSedeOperativa());
			callableStatementSEUSRIns.setString(41, bean.getCodbelfSedeOperativa());
			callableStatementSEUSRIns.setString(42, bean.getCapcomuneSedeOperativa());
			callableStatementSEUSRIns.setString(43, bean.getEmailPartitaIVA());
			callableStatementSEUSRIns.setString(44, bean.getEmailPECPartitaIVA());
			callableStatementSEUSRIns.setString(45, bean.getCellularePartitaIVA());
			callableStatementSEUSRIns.setString(46, bean.getTelefonoPartitaIVA());
			callableStatementSEUSRIns.setString(47, bean.getFaxPartitaIVA());
			callableStatementSEUSRIns.setString(48, bean.getPassword());
			callableStatementSEUSRIns.setString(49, bean.getPassword2());
			callableStatementSEUSRIns.setString(50, bean.getPassword3());
			callableStatementSEUSRIns.setString(51, bean.getPuk());
			callableStatementSEUSRIns.setString(52, bean.getUtenzaAttiva());
			callableStatementSEUSRIns.setTimestamp(53, bean.getDataInizioValiditaUtenza());
			callableStatementSEUSRIns.setTimestamp(54, bean.getDataFineValiditaUtenza());
			callableStatementSEUSRIns.setString(55, bean.getPrimoAccesso());
			callableStatementSEUSRIns.setTimestamp(56, bean.getDataScadenzaPassword());
			callableStatementSEUSRIns.setTimestamp(57, bean.getDataUltimoAccesso());
			callableStatementSEUSRIns.setTimestamp(58, bean.getDataInserimentoUtenza());
			callableStatementSEUSRIns.setString(59, bean.getNote());
			callableStatementSEUSRIns.setString(60, bean.getOperatoreUltimoAggiornamento());
			callableStatementSEUSRIns.setString(61, codSoc);
			callableStatementSEUSRIns.setString(62, bean.getFlagOperatoreBackOffice());
			callableStatementSEUSRIns.registerOutParameter(63, Types.INTEGER);
			callableStatementSEUSRIns.executeUpdate();
			if (callableStatementSEUSRIns.getInt(63) == 1) return true;
			else return false;
		} catch (SQLException x) {
			throw new Exception(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		finally {
		}
	}

	public boolean updateSEUSR(SeUser bean, String codSoc) throws Exception {
		if(bean == null) return false;
		try {	
			if (callableStatementSEUSRUpdate == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementSEUSRUpdate = Helper.prepareCall(connection, schema, "SEUSRSP_UPD_PIVA");	//connessione al db SEC00DB0
				callableStatementSEUSRUpdate = prepareCall("SEUSRSP_UPD_PIVA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementSEUSRUpdate.setString(1, bean.getUsername());
			callableStatementSEUSRUpdate.setString(2, bean.getTipologiaUtente());
			callableStatementSEUSRUpdate.setString(3, bean.getNome());
			callableStatementSEUSRUpdate.setString(4, bean.getCognome());
			callableStatementSEUSRUpdate.setString(5, bean.getSesso());
			callableStatementSEUSRUpdate.setString(6, bean.getCodiceFiscale());
			callableStatementSEUSRUpdate.setDate(7, new java.sql.Date(bean.getDataNascita().getTime()));
			callableStatementSEUSRUpdate.setString(8, bean.getProvincia());
			callableStatementSEUSRUpdate.setString(9, bean.getCodbelfNascita());
			callableStatementSEUSRUpdate.setString(10, bean.getFlagesteroNascita());
			callableStatementSEUSRUpdate.setString(11, bean.getTipoDocumento());
			callableStatementSEUSRUpdate.setString(12, bean.getNumeroDocumento());
			callableStatementSEUSRUpdate.setString(13, bean.getEnteRilascio());
			callableStatementSEUSRUpdate.setDate(14, new java.sql.Date(bean.getDataRilascio().getTime()));
			callableStatementSEUSRUpdate.setString(15, bean.getIndirizzoResidenza());
			callableStatementSEUSRUpdate.setString(16, bean.getProvinciaResidenza());
			callableStatementSEUSRUpdate.setString(17, bean.getCodbelfResidenza());
			callableStatementSEUSRUpdate.setString(18, bean.getCapcomuneResidenza());
			callableStatementSEUSRUpdate.setString(19, bean.getFlagesteroResidenza());
			callableStatementSEUSRUpdate.setString(20, bean.getIndirizzoDomicilio());
			callableStatementSEUSRUpdate.setString(21, bean.getProvinciaDomicilio());
			callableStatementSEUSRUpdate.setString(22, bean.getCodbelfDomicilio());
			callableStatementSEUSRUpdate.setString(23, bean.getCapcomuneDomicilio());
			callableStatementSEUSRUpdate.setString(24, bean.getFlagesteroDomicilio());
			callableStatementSEUSRUpdate.setString(25, bean.getEmailPersonaFisica());
			callableStatementSEUSRUpdate.setString(26, bean.getEmailPECPersonaFisica());
			callableStatementSEUSRUpdate.setString(27, bean.getCellularePersonaFisica());
			callableStatementSEUSRUpdate.setString(28, bean.getTelefonoPersonaFisica());
			callableStatementSEUSRUpdate.setString(29, bean.getFaxPersonaFisica());
			callableStatementSEUSRUpdate.setString(30, bean.getPartitaIVA());
			callableStatementSEUSRUpdate.setString(31, bean.getRagioneSociale());
			callableStatementSEUSRUpdate.setString(32, bean.getCodiceClassificazioneMerceologica());
			callableStatementSEUSRUpdate.setString(33, bean.getNumeroAutorizzazione());
			callableStatementSEUSRUpdate.setString(34, bean.getIndirizzoSedeLegale());
			callableStatementSEUSRUpdate.setString(35, bean.getProvinciaSedeLegale());
			callableStatementSEUSRUpdate.setString(36, bean.getCodbelfSedeLegale());
			callableStatementSEUSRUpdate.setString(37, bean.getCapcomuneSedeLegale());
			callableStatementSEUSRUpdate.setString(38, bean.getFlagesteroSedeLegale());
			callableStatementSEUSRUpdate.setString(39, bean.getIndirizzoSedeOperativa());
			callableStatementSEUSRUpdate.setString(40, bean.getProvinciaSedeOperativa());
			callableStatementSEUSRUpdate.setString(41, bean.getCodbelfSedeOperativa());
			callableStatementSEUSRUpdate.setString(42, bean.getCapcomuneSedeOperativa());
			callableStatementSEUSRUpdate.setString(43, bean.getEmailPartitaIVA());
			callableStatementSEUSRUpdate.setString(44, bean.getEmailPECPartitaIVA());
			callableStatementSEUSRUpdate.setString(45, bean.getCellularePartitaIVA());
			callableStatementSEUSRUpdate.setString(46, bean.getTelefonoPartitaIVA());
			callableStatementSEUSRUpdate.setString(47, bean.getFaxPartitaIVA());
			callableStatementSEUSRUpdate.setString(48, bean.getPassword());
			callableStatementSEUSRUpdate.setString(49, bean.getPassword2());
			callableStatementSEUSRUpdate.setString(50, bean.getPassword3());
			callableStatementSEUSRUpdate.setString(51, bean.getPuk());
			callableStatementSEUSRUpdate.setString(52, bean.getUtenzaAttiva());
			callableStatementSEUSRUpdate.setTimestamp(53, bean.getDataInizioValiditaUtenza());
			callableStatementSEUSRUpdate.setTimestamp(54, bean.getDataFineValiditaUtenza());
			callableStatementSEUSRUpdate.setString(55, bean.getPrimoAccesso());
			callableStatementSEUSRUpdate.setTimestamp(56, bean.getDataScadenzaPassword());
			callableStatementSEUSRUpdate.setTimestamp(57, bean.getDataUltimoAccesso());
			callableStatementSEUSRUpdate.setTimestamp(58, bean.getDataInserimentoUtenza());
			callableStatementSEUSRUpdate.setString(59, bean.getNote());
			callableStatementSEUSRUpdate.setString(60, bean.getOperatoreUltimoAggiornamento());
			callableStatementSEUSRUpdate.setString(61, codSoc);
			callableStatementSEUSRUpdate.setString(62, bean.getFlagOperatoreBackOffice());
			callableStatementSEUSRUpdate.registerOutParameter(63, Types.INTEGER);
			callableStatementSEUSRUpdate.executeUpdate();
			if (callableStatementSEUSRUpdate.getInt(63) == 1) return true;
			else return false;
		} catch (SQLException x) {
			throw new Exception(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		finally {
		}
	}

	public SeUser selectSEUSR(String username, String codiceSocieta) throws Exception{
		ResultSet data = null;
		SeUser seUser = null;
		try {
			if (callableStatementSEUSRSel == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementSEUSRSel = Helper.prepareCall(connection, schema, "SEUSRSP_SEL_PIVA");
				callableStatementSEUSRSel = prepareCall("SEUSRSP_SEL_PIVA");
				//fine LP PGNTBOLDER-1
			}
			callableStatementSEUSRSel.setString(1, username);
			callableStatementSEUSRSel.setString(2, codiceSocieta);
			if (callableStatementSEUSRSel.execute()) {
				data = callableStatementSEUSRSel.getResultSet();
				seUser = SeUser.getBean(data);
				return seUser;
			}
			return null;
		} catch (SQLException x) {
			throw new Exception(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
		finally
		{
		}
	}

	public void insertSESOC(String codSoc, String descrSoc) throws DaoException, SQLException {
		try {
			if (callableStatementSESOCIns == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementSESOCIns = Helper.prepareCall(connection, schema, "SESOCSP_INS");
				callableStatementSESOCIns = prepareCall("SESOCSP_INS");
				//fine LP PGNTBOLDER-1
			}
			callableStatementSESOCIns.setString(1, codSoc);
			callableStatementSESOCIns.setString(2, descrSoc);
			callableStatementSESOCIns.registerOutParameter(3, Types.VARCHAR);
			callableStatementSESOCIns.executeUpdate();
			
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		} catch (SQLException x) {
			throw new DaoException(x);
		}
	}

	public void dropTrigger() throws DaoException {
		try	{
			if (callableStatementDropTrigger == null) {
				//inizio LP PGNTBOLDER-1
				//callableStatementDropTrigger = Helper.prepareCall(connection, schema, "PYTRASP_DROP_TRIGGER3");
				callableStatementDropTrigger = prepareCall("PYTRASP_DROP_TRIGGER3");
				//fine LP PGNTBOLDER-1
			}
			callableStatementDropTrigger.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP PGNTBOLDER-1
		} catch (ProcedureReflectorException x) {
			throw new DaoException(x);
		//fine LP PGNTBOLDER-1
		}
	}

	public void createTrigger(String dbSchema) throws SQLException {
		//inizio LP 20240820 - PGNTBOLDER-1
		//Statement stmt = connection.createStatement();
		Statement stmt = getConnection().createStatement();
		if (DriverType.isPostgres(getConnection())) {		
			stmt.execute(
						"CREATE OR REPLACE TRIGGER PYTRAT3 " +
						"BEFORE DELETE ON  " + dbSchema + ".PYTRATB " +  
						"FOR EACH ROW "+ 
						"WHEN ((OLD.TRA_FTRAFESI = '1' OR OLD.TRA_CTRACOPE = '2') AND UPPER(OLD.tra_ctracope) != 'MIGRAZIONE')" +
						"EXECUTE FUNCTION SWP_GenError();"		
						);
		} else {
		//fine LP 20240820 - PGNTBOLDER-1
			stmt.execute("CREATE DEFINER=`admin`@`%` TRIGGER `" + dbSchema + "`.`PYTRAT3` BEFORE DELETE ON `" + dbSchema + "`.`PYTRATB` FOR EACH ROW "+
					"BEGIN "+
						"IF (OLD.TRA_FTRAFESI IN ('1', '2') AND OLD.TRA_CTRACOPE != 'Migrazione') THEN "+
							"CALL SWP_GenError('Impossibile cancellare una transazione con stato 1 o 2!!'); "+
						"END IF; "+
					"END");
			
		//inizio LP 20240820 - PGNTBOLDER-1
		}
		//fine LP 20240820 - PGNTBOLDER-1
	}

}
