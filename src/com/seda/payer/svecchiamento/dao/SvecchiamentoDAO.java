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

import com.seda.data.dao.DAOHelper;
import com.seda.data.helper.HelperException;
import com.seda.data.procedure.reflection.DriverType;
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
	
//inizio LP 20241001 - PGNTBOLDER-1
//	protected CallableStatement callableStatementTRAList = null;
//	protected CallableStatement callableStatementTRADel = null;
//	protected CallableStatement callableStatementTRAIns = null;
//	protected CallableStatement callableStatementRENList = null;
//	protected CallableStatement callableStatementRENIns = null;
//	protected CallableStatement callableStatementRENDel = null;
//	protected CallableStatement callableStatementTFRList = null;
//	protected CallableStatement callableStatementTFRDel = null;
//	protected CallableStatement callableStatementTDTList = null;
//	protected CallableStatement callableStatementTDTDel = null;
//	protected CallableStatement callableStatementTICList = null;
//	protected CallableStatement callableStatementTICDel = null;
//	protected CallableStatement callableStatementQUNList = null;
//	protected CallableStatement callableStatementQUNIns = null;
//	protected CallableStatement callableStatementQUNDel = null;
//	protected CallableStatement callableStatementRPTList = null;
//	protected CallableStatement callableStatementRPTIns = null;
//	protected CallableStatement callableStatementRPTDel = null;
//	protected CallableStatement callableStatementMINList = null;
//	protected CallableStatement callableStatementMINDel = null;
//	protected CallableStatement callableStatementMINIns = null;
//	protected CallableStatement callableStatementMIPList = null;
//	protected CallableStatement callableStatementMIPDel = null;
//	protected CallableStatement callableStatementMIPIns = null;
//	protected CallableStatement callableStatementMPSList = null;
//	protected CallableStatement callableStatementMPSDel = null;
//	protected CallableStatement callableStatementMPSIns = null;
//	protected CallableStatement callableStatementGDCList = null;
//	protected CallableStatement callableStatementGDCIns = null;
//	protected CallableStatement callableStatementGDCDel = null;
//	protected CallableStatement callableStatementMDCList = null;
//	protected CallableStatement callableStatementMDCIns = null;
//	protected CallableStatement callableStatementMDCDel = null;
//	protected CallableStatement callableStatementRMTList = null;
//	protected CallableStatement callableStatementRMTIns = null;
//	protected CallableStatement callableStatementRMTDel = null;
//	protected CallableStatement callableStatementRMFList = null;
//	protected CallableStatement callableStatementRMFIns = null;
//	protected CallableStatement callableStatementRMFDel = null;
//	protected CallableStatement callableStatementATMList = null;
//	protected CallableStatement callableStatementATMDel = null;
//	protected CallableStatement callableStatementPOSList = null;
//	protected CallableStatement callableStatementPOSDel = null;
//	protected CallableStatement callableStatementMNAList = null;
//	protected CallableStatement callableStatementMNADel = null;
//	protected CallableStatement callableStatementMNAIns = null;
//	protected CallableStatement callableStatementUSRIns = null;
//	protected CallableStatement callableStatementUSRLst = null;
//	protected CallableStatement callableStatementSOCLst = null;
//	protected CallableStatement callableStatementUTELst = null;
//	protected CallableStatement callableStatementPRFList = null;
//	protected CallableStatement callableStatementAPCList = null;
//	protected CallableStatement callableStatementANEList = null;
//	protected CallableStatement callableStatementANEIns = null;
//	protected CallableStatement callableStatementENTList = null;
//	protected CallableStatement callableStatementBOLList = null;
//	protected CallableStatement callableStatementCFEList = null;
//	protected CallableStatement callableStatementCFSList = null;
//	protected CallableStatement callableStatementCANList = null;
//	protected CallableStatement callableStatementCESList = null;
//	protected CallableStatement callableStatementTSEList = null;
//	protected CallableStatement callableStatementGTWList = null;
//	protected CallableStatement callableStatementGTWIns = null;
//	protected CallableStatement callableStatementCARList = null;
//	protected CallableStatement callableStatementSERList = null;
//	protected CallableStatement callableStatementSEUSRIns = null;
//	protected CallableStatement callableStatementSEUSRSel = null;
//	protected CallableStatement callableStatementSEUSRUpdate = null;
//	protected CallableStatement callableStatementSESOCIns = null;
//	protected CallableStatement callableStatementDropTrigger = null;
	//Note. Unico CallableStatement
	protected CallableStatement callableStatement = null;
//fine LP 20241001 - PGNTBOLDER-1
	
	public SvecchiamentoDAO(Connection connection, String schema) {
		//inizio LP 20240820 - PGNTBOLDER-1
		//super();
		//this.connection = connection;
		//this.schema = schema;
		super(connection, schema);
		//fine LP 20240820 - PGNTBOLDER-1
	}

	public ArrayList<Company> getListSOC() throws DaoException {
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<Company> result = new ArrayList<Company>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatementSOCLst = Helper.prepareCall(connection, schema, "PYSOCSP_LST_ALL");
				callableStatement = prepareCall(false, "PYSOCSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new Company(data));
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<User> getListUTE() throws DaoException {
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<User> result = new ArrayList<User>();	
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatementUTELst = Helper.prepareCall(connection, schema, "PYUTESP_LST_ALL");
				callableStatement = prepareCall(false, "PYUTESP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new User(data));
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<PyUser> getListUSR() throws DaoException {
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<PyUser> result = new ArrayList<PyUser>();	
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYUSRSP_LST_ALL");
				callableStatement = prepareCall(false, "PYUSRSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(PyUser.getBean(data, false));
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
			//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void insertUSR(PyUser pyUser) throws DaoException, SQLException {
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYUSRSP_INS2");
				callableStatement = prepareCall(false, "PYUSRSP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, pyUser.getChiaveUtente());
			callableStatement.setString(2, pyUser.getUserName());
			callableStatement.setString(3, pyUser.getUserProfile());
			callableStatement.setString(4, pyUser.getFlagAttivazione());
			callableStatement.setString(5, pyUser.getCodiceSocieta());
			callableStatement.setString(6, pyUser.getCodiceUtente());
			callableStatement.setString(7, pyUser.getChiaveEnteConsorzio());
			callableStatement.setString(8, pyUser.getChiaveEnteConsorziato());
			callableStatement.setString(9, pyUser.getDownloadFlussiRendicontazione());
			callableStatement.setString(10, pyUser.getInvioFlussiRendicontazioneViaFtp());
			callableStatement.setString(11, pyUser.getInvioFlussiRendicontazioneViaEmail());
			callableStatement.setString(12, pyUser.getAzioniPerTransazioniOK());
			callableStatement.setString(13, pyUser.getAzioniPerTransazioniKO());
			callableStatement.setString(14, pyUser.getAzioniPerRiconciliazioneManuale());
			callableStatement.setString(15, pyUser.getAttivazioneEstrattoContoManager());
			callableStatement.setString(16, pyUser.getAbilitazioneProfiloRiversamento());
			callableStatement.setString(17, pyUser.getAbilitazioneMultiUtente());
			callableStatement.setString(18, pyUser.getOperatoreUltimoAggiornamento());
			callableStatement.setString(19, pyUser.getListaTipologieServizio());
			callableStatement.setString(20, pyUser.getMailContoGestione().equals("") ? "N" : pyUser.getMailContoGestione());
			callableStatement.setString(21, pyUser.getEntePertinenza());
			callableStatement.setString(22, pyUser.getGruppoAgenzia());
			callableStatement.setString(23, pyUser.getAssociazioniProvvisorieRiconciliazionemt());
			callableStatement.setString(24, pyUser.getAssociazioniDefinitiveRiconciliazionemt());
			callableStatement.setString(25, pyUser.getMail());
			callableStatement.setString(26, pyUser.getMailPec());
			callableStatement.setString(27, pyUser.getPinCodeMail());
			callableStatement.setString(28, pyUser.getPinCodePec());
			callableStatement.setString(29, pyUser.getFlagValidazioneMail());
			callableStatement.setString(30, pyUser.getFlagValidazionePec());
			callableStatement.registerOutParameter(31, Types.INTEGER);
			callableStatement.executeUpdate();
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}
	
	public void insertMNA(AssociazioneUtenteAppl associazioneUtenteAppl) throws DaoException, SQLException {
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMNASP_INS");
				callableStatement = prepareCall(false, "PYMNASP_INS");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, associazioneUtenteAppl.getChiaveUtente());
			callableStatement.setString(2, associazioneUtenteAppl.getCodiceApplicazione());
			callableStatement.registerOutParameter(3, Types.INTEGER);
			callableStatement.executeUpdate();
			
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<AssociazioneUtenteAppl> getListMNA() throws DaoException {
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<AssociazioneUtenteAppl> result = new ArrayList<AssociazioneUtenteAppl>();	
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMNASP_LST_ALL");
				callableStatement = prepareCall(false, "PYMNASP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new AssociazioneUtenteAppl(data));
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void deleteMNA(AssociazioneUtenteAppl associazioneUtenteAppl) throws DaoException {
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMNASP_DEL");
				callableStatement = prepareCall(false, "PYMNASP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, associazioneUtenteAppl.getChiaveUtente());
			callableStatement.setString(2, associazioneUtenteAppl.getCodiceApplicazione());
			callableStatement.execute();
		} catch(Exception e){
			System.out.println("Eliminazione MNA: "+e.getMessage());
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<ApplProf> getListPRF() throws DaoException {
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<ApplProf> result = new ArrayList<ApplProf>();		
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYPRFSP_LST_ALL");
				callableStatement = prepareCall(false, "PYPRFSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new ApplProf(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<AnagProvCom> getListAPC() throws DaoException {
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<AnagProvCom> result = new ArrayList<AnagProvCom>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYAPCSP_LST_ALL");
				callableStatement = prepareCall(false, "PYAPCSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new AnagProvCom(data));
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<AnagEnte> getListANE() throws DaoException {
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<AnagEnte> result = new ArrayList<AnagEnte>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYANESP_LST_ALL");
				callableStatement = prepareCall(false, "PYANESP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new AnagEnte(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void insertANE(AnagEnte anagEnte) throws DaoException, SQLException {
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYANESP_INS2");
				callableStatement = prepareCall(false, "PYANESP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, anagEnte.getChiaveEnte()); 
			callableStatement.setString(2, anagEnte.getCodiceEnte()); 
			callableStatement.setString(3, anagEnte.getTipoUfficio());
			callableStatement.setString(4, anagEnte.getCodiceUfficio());
			callableStatement.setString(5, anagEnte.getCodiceTipoEnte());
			callableStatement.setString(6, anagEnte.getDescrizioneEnte());
			callableStatement.setString(7, anagEnte.getDescrizioneUfficio());
			callableStatement.setString(8,anagEnte.getAnagProvCom().getCodiceBelfiore());
			callableStatement.setString(9,anagEnte.getCodiceRuoliErariali());			
			callableStatement.setDate(10, anagEnte.getDataDecorrenza());
			callableStatement.setString(11, anagEnte.getUfficioStatale());
			callableStatement.setString(12, anagEnte.getCodiceOperatore());	
			callableStatement.executeUpdate();
			
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void insertGTW(GatewayPagamento gatewayPagamento) throws DaoException, SQLException {
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYGTWSP_INS2");
				callableStatement = prepareCall(false, "PYGTWSP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, gatewayPagamento.getUser().getCompany().getCompanyCode());
			callableStatement.setString(2, gatewayPagamento.getUser().getUserCode());
			callableStatement.setString(3, gatewayPagamento.getCanale().getChiaveCanalePagamento());
			callableStatement.setString(4, gatewayPagamento.getDescrizioneGateway());
			callableStatement.setString(5, gatewayPagamento.getPathImgLogo());		
			callableStatement.setString(6, gatewayPagamento.getUrlSitoWebGateway());
			callableStatement.setString(7, gatewayPagamento.getTipoGateway());
			callableStatement.setString(8, gatewayPagamento.getEmailNotificaAdmin());
			callableStatement.setString(9, gatewayPagamento.getUrlApiEndpoint());
			callableStatement.setString(10, gatewayPagamento.getApiUser());
			callableStatement.setString(11, gatewayPagamento.getApiPassword());
			callableStatement.setString(12, gatewayPagamento.getApiSignature());
			callableStatement.setString(13, gatewayPagamento.getUrlApiImage());
			callableStatement.setString(14, gatewayPagamento.getApiVersion());
			callableStatement.setString(15, gatewayPagamento.getUrlApiRedirect());
			callableStatement.setString(16, gatewayPagamento.getUrlApiCancel());
			callableStatement.setString(17, gatewayPagamento.getCodiceNegozio());
			callableStatement.setString(18, gatewayPagamento.getCodiceMacAvvio());
			callableStatement.setString(19, gatewayPagamento.getCodiceMacEsito());
			callableStatement.setString(20, gatewayPagamento.getTipoAutorizzazione());
			callableStatement.setString(21, gatewayPagamento.getTipoContabilizzazione());
			callableStatement.setString(22, gatewayPagamento.getOpzioniAggiuntive());
			callableStatement.setString(23, gatewayPagamento.getCarta().getCodiceCartaPagamento());
			callableStatement.setString(24, gatewayPagamento.getFlagAttivazione());
			callableStatement.setString(25, gatewayPagamento.getCodiceSIAAziendaDestinataria());
			callableStatement.setString(26, gatewayPagamento.getCodiceCINBancaMittente());
			callableStatement.setString(27, gatewayPagamento.getCodiceABIBancaMittente());
			callableStatement.setString(28, gatewayPagamento.getCodiceCABBancaMittente());
			callableStatement.setString(29, gatewayPagamento.getCodiceContoCorrente());
			callableStatement.setInt(30, gatewayPagamento.getDeltaGiorniDataContabile());
			callableStatement.setBigDecimal(31, gatewayPagamento.getImportoScostamento());
			callableStatement.setString(32, gatewayPagamento.getCodiceOperatore());
			callableStatement.setString(33, gatewayPagamento.getChiaveGateway());
			callableStatement.executeUpdate();
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<Ente> getListENT() throws DaoException {
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<Ente> result = new ArrayList<Ente>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYENTSP_LST_ALL");
				callableStatement = prepareCall(false, "PYENTSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new Ente(data));
			}	
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<Bollettino> getListBOL() throws DaoException {
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<Bollettino> result = new ArrayList<Bollettino>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYBOLSP_LST");
				callableStatement = prepareCall(false, "PYBOLSP_LST");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new Bollettino(data));
			}	
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<ConfigUtenteTipoServizioEnte> getListCFE() throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<ConfigUtenteTipoServizioEnte> result = new ArrayList<ConfigUtenteTipoServizioEnte>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYCFESP_LST_ALL");
				callableStatement = prepareCall(false, "PYCFESP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new ConfigUtenteTipoServizioEnte(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<ConfigUtenteTipoServizio> getListCFS() throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<ConfigUtenteTipoServizio> result = new ArrayList<ConfigUtenteTipoServizio>();		
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYCFSSP_LST_ALL");
				callableStatement = prepareCall(false, "PYCFSSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new ConfigUtenteTipoServizio(data));
			}	
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<CanalePagamento> getListCAN() throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<CanalePagamento> result = new ArrayList<CanalePagamento>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYCANSP_LST_ALL");
				callableStatement = prepareCall(false, "PYCANSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new CanalePagamento(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<AbilitaCanalePagamentoTipoServizioEnte> getListCES() throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<AbilitaCanalePagamentoTipoServizioEnte> result = new ArrayList<AbilitaCanalePagamentoTipoServizioEnte>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYCESSP_LST_ALL");
				callableStatement = prepareCall(false, "PYCESSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new AbilitaCanalePagamentoTipoServizioEnte(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<TipologiaServizio> getListTSE() throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<TipologiaServizio> result = new ArrayList<TipologiaServizio>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYTSESP_LST_ALL");
				callableStatement = prepareCall(false, "PYTSESP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new TipologiaServizio(data));
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<GatewayPagamento> getListGTW() throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<GatewayPagamento> result = new ArrayList<GatewayPagamento>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYGTWSP_LST_ALL");
				callableStatement = prepareCall(false, "PYGTWSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new GatewayPagamento(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<CartaPagamento> getListCAR() throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<CartaPagamento> result = new ArrayList<CartaPagamento>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYCARSP_LST_ALL");
				callableStatement = prepareCall(false, "PYCARSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new CartaPagamento(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<Transazione> getListTRA(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<Transazione> result = new ArrayList<Transazione>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYTRASP_LST_ALL");
				callableStatement = prepareCall(false, "PYTRASP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(Transazione.getBean_ExtendedKPOF(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void deleteTRA(Transazione transazione) throws DaoException {
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYTRASP_DEL2");
				callableStatement = prepareCall(false, "PYTRASP_DEL2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, transazione.getChiaveTransazione());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<AnagServizi> getListSER() throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<AnagServizi> result = new ArrayList<AnagServizi>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYSERSP_LST_ALL");
				callableStatement = prepareCall(false, "PYSERSP_LST_ALL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new AnagServizi(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}	
		return result;
	}

	public ArrayList<TransazioneIV> getListTDT(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<TransazioneIV> result = new ArrayList<TransazioneIV>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYTDTSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYTDTSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);
			callableStatement.setString(2, codSoc);	
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(TransazioneIV.getBean(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void deleteTDT(TransazioneIV transazioneIV) throws DaoException	{
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYTDTSP_DEL");
				callableStatement = prepareCall(false, "PYTDTSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, transazioneIV.getChiaveTransazioneDettaglio());
			callableStatement.registerOutParameter(2, Types.INTEGER);
			callableStatement.executeUpdate();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<QuadraturaNodo> getListQUN(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<QuadraturaNodo> result = new ArrayList<QuadraturaNodo>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYQUNSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYQUNSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);
			callableStatement.setString(2, codSoc);	
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new QuadraturaNodo(data));	
			}
		
		} catch (SQLException x) {
			throw new DaoException(x); 
			
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void insertQUN(QuadraturaNodo quadraturaNodo) throws DaoException{
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYQUNSP_INS");
				callableStatement = prepareCall(false, "PYQUNSP_INS");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, quadraturaNodo.getKeyQuadratura());	
			callableStatement.setString(2, quadraturaNodo.getCodSocieta());		
			callableStatement.setString(3, quadraturaNodo.getCodUtente());	
			callableStatement.setString(4, quadraturaNodo.getKeyGateway());		
			callableStatement.setString(5, quadraturaNodo.getNomeFileTxt());				
			callableStatement.setTimestamp(6, quadraturaNodo.getDtInizioRend());			
			callableStatement.setTimestamp(7, quadraturaNodo.getDtFineRend());				
			callableStatement.setLong(8, quadraturaNodo.getNumTransazioni());		
			callableStatement.setBigDecimal(9, quadraturaNodo.getImpSquadratura());				
			callableStatement.setString(10, quadraturaNodo.getStatoSquadratura());	
			callableStatement.setString(11, quadraturaNodo.getMovimentoElab());				
			callableStatement.setTimestamp(12, quadraturaNodo.getDtChiusuraFlusso());			
			callableStatement.setString(13, quadraturaNodo.getCodOperatore());			
			callableStatement.setString(14, quadraturaNodo.getVersOggetto());			
			callableStatement.setString(15, quadraturaNodo.getIdFlusso());		
			callableStatement.setTimestamp(16, quadraturaNodo.getDtflusso());			
			callableStatement.setString(17, quadraturaNodo.getIdUnivocoRegol());
			callableStatement.setDate(18, quadraturaNodo.getDtregol());		
			callableStatement.setString(19, quadraturaNodo.getTipoIdUnivocoMitt());
			callableStatement.setString(20, quadraturaNodo.getCodIdUnivocoMitt());			
			callableStatement.setString(21, quadraturaNodo.getDenomMitt());				
			callableStatement.setString(22, quadraturaNodo.getTipoIdUnivocoRice());			
			callableStatement.setString(23, quadraturaNodo.getCodIdUnivocoRice());			
			callableStatement.setString(24, quadraturaNodo.getDenomRice());			
			callableStatement.setLong(25, quadraturaNodo.getNumTotPagamenti());			
			callableStatement.setBigDecimal(26, quadraturaNodo.getImpTotPagamenti());			
			callableStatement.setString(27, quadraturaNodo.getKeyEnte());	
			callableStatement.setLong(28, quadraturaNodo.getNumTransazioniRecuperate());				
			callableStatement.execute();		
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void deleteQUN(QuadraturaNodo quadraturaNodo) throws DaoException {
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYQUNSP_DEL");
				callableStatement = prepareCall(false, "PYQUNSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, quadraturaNodo.getKeyQuadratura());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<FlussiRen> getListREN(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<FlussiRen> result = new ArrayList<FlussiRen>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRENSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYRENSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(FlussiRen.getBean_Extended(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void deleteREN(FlussiRen flussiRen) throws DaoException {		
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRENSP_DEL");
				callableStatement = prepareCall(false, "PYRENSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, flussiRen.getChiaveRendicontazione());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<NodoSpcRpt> getListRPT(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<NodoSpcRpt> result = new ArrayList<NodoSpcRpt>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRPTSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYRPTSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add( NodoSpcRpt.getBean_Extended(data));			
			}
			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void deleteRPT(NodoSpcRpt nodoSpcRpt) throws DaoException {		
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRPTSP_DEL");
				callableStatement = prepareCall(false, "PYRPTSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, nodoSpcRpt.getId().longValue());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<ModuloIntegrazionePagamentiNodo> getListMIN(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<ModuloIntegrazionePagamentiNodo> result = new ArrayList<ModuloIntegrazionePagamentiNodo>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMINSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYMINSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new ModuloIntegrazionePagamentiNodo(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void deleteMIN(ModuloIntegrazionePagamentiNodo moduloIntegrazionePagamenti) throws DaoException {		
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMINSP_DEL");
				callableStatement = prepareCall(false, "PYMINSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, moduloIntegrazionePagamenti.getChiaveTransazione());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<ModuloIntegrazionePagamentiPaymentStatus> getListMPS(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<ModuloIntegrazionePagamentiPaymentStatus> result = new ArrayList<ModuloIntegrazionePagamentiPaymentStatus>();	
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMPSSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYMPSSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new ModuloIntegrazionePagamentiPaymentStatus(data));			
			}			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void insertMPS(ModuloIntegrazionePagamentiPaymentStatus moduloIntPagamentiStatus) throws DaoException{
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMPSSP_INS2");
				callableStatement = prepareCall(false, "PYMPSSP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, moduloIntPagamentiStatus.getChiaveTransazione());	
			callableStatement.setInt(2, moduloIntPagamentiStatus.getGruppoTentativiNotifica());		
			callableStatement.setInt(3, moduloIntPagamentiStatus.getNumeroTentativoNotifica());			
			callableStatement.setString(4, moduloIntPagamentiStatus.getModalitaNotifica());
			callableStatement.setString(5, moduloIntPagamentiStatus.getEsitoTransazione());
			callableStatement.setString(6, moduloIntPagamentiStatus.getIdPortale());	
			callableStatement.setString(7, moduloIntPagamentiStatus.getNumeroOperazione());	
			callableStatement.setString(8, moduloIntPagamentiStatus.getXmlPaymentStatus());			
			callableStatement.setString(9, moduloIntPagamentiStatus.getXmlPaymentData());	
			callableStatement.setString(10, moduloIntPagamentiStatus.getXmlCommitMessage());				
			callableStatement.setString(11, moduloIntPagamentiStatus.getEsitoNotifica());
			callableStatement.setString(12, moduloIntPagamentiStatus.getS2SResponseHtmlStatusCode());		
			callableStatement.setString(13, moduloIntPagamentiStatus.getS2SResponseMessage());		
			callableStatement.setString(14, moduloIntPagamentiStatus.getParametriOpzionali1());			
			callableStatement.setString(15, moduloIntPagamentiStatus.getParametriOpzionali2());				
			callableStatement.setString(16, moduloIntPagamentiStatus.getOperatoreUltimoAggiornamento());			
							
			callableStatement.execute();		
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void deleteMPS(ModuloIntegrazionePagamentiPaymentStatus moduloIntPagamentiStatus) throws DaoException {		
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMPSSP_DEL");
				callableStatement = prepareCall(false, "PYMPSSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, moduloIntPagamentiStatus.getChiaveTransazione());
			callableStatement.setLong(2, moduloIntPagamentiStatus.getGruppoTentativiNotifica());
			callableStatement.setLong(3, moduloIntPagamentiStatus.getNumeroTentativoNotifica());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<ModuloIntegrazionePagamenti> getListMIP(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<ModuloIntegrazionePagamenti> result = new ArrayList<ModuloIntegrazionePagamenti>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMIPSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYMIPSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new ModuloIntegrazionePagamenti(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void deleteMIP(ModuloIntegrazionePagamenti moduloIntegrazionePagamenti) throws DaoException {
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMIPSP_DEL");
				callableStatement = prepareCall(false, "PYMIPSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, moduloIntegrazionePagamenti.getChiaveTransazione());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<TransazioneFreccia> getListTFR(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<TransazioneFreccia> result = new ArrayList<TransazioneFreccia>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYTFRSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYTFRSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);	
			callableStatement.setString(2, codSoc);	
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(TransazioneFreccia.getBean(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void deleteTFR(TransazioneFreccia transazioneFreccia) throws DaoException {		
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYTFRSP_DEL");
				callableStatement = prepareCall(false, "PYTFRSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, transazioneFreccia.getChiaveTransazioneDettaglio());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<TransazioneIci> getListTIC(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<TransazioneIci> result = new ArrayList<TransazioneIci>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYTICSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYTICSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);	
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(TransazioneIci.getBean(data));			
			}
			
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void deleteTIC(TransazioneIci transazioneIci) throws DaoException {
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYTICSP_DEL");
				callableStatement = prepareCall(false, "PYTICSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}	
			callableStatement.setString(1, transazioneIci.getChiaveTransazioneIci());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<MovimentoDiCassa> getListMDC(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<MovimentoDiCassa> result = new ArrayList<MovimentoDiCassa>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMDCSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYMDCSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);		
			callableStatement.setString(2, codSoc);	
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new MovimentoDiCassa(data));			
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void insertMDC(MovimentoDiCassa movimentoDiCassa) throws DaoException{
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMDCSP_INS2");
				callableStatement = prepareCall(false, "PYMDCSP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, movimentoDiCassa.getId());	
			callableStatement.setString(2, movimentoDiCassa.getCodiceSocieta());
			callableStatement.setString(3, movimentoDiCassa.getCutecute());
			callableStatement.setString(4, movimentoDiCassa.getSiglaProvincia());
			callableStatement.setLong(5, movimentoDiCassa.getIdGiornale());
			callableStatement.setString(6, movimentoDiCassa.getConto());
			callableStatement.setString(7, movimentoDiCassa.getStatoSospeso());
			callableStatement.setLong(8, Long.parseLong(movimentoDiCassa.getNumDocumento()));
			callableStatement.setString(9, movimentoDiCassa.getCliente());
			callableStatement.setBigDecimal(10, movimentoDiCassa.getImporto());
			callableStatement.setString(11, movimentoDiCassa.getRendicontato());
			callableStatement.setString(12, movimentoDiCassa.getRegolarizzato());
			callableStatement.setLong(13, movimentoDiCassa.getProgressivoDoc());
			callableStatement.setString(14, movimentoDiCassa.getNumBolletta());
			callableStatement.setTimestamp(15,new java.sql.Timestamp( movimentoDiCassa.getDataMovimento().getTimeInMillis()));		
			callableStatement.setTimestamp(16,new java.sql.Timestamp( movimentoDiCassa.getDataValuta().getTimeInMillis()));	
			callableStatement.setString(17, movimentoDiCassa.getTipoEsecuzione());
			callableStatement.setString(18, movimentoDiCassa.getCodiceRiferimento());
			callableStatement.setString(19, movimentoDiCassa.getCausale());
			callableStatement.setString(20, movimentoDiCassa.getOperatoreReg());
			callableStatement.setTimestamp(21,new java.sql.Timestamp( movimentoDiCassa.getDataRegolarizzazione().getTimeInMillis()));
			callableStatement.setString(22, movimentoDiCassa.getNota());
			callableStatement.setString(23, movimentoDiCassa.getTipoAnomalia());
			callableStatement.setString(24, movimentoDiCassa.getSquadratura());
			callableStatement.setTimestamp(25,new java.sql.Timestamp( movimentoDiCassa.getDataRendicontazione().getTimeInMillis()));
			callableStatement.execute();		
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void deleteMDC(MovimentoDiCassa movimentoDiCassa) throws DaoException {		
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYMDCSP_DEL");
				callableStatement = prepareCall(false, "PYMDCSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			//callableStatement = Helper.prepareCall(connection, schema, "PYMDCSP_DEL"); //LP 20240909 - PGNTBOLDER-1
			callableStatement.setLong(1, movimentoDiCassa.getId());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<GiornaleDiCassa> getListGDC(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<GiornaleDiCassa> result = new ArrayList<GiornaleDiCassa>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYGDCSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYGDCSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);	
			callableStatement.setString(2, codSoc);	
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new GiornaleDiCassa(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void insertGDC(GiornaleDiCassa giornaleDiCassa) throws DaoException{
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYGDCSP_INS2");
				callableStatement = prepareCall(false, "PYGDCSP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, giornaleDiCassa.getId());	
			callableStatement.setString(2, giornaleDiCassa.getCodSocieta());
			callableStatement.setString(3, giornaleDiCassa.getCodUtente());
			callableStatement.setString(4, giornaleDiCassa.getCodEnte());			
			callableStatement.setString(5, giornaleDiCassa.getProvenienza());
			callableStatement.setString(6, giornaleDiCassa.getIdFlusso());		
			callableStatement.setLong(7, giornaleDiCassa.getEsercizio());
			callableStatement.setTimestamp(8, new java.sql.Timestamp(giornaleDiCassa.getDataGiornale().getTimeInMillis()));
			callableStatement.setTimestamp(9, new java.sql.Timestamp(giornaleDiCassa.getDataGiornaleDa().getTimeInMillis()));
			callableStatement.setTimestamp(10, new java.sql.Timestamp(giornaleDiCassa.getDataGiornaleA().getTimeInMillis()));	
			callableStatement.execute();
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void deleteGDC(GiornaleDiCassa giornaleDiCassa) throws DaoException {		
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYGDCSP_DEL");
				callableStatement = prepareCall(false, "PYGDCSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, giornaleDiCassa.getId());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void deleteATM(TransazioneAtm transazioneAtm) throws DaoException {		
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYATMSP_DEL");
				callableStatement = prepareCall(false, "PYATMSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, String.valueOf(transazioneAtm.getChiaveTransazioneInterna()));
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void deletePOS(POS pos) throws DaoException {		
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYPOSSP_DEL");
				callableStatement = prepareCall(false, "PYPOSSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, String.valueOf(pos.getChiaveTransazione()));
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<AssociazioneTransazioni> getListRMT(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<AssociazioneTransazioni> result = new ArrayList<AssociazioneTransazioni>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRMTSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYRMTSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new AssociazioneTransazioni(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void insertRMT(AssociazioneTransazioni collegamentoTransazione) throws DaoException{
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRMTSP_INS2");
				callableStatement = prepareCall(false, "PYRMTSP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, collegamentoTransazione.getIdMdc());	
			callableStatement.setLong(2, collegamentoTransazione.getIdNodoScript());				
			callableStatement.execute();		
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void deleteRMT(AssociazioneTransazioni collegamentoTransazione) throws DaoException {
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRMTSP_DEL");
				callableStatement = prepareCall(false, "PYRMTSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, collegamentoTransazione.getIdMdc());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<AssociazioneFlussi> getListRMF(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<AssociazioneFlussi> result = new ArrayList<AssociazioneFlussi>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRMFSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYRMFSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);	
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(new AssociazioneFlussi(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}	

	public void insertRMF(AssociazioneFlussi collegamentoFlussi) throws DaoException{
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRMFSP_INS2");
				callableStatement = prepareCall(false, "PYRMFSP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, collegamentoFlussi.getIdMdc());	
			callableStatement.setLong(2, collegamentoFlussi.getIdQuadratura());						
			callableStatement.execute();		
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void deleteRMF(AssociazioneFlussi collegamentoFlussi) throws DaoException {
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRMFSP_DEL");
				callableStatement = prepareCall(false, "PYRMFSP_DEL");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, collegamentoFlussi.getIdMdc());
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public boolean insertREN(FlussiRen fr) throws DaoException {
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRENSP_INS2");
				callableStatement = prepareCall(false, "PYRENSP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1,fr.getChiaveRendicontazione());
			callableStatement.setString(2, fr.getTipologiaFlusso());
			callableStatement.setString(3, fr.getCodiceSocieta());
			callableStatement.setString(4, fr.getCodiceUtente());
			callableStatement.setString(5, fr.getChiaveEnte());
			callableStatement.setTimestamp(6, new java.sql.Timestamp(fr.getDataCreazioneFlusso().getTime()));
			callableStatement.setInt(7, fr.getProgressivoFlusso());
			callableStatement.setString(8, fr.getCodiceTiplogiaServizio());
			callableStatement.setString(9, fr.getCodiceImpostaServizio());
			callableStatement.setString(10, fr.getChiaveGateway());
			callableStatement.setString(11, fr.getNumeroContoCorrentePostale());
			callableStatement.setString(12, fr.getNomeFile());
			callableStatement.setString(13, fr.getFlagInvioMail());
			callableStatement.setString(14, fr.getFlagInvioFtp());
			callableStatement.setString(15, fr.getOperatoreUltimoAggiornamento());
			callableStatement.setString(16, fr.getChiaveFlussoContabilita());
			callableStatement.registerOutParameter(17, Types.INTEGER);
			callableStatement.execute();
			int numrighe =  callableStatement.getInt(17);
			if (numrighe == 1) return true;
			else return false;
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public boolean insertRPT (NodoSpcRpt nodoSpcRpt) throws DaoException {
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYRPTSP_INS2");
				callableStatement = prepareCall(false, "	PYRPTSP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setLong(1, nodoSpcRpt.getId().longValue());
			callableStatement.setString(2, nodoSpcRpt.getChiaveTra());
			callableStatement.setString(3, nodoSpcRpt.getCodSocieta());
			callableStatement.setString(4, nodoSpcRpt.getCodUtente());
			callableStatement.setString(5, nodoSpcRpt.getCodiceIuv());
			callableStatement.setString(6, nodoSpcRpt.getCodiceTabella());
			callableStatement.setString(7, nodoSpcRpt.getChiaveTabella());
			callableStatement.setBigDecimal(8, nodoSpcRpt.getImporto());
			callableStatement.setString(9, nodoSpcRpt.getRpt());
			callableStatement.setString(10, nodoSpcRpt.getRptEsito());
			callableStatement.setString(11, nodoSpcRpt.getRptFirma());
			callableStatement.setString(12, nodoSpcRpt.getRt());
			callableStatement.setString(13, nodoSpcRpt.getRtEsito());
			callableStatement.setString(14, nodoSpcRpt.getRtFirma());
			callableStatement.setString(15, nodoSpcRpt.getCodContestoPagamento());
			callableStatement.setString(16, nodoSpcRpt.getIdDominio());
			callableStatement.setString(17, nodoSpcRpt.getIdPSP());
			callableStatement.setString(18, nodoSpcRpt.getIdIntermediarioPSP());
			callableStatement.setString(19, nodoSpcRpt.getIdCanalePSP());
			callableStatement.setTimestamp(20, new java.sql.Timestamp(nodoSpcRpt.getDataIuv().getTime()));
			callableStatement.setString(21, nodoSpcRpt.getIdSessioneCarrello());
			callableStatement.setString(22, nodoSpcRpt.getIdentificativoTipoDovuto());
			callableStatement.setString(23, nodoSpcRpt.getIdentificativoUnivocoDovuto());
			callableStatement.setString(24, nodoSpcRpt.getIdQuadratura()==BigInteger.ZERO?null:nodoSpcRpt.getIdQuadratura().toString());
			callableStatement.setString(25, nodoSpcRpt.getStatoQuadratura());
			callableStatement.setString(26, nodoSpcRpt.getStatoProtocollo());
			callableStatement.setString(27, nodoSpcRpt.getCodiceErroreComunicazione());
			callableStatement.setString(28, nodoSpcRpt.getEsitoComunicazione());
			callableStatement.setString(29, nodoSpcRpt.getIdTemporaneo());
			callableStatement.setString(30, nodoSpcRpt.getNumeroprotocolloRT());
			callableStatement.registerOutParameter(31, Types.INTEGER);
			callableStatement.execute();
			int numrighe =  callableStatement.getInt(31);
			if (numrighe == 1) return true;
			else return false;
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public ArrayList<TransazioneAtm> getListATM(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<TransazioneAtm> result = new ArrayList<TransazioneAtm>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYATMSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYATMSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);		
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(TransazioneAtm.getBean(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public ArrayList<POS> getListPOS(Date dataTransazione, String codSoc) throws DaoException{
		ResultSet data = null; //LP 20241001 - PGNTBOLDER-1
		ArrayList<POS> result = new ArrayList<POS>();
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYPOSSP_LST_BY_DTRA");
				callableStatement = prepareCall(false, "PYPOSSP_LST_BY_DTRA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setDate(1, dataTransazione);
			callableStatement.setString(2, codSoc);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
				while (data.next())
					result.add(POS.getBean(data));		
			}
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
		return result;
	}

	public void insertTRA(Transazione transazione) throws DaoException, SQLException {
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "PYTRASP_INS2");
				callableStatement = prepareCall(false, "PYTRASP_INS2");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, transazione.getChiaveTransazione());
			callableStatement.setString(2, transazione.getCodiceSocieta());
			callableStatement.setString(3, transazione.getChiaveGatewayDiPagamento());
			callableStatement.setTimestamp(4, new java.sql.Timestamp(transazione.getDataInizioTransazione().getTime()));
			callableStatement.setTimestamp(5, new java.sql.Timestamp(transazione.getDataEffettivoPagamentoSuGateway().getTime()));
			callableStatement.setString(6, transazione.getFlagEsitoTransazione());
			callableStatement.setString(7, transazione.getCodiceIdentificativoBanca());
			callableStatement.setString(8, transazione.getCodiceAutorizzazioneBanca());
			callableStatement.setString(9, transazione.getIndirizzoIpTerminalePagamento());
			callableStatement.setString(10, transazione.getEmailContribuente());
			callableStatement.setString(11, transazione.getNumeroSmsContribuente());
			callableStatement.setString(12, transazione.getDenominazioneContribuentePerInvioPostaOrdinaria());
			callableStatement.setString(13, transazione.getIndirizzoContribuentePerInvioPostaOrdinaria());
			callableStatement.setString(14, transazione.getCapContribuentePerInvioPostaOrdinaria());
			callableStatement.setString(15, transazione.getCittaContribuentePerInvioPostaOrdinaria());
			callableStatement.setString(16, transazione.getProvinciaContribuentePerInvioPostaOrdinaria());
			callableStatement.setString(17, transazione.getInvioNotificaBollettiniPerEmail());
			callableStatement.setString(18, transazione.getInvioAutorizzazioneBancaPerEmailContribuente());
			callableStatement.setString(19, transazione.getInvioAutorizzazioneBancaPerEmailAmministratore());
			callableStatement.setString(20, transazione.getInvioNotificaAutorizzazioneBancaPerSms());
			callableStatement.setString(21, transazione.getInvioNotificaPerPostaOrdinaria());
			callableStatement.setTimestamp(22, new java.sql.Timestamp(transazione.getDataAllineamentoBatchTransazione().getTime()));
			callableStatement.setString(23, transazione.getCodiceOrdineGateway());
			callableStatement.setBigDecimal(24, transazione.getImportoTotaleTransazione());
			callableStatement.setBigDecimal(25, transazione.getImportoCostoTransazione());
			callableStatement.setBigDecimal(26, transazione.getImportoCostoGateway());
			callableStatement.setBigDecimal(27, transazione.getImportoCostoSpeseDiNotifica());
			callableStatement.setString(28, transazione.getChiaveTransazioneSistemaEsterno());
			callableStatement.setString(29, transazione.getStatoRendicontazione());
			callableStatement.setString(30, transazione.getNoteGeneriche());
			callableStatement.setLong(31, transazione.getChiaveQuadratura());
			callableStatement.setString(32, transazione.getStatoQuadratura());
			callableStatement.setString(33, transazione.getInvioNotificaStatoPagamentoEmailContribuente());
			callableStatement.setString(34, transazione.getInvioNotificaStatoPagamentoEmailAmministratore());
			callableStatement.setString(35, transazione.getStatoRiversamento());
			callableStatement.setDate(36, new java.sql.Date(transazione.getDataRiversamento().getTime()));
			callableStatement.setString(37, transazione.getOpertoreUltimoAggiornamento());
			callableStatement.setBigDecimal(38, transazione.getImportoCostoTransazioneEnte());
			callableStatement.setString(39, transazione.getTipoStorno());
			callableStatement.setInt(40, transazione.getNumeroTentativiPagamento());
			callableStatement.setString(41, transazione.getFlagRiversamentoAutomatico());
			callableStatement.setString(42, transazione.getOperatoreInserimento());
			callableStatement.setString(43, transazione.getCampoOpzionale1());
			callableStatement.setString(44, transazione.getCampoOpzionale2());
			callableStatement.setString(45, transazione.getCampoOpzionale2());
			callableStatement.setTimestamp(46, new java.sql.Timestamp(transazione.getDataAccredito().getTime()));
			callableStatement.setString(47, transazione.getNumeroRiferimentoOrdineGateway());
			callableStatement.executeUpdate();
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public boolean insertSEUSR(SeUser bean, String codSoc) throws Exception {
		if(bean == null) return false;
		try {	
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "SEUSRSP_INS_PIVA");	//connessione al db SEC00DB0
				callableStatement = prepareCall(false, "SEUSRSP_INS_PIVA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, bean.getUsername());
			callableStatement.setString(2, bean.getTipologiaUtente());
			callableStatement.setString(3, bean.getNome());
			callableStatement.setString(4, bean.getCognome());
			callableStatement.setString(5, bean.getSesso());
			callableStatement.setString(6, bean.getCodiceFiscale());
			callableStatement.setDate(7, new java.sql.Date(bean.getDataNascita().getTime()));
			callableStatement.setString(8, bean.getProvincia());
			callableStatement.setString(9, bean.getCodbelfNascita());
			callableStatement.setString(10, bean.getFlagesteroNascita());
			callableStatement.setString(11, bean.getTipoDocumento());
			callableStatement.setString(12, bean.getNumeroDocumento());
			callableStatement.setString(13, bean.getEnteRilascio());
			callableStatement.setDate(14, new java.sql.Date(bean.getDataRilascio().getTime()));
			callableStatement.setString(15, bean.getIndirizzoResidenza());
			callableStatement.setString(16, bean.getProvinciaResidenza());
			callableStatement.setString(17, bean.getCodbelfResidenza());
			callableStatement.setString(18, bean.getCapcomuneResidenza());
			callableStatement.setString(19, bean.getFlagesteroResidenza());
			callableStatement.setString(20, bean.getIndirizzoDomicilio());
			callableStatement.setString(21, bean.getProvinciaDomicilio());
			callableStatement.setString(22, bean.getCodbelfDomicilio());
			callableStatement.setString(23, bean.getCapcomuneDomicilio());
			callableStatement.setString(24, bean.getFlagesteroDomicilio());
			callableStatement.setString(25, bean.getEmailPersonaFisica());
			callableStatement.setString(26, bean.getEmailPECPersonaFisica());
			callableStatement.setString(27, bean.getCellularePersonaFisica());
			callableStatement.setString(28, bean.getTelefonoPersonaFisica());
			callableStatement.setString(29, bean.getFaxPersonaFisica());
			callableStatement.setString(30, bean.getPartitaIVA());
			callableStatement.setString(31, bean.getRagioneSociale());
			callableStatement.setString(32, bean.getCodiceClassificazioneMerceologica());
			callableStatement.setString(33, bean.getNumeroAutorizzazione());
			callableStatement.setString(34, bean.getIndirizzoSedeLegale());
			callableStatement.setString(35, bean.getProvinciaSedeLegale());
			callableStatement.setString(36, bean.getCodbelfSedeLegale());
			callableStatement.setString(37, bean.getCapcomuneSedeLegale());
			callableStatement.setString(38, bean.getFlagesteroSedeLegale());
			callableStatement.setString(39, bean.getIndirizzoSedeOperativa());
			callableStatement.setString(40, bean.getProvinciaSedeOperativa());
			callableStatement.setString(41, bean.getCodbelfSedeOperativa());
			callableStatement.setString(42, bean.getCapcomuneSedeOperativa());
			callableStatement.setString(43, bean.getEmailPartitaIVA());
			callableStatement.setString(44, bean.getEmailPECPartitaIVA());
			callableStatement.setString(45, bean.getCellularePartitaIVA());
			callableStatement.setString(46, bean.getTelefonoPartitaIVA());
			callableStatement.setString(47, bean.getFaxPartitaIVA());
			callableStatement.setString(48, bean.getPassword());
			callableStatement.setString(49, bean.getPassword2());
			callableStatement.setString(50, bean.getPassword3());
			callableStatement.setString(51, bean.getPuk());
			callableStatement.setString(52, bean.getUtenzaAttiva());
			callableStatement.setTimestamp(53, bean.getDataInizioValiditaUtenza());
			callableStatement.setTimestamp(54, bean.getDataFineValiditaUtenza());
			callableStatement.setString(55, bean.getPrimoAccesso());
			callableStatement.setTimestamp(56, bean.getDataScadenzaPassword());
			callableStatement.setTimestamp(57, bean.getDataUltimoAccesso());
			callableStatement.setTimestamp(58, bean.getDataInserimentoUtenza());
			callableStatement.setString(59, bean.getNote());
			callableStatement.setString(60, bean.getOperatoreUltimoAggiornamento());
			callableStatement.setString(61, codSoc);
			callableStatement.setString(62, bean.getFlagOperatoreBackOffice());
			callableStatement.registerOutParameter(63, Types.INTEGER);
			callableStatement.executeUpdate();
			if (callableStatement.getInt(63) == 1) return true;
			else return false;
		} catch (SQLException x) {
			throw new Exception(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public boolean updateSEUSR(SeUser bean, String codSoc) throws Exception {
		if(bean == null) return false;
		try {	
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatement = Helper.prepareCall(connection, schema, "SEUSRSP_UPD_PIVA");	//connessione al db SEC00DB0
				callableStatement = prepareCall(false, "SEUSRSP_UPD_PIVA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, bean.getUsername());
			callableStatement.setString(2, bean.getTipologiaUtente());
			callableStatement.setString(3, bean.getNome());
			callableStatement.setString(4, bean.getCognome());
			callableStatement.setString(5, bean.getSesso());
			callableStatement.setString(6, bean.getCodiceFiscale());
			callableStatement.setDate(7, new java.sql.Date(bean.getDataNascita().getTime()));
			callableStatement.setString(8, bean.getProvincia());
			callableStatement.setString(9, bean.getCodbelfNascita());
			callableStatement.setString(10, bean.getFlagesteroNascita());
			callableStatement.setString(11, bean.getTipoDocumento());
			callableStatement.setString(12, bean.getNumeroDocumento());
			callableStatement.setString(13, bean.getEnteRilascio());
			callableStatement.setDate(14, new java.sql.Date(bean.getDataRilascio().getTime()));
			callableStatement.setString(15, bean.getIndirizzoResidenza());
			callableStatement.setString(16, bean.getProvinciaResidenza());
			callableStatement.setString(17, bean.getCodbelfResidenza());
			callableStatement.setString(18, bean.getCapcomuneResidenza());
			callableStatement.setString(19, bean.getFlagesteroResidenza());
			callableStatement.setString(20, bean.getIndirizzoDomicilio());
			callableStatement.setString(21, bean.getProvinciaDomicilio());
			callableStatement.setString(22, bean.getCodbelfDomicilio());
			callableStatement.setString(23, bean.getCapcomuneDomicilio());
			callableStatement.setString(24, bean.getFlagesteroDomicilio());
			callableStatement.setString(25, bean.getEmailPersonaFisica());
			callableStatement.setString(26, bean.getEmailPECPersonaFisica());
			callableStatement.setString(27, bean.getCellularePersonaFisica());
			callableStatement.setString(28, bean.getTelefonoPersonaFisica());
			callableStatement.setString(29, bean.getFaxPersonaFisica());
			callableStatement.setString(30, bean.getPartitaIVA());
			callableStatement.setString(31, bean.getRagioneSociale());
			callableStatement.setString(32, bean.getCodiceClassificazioneMerceologica());
			callableStatement.setString(33, bean.getNumeroAutorizzazione());
			callableStatement.setString(34, bean.getIndirizzoSedeLegale());
			callableStatement.setString(35, bean.getProvinciaSedeLegale());
			callableStatement.setString(36, bean.getCodbelfSedeLegale());
			callableStatement.setString(37, bean.getCapcomuneSedeLegale());
			callableStatement.setString(38, bean.getFlagesteroSedeLegale());
			callableStatement.setString(39, bean.getIndirizzoSedeOperativa());
			callableStatement.setString(40, bean.getProvinciaSedeOperativa());
			callableStatement.setString(41, bean.getCodbelfSedeOperativa());
			callableStatement.setString(42, bean.getCapcomuneSedeOperativa());
			callableStatement.setString(43, bean.getEmailPartitaIVA());
			callableStatement.setString(44, bean.getEmailPECPartitaIVA());
			callableStatement.setString(45, bean.getCellularePartitaIVA());
			callableStatement.setString(46, bean.getTelefonoPartitaIVA());
			callableStatement.setString(47, bean.getFaxPartitaIVA());
			callableStatement.setString(48, bean.getPassword());
			callableStatement.setString(49, bean.getPassword2());
			callableStatement.setString(50, bean.getPassword3());
			callableStatement.setString(51, bean.getPuk());
			callableStatement.setString(52, bean.getUtenzaAttiva());
			callableStatement.setTimestamp(53, bean.getDataInizioValiditaUtenza());
			callableStatement.setTimestamp(54, bean.getDataFineValiditaUtenza());
			callableStatement.setString(55, bean.getPrimoAccesso());
			callableStatement.setTimestamp(56, bean.getDataScadenzaPassword());
			callableStatement.setTimestamp(57, bean.getDataUltimoAccesso());
			callableStatement.setTimestamp(58, bean.getDataInserimentoUtenza());
			callableStatement.setString(59, bean.getNote());
			callableStatement.setString(60, bean.getOperatoreUltimoAggiornamento());
			callableStatement.setString(61, codSoc);
			callableStatement.setString(62, bean.getFlagOperatoreBackOffice());
			callableStatement.registerOutParameter(63, Types.INTEGER);
			callableStatement.executeUpdate();
			if (callableStatement.getInt(63) == 1) return true;
			else return false;
		} catch (SQLException x) {
			throw new Exception(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public SeUser selectSEUSR(String username, String codiceSocieta) throws Exception{
		ResultSet data = null;
		SeUser seUser = null;
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatementSEUSRSel = Helper.prepareCall(connection, schema, "SEUSRSP_SEL_PIVA");
				callableStatement = prepareCall(false, "SEUSRSP_SEL_PIVA");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, username);
			callableStatement.setString(2, codiceSocieta);
			if (callableStatement.execute()) {
				data = callableStatement.getResultSet();
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
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(data);
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void insertSESOC(String codSoc, String descrSoc) throws DaoException, SQLException {
		try {
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatementSESOCIns = Helper.prepareCall(connection, schema, "SESOCSP_INS");
				callableStatement = prepareCall(false, "SESOCSP_INS");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.setString(1, codSoc);
			callableStatement.setString(2, descrSoc);
			callableStatement.registerOutParameter(3, Types.VARCHAR);
			callableStatement.executeUpdate();
			
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		} catch (SQLException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void dropTrigger() throws DaoException {
		try	{
			//if (callableStatement == null) {		
				//inizio LP 20240909 - PGNTBOLDER-1
				//callableStatementDropTrigger = Helper.prepareCall(connection, schema, "PYTRASP_DROP_TRIGGER3");
				callableStatement = prepareCall(false, "PYTRASP_DROP_TRIGGER3");
				//fine LP 20240909 - PGNTBOLDER-1
			//}
			callableStatement.execute();
		} catch (SQLException x) {
			throw new DaoException(x);
		} catch (IllegalArgumentException x) {
			throw new DaoException(x);
		} catch (HelperException x) {
			throw new DaoException(x);
		//inizio LP 20241001 - PGNTBOLDER-1
		} finally {
			DAOHelper.closeIgnoringException(callableStatement);
			callableStatement = null;
		//fine LP 20241001 - PGNTBOLDER-1
		}
	}

	public void createTrigger(String dbSchema) throws SQLException {
		//inizio LP 20240820 - PGNTBOLDER-1
		//Statement stmt = connection.createStatement();
		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
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
		//inizio LP 20241001 - PGNTBOLDER-1
		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			DAOHelper.closeIgnoringException(stmt);
			stmt = null;
		}
		//fine LP 20241001 - PGNTBOLDER-1
	}
}
