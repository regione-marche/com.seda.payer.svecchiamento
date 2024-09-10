package com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.components;

import java.sql.Connection;
import java.sql.SQLException;

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
import com.seda.payer.core.bean.GatewayPagamento;
import com.seda.payer.core.bean.ModuloIntegrazionePagamenti;
import com.seda.payer.core.bean.ModuloIntegrazionePagamentiNodo;
import com.seda.payer.core.bean.POS;
import com.seda.payer.core.bean.PyUser;
import com.seda.payer.core.bean.TipologiaServizio;
import com.seda.payer.core.bean.Transazione;
import com.seda.payer.core.bean.TransazioneAtm;
import com.seda.payer.core.bean.TransazioneFreccia;
import com.seda.payer.core.bean.TransazioneIV;
import com.seda.payer.core.bean.TransazioneIci;
import com.seda.payer.core.bean.User;
import com.seda.payer.core.dao.AbilitaCanalePagamentoTipoServizioEnteDao;
import com.seda.payer.core.dao.AnagEnteDao;
import com.seda.payer.core.dao.AnagProvComDao;
import com.seda.payer.core.dao.AnagServiziDao;
import com.seda.payer.core.dao.ApplProfDao;
import com.seda.payer.core.dao.BollettinoDao;
import com.seda.payer.core.dao.CanalePagamentoDao;
import com.seda.payer.core.dao.CartaPagamentoDao;
import com.seda.payer.core.dao.CompanyDao;
import com.seda.payer.core.dao.ConfigUtenteTipoServizioDao;
import com.seda.payer.core.dao.ConfigUtenteTipoServizioEnteDao;
import com.seda.payer.core.dao.EnteDao;
import com.seda.payer.core.dao.GatewayPagamentoDao;
import com.seda.payer.core.dao.ModuloIntegrazionePagamentiDao;
import com.seda.payer.core.dao.ModuloIntegrazionePagamentiNodoDao;
import com.seda.payer.core.dao.POSDao;
import com.seda.payer.core.dao.PyUserDao;
import com.seda.payer.core.dao.TipologiaServizioDao;
import com.seda.payer.core.dao.TxTransazioniAtmDao;
import com.seda.payer.core.dao.TxTransazioniDao;
import com.seda.payer.core.dao.TxTransazioniFrecciaDao;
import com.seda.payer.core.dao.TxTransazioniIVDao;
import com.seda.payer.core.dao.TxTransazioniIciDao;
import com.seda.payer.core.dao.UserDao;
import com.seda.payer.core.exception.DaoException;

public class SvecchiamentoArchivioOperativoBL {
	private Connection connection = null;
	private String schema = null;
	

	public SvecchiamentoArchivioOperativoBL(Connection connection, String schema) {
		super();
		this.connection = connection;
		this.schema = schema;
	}

	public void insertUTE(User user) throws DaoException {
		UserDao userDao = new UserDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//userDao.doSave(user, "");
		userDao.doSaveTail(false, user, "");
		//fine LP 20240909 - PGNTBOLDER-1
	}

	public void insertPRF(ApplProf applProf) throws DaoException {
		ApplProfDao applProfDao = new ApplProfDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//applProfDao.doSave(applProf, "");
		applProfDao.doSaveTail(false, applProf, "");
		//fine LP 20240909 - PGNTBOLDER-1
	}

	public boolean updateUSR(PyUser user) throws DaoException {
		PyUserDao userDao = new PyUserDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//PyUser result = userDao.selectPyUserByKey(user.getChiaveUtente());
		PyUser result = userDao.selectPyUserByKeyTail(false, user.getChiaveUtente());
		//fine LP 20240909 - PGNTBOLDER-1
		if(result != null) {
			//inizio LP 20240909 - PGNTBOLDER-1
			//userDao.updatePyUser(user);
			userDao.updatePyUserTail(false, user); 
			//fine LP 20240909 - PGNTBOLDER-1
			return true;
		} else {
			return false;
		}
	}
	
	public void insertSOC(Company company) throws DaoException {
		CompanyDao companyDao = new CompanyDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//companyDao.doSave(company, "");
		companyDao.doSaveAutocommitFalse(company, "");
		//inizio LP 20240909 - PGNTBOLDER-1
	}
	
	public void insertAPC(AnagProvCom anagProvCom) throws DaoException {
		AnagProvComDao anagProvComDao = new AnagProvComDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//anagProvComDao.doSave(anagProvCom, "");
		anagProvComDao.doSaveTail(false, anagProvCom, "");
		//fine LP 20240909 - PGNTBOLDER-1
	}
	
	public void insertENT(Ente ente) throws DaoException {
		EnteDao enteDao = new EnteDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//enteDao.doSave(ente, "");
		enteDao.doSaveTail(false, ente, "");
		//fine LP 20240909 - PGNTBOLDER-1
	}
	
	public void insertBOL(Bollettino bol) throws DaoException {
		BollettinoDao bolDao = new BollettinoDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//bolDao.doSave(bol, "");
		bolDao.doSaveTail(false, bol, "");
		//fine LP 20240909 - PGNTBOLDER-1
	}
	
	public void insertCFE(ConfigUtenteTipoServizioEnte cfe) throws DaoException {
		ConfigUtenteTipoServizioEnteDao cfeDao = new ConfigUtenteTipoServizioEnteDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//cfeDao.doSave(cfe, "");
		cfeDao.doSaveTail(false, cfe, "");
		//fine LP 20240909 - PGNTBOLDER-1
	}
	
	public void insertCFS(ConfigUtenteTipoServizio cfs) throws DaoException {
		ConfigUtenteTipoServizioDao cfsDao = new ConfigUtenteTipoServizioDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//cfsDao.doSave(cfs, "");
		cfsDao.doSaveTail(false, cfs, "");
		//fine LP 20240909 - PGNTBOLDER-1
	}

	public void insertCES(AbilitaCanalePagamentoTipoServizioEnte ces) throws DaoException {
		AbilitaCanalePagamentoTipoServizioEnteDao cesDao = new AbilitaCanalePagamentoTipoServizioEnteDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//AbilitaCanalePagamentoTipoServizioEnte data = cesDao.doDetail(ces.getEnte().getUser().getCompany().getCompanyCode(), ces.getEnte().getUser().getUserCode(), 
		AbilitaCanalePagamentoTipoServizioEnte data = cesDao.doDetailTail(false, ces.getEnte().getUser().getCompany().getCompanyCode(), ces.getEnte().getUser().getUserCode(), 
		//fine LP 20240909 - PGNTBOLDER-1
				ces.getEnte().getAnagEnte().getChiaveEnte(), ces.getCanale().getChiaveCanalePagamento(), 
				ces.getTipoServizio().getCodiceTipologiaServizio());
		if(data == null) {
			//inizio LP 20240909 - PGNTBOLDER-1
			//cesDao.doSave(ces, "1");
			cesDao.doSaveTail(false, data, "1");
			//fine LP 20240909 - PGNTBOLDER-1
		} else{
			//inizio LP 20240909 - PGNTBOLDER-1
			//cesDao.doSave(ces, "2");
			cesDao.doSaveTail(false, data, "2");
			//fine LP 20240909 - PGNTBOLDER-1
		}
	}
	
	public void insertCAN(CanalePagamento canalePagamento) throws DaoException {
		CanalePagamentoDao canalePagamentoDao = new CanalePagamentoDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//canalePagamentoDao.doSave(canalePagamento, null);
		canalePagamentoDao.doSaveTail(false, canalePagamento, null);
		//fine LP 20240909 - PGNTBOLDER-1
	}
	
	public void insertTSE(TipologiaServizio tipologiaServizio) throws DaoException {
		TipologiaServizioDao tipologiaServizioDao = new TipologiaServizioDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//tipologiaServizioDao.doSave(tipologiaServizio, "");
		tipologiaServizioDao.doSaveTail(false, tipologiaServizio, "");
		//fine LP 20240909 - PGNTBOLDER-1
	}
	public void insertCAR(CartaPagamento cartaPagamento) throws DaoException {
		CartaPagamentoDao cartaPagamentoDao = new CartaPagamentoDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//cartaPagamentoDao.doSave(cartaPagamento, "");
		cartaPagamentoDao.doSaveTail(false, cartaPagamento, "");
		//fine LP 20240909 - PGNTBOLDER-1
	}

	public void insertTRA(Transazione transazione) throws DaoException, SQLException {
		TxTransazioniDao transazioniDao = new TxTransazioniDao(connection, schema);
		transazioniDao.insertTransazione(transazione); //LP 20240909 - PGNTBOLDER-1 (vedi nota su TxTransazioniDao)
	}
	
	public void insertSER(AnagServizi anagServizi) throws DaoException {
		AnagServiziDao anagServiziDao = new AnagServiziDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//anagServiziDao.doSave(anagServizi, "");
		anagServiziDao.doSaveTail(false, anagServizi, "");
		//fine LP 20240909 - PGNTBOLDER-1
	}
	
	public void insertTDT(TransazioneIV transazioneTDT) throws DaoException, SQLException {
		TxTransazioniIVDao transazioniTDTDao = new TxTransazioniIVDao(connection, schema);
		transazioniTDTDao.insertTransazioneIV(transazioneTDT); //LP 20240909 - PGNTBOLDER-1 (vedi nota su TxTransazioniIVDao)
	}
	
	public void insertMIN(ModuloIntegrazionePagamentiNodo moduloIntegrazionePagamenti) throws DaoException, SQLException {
		ModuloIntegrazionePagamentiNodoDao moduloPagDao = new ModuloIntegrazionePagamentiNodoDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//moduloPagDao.doSave(moduloIntegrazionePagamenti);
		moduloPagDao.doSaveTail(false, moduloIntegrazionePagamenti);
		//fine LP 20240909 - PGNTBOLDER-1
	}
	
	public void insertMIP(ModuloIntegrazionePagamenti moduloIntegrazionePagamenti) throws DaoException, SQLException {
		ModuloIntegrazionePagamentiDao moduloIntegrazionePagamentoDao = new ModuloIntegrazionePagamentiDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//moduloIntegrazionePagamentoDao.doSave(moduloIntegrazionePagamenti);
		moduloIntegrazionePagamentoDao.doSaveTail(false, moduloIntegrazionePagamenti);
		//fine LP 20240909 - PGNTBOLDER-1
	}
	
	public void insertTFR(TransazioneFreccia transazioneFreccia) throws DaoException, SQLException {
		TxTransazioniFrecciaDao transazioniFrecciaDao = new TxTransazioniFrecciaDao(connection, schema);
		transazioniFrecciaDao.insertTransazioneFreccia(transazioneFreccia); //LP 20240909 - PGNTBOLDER-1 (vedi nota su TxTransazioniFrecciaDao)
	}
	
	public void insertTIC(TransazioneIci transazioneIci) throws DaoException, SQLException {
		TxTransazioniIciDao transazioneIciDao = new TxTransazioniIciDao(connection, schema);
		transazioneIciDao.insertTransazioneICI(transazioneIci); //LP 20240909 - PGNTBOLDER-1 (vedi nota su TxTransazioniIciDao)
	}

	public void deleteSOC(Company company) {
		try {
			CompanyDao companyDao = new CompanyDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//companyDao.doDelete(company);
			companyDao.doDeleteTail(false, company);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e){
			//System.out.println("Eliminazione SOC: "+e.getMessage());
		}
	}

	public void deletePRF(ApplProf applProf) {
		try {
			ApplProfDao applProfDao = new ApplProfDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//applProfDao.doDelete(applProf);
			applProfDao.doDeleteTail(false, applProf);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e){
			//System.out.println("Eliminazione PRF: "+e.getMessage());
		}
	}

	public void deleteUSR(PyUser user) {
		try {
			PyUserDao pyUserDao = new PyUserDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//pyUserDao.deletePyUser(user.getChiaveUtente());
			pyUserDao.deletePyUserTail(false, user.getChiaveUtente());
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e){
			//System.out.println("Eliminazione USR: "+e.getMessage());
		}
	}

	public void deleteUTE(User user) {
		try {
			UserDao userDao = new UserDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//userDao.doDelete(user);
			userDao.doDeleteTail(false, user);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e){
			//System.out.println("Eliminazione UTE: "+e.getMessage());
		}
	}
	
	public void deleteAPC(AnagProvCom anagProvCom) {
		try {
			AnagProvComDao anagProvComDao = new AnagProvComDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//anagProvComDao.doDelete(anagProvCom);
			anagProvComDao.doDeleteTail(false, anagProvCom);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e) {
			//System.out.println("Eliminazione APC: "+e.getMessage());
		}
	}
	
	public void deleteANE(AnagEnte anagEnte) {
		try {
			AnagEnteDao anagEnteDao = new AnagEnteDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//anagEnteDao.doDelete(anagEnte);
			anagEnteDao.doDeleteTail(false, anagEnte);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e){
			//System.out.println("Eliminazione ANE: "+e.getMessage());
		}
	}
	
	public void deleteENT(Ente ente) {
		try {
			EnteDao enteDao = new EnteDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//enteDao.doDelete(ente);
			enteDao.doDeleteTail(false, ente);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e){
			//System.out.println("Eliminazione ENT: "+e.getMessage());
		}
	}
	
	public void deleteBOL(Bollettino bol) {
		try {
			BollettinoDao bolDao = new BollettinoDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//bolDao.deleteRecord(bol);
			bolDao.deleteRecordTail(false, bol);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e) {
			//System.out.println("Eliminazione BOL: "+e.getMessage());
		}
	}
	
	public void deleteCFE(ConfigUtenteTipoServizioEnte cfe) {
		try {
			ConfigUtenteTipoServizioEnteDao cfeDao = new ConfigUtenteTipoServizioEnteDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//cfeDao.doDelete(cfe);
			cfeDao.doDeleteTail(false, cfe);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e){
			//System.out.println("Eliminazione CFE: "+e.getMessage());
		}
	}
	
	public void deleteCFS(ConfigUtenteTipoServizio cfs) throws DaoException {
		try{
			ConfigUtenteTipoServizioDao cfsDao = new ConfigUtenteTipoServizioDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//cfsDao.doDelete(cfs);
			cfsDao.doDeleteTail(false, cfs);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e){
			//System.out.println("Eliminazione CFS: "+e.getMessage());
		}
	}

	public void deleteCES(AbilitaCanalePagamentoTipoServizioEnte ces) {
		try{
			AbilitaCanalePagamentoTipoServizioEnteDao cesDao = new AbilitaCanalePagamentoTipoServizioEnteDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//cesDao.doDelete(ces);
			cesDao.doDeleteTail(false, ces);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e){
			//System.out.println("Eliminazione CES " +e.getMessage());
		}
	}
	
	public void deleteCAN(CanalePagamento canalePagamento) {
		try {
			CanalePagamentoDao canalePagamentoDao = new CanalePagamentoDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//canalePagamentoDao.doDelete(canalePagamento);
			canalePagamentoDao.doDeleteTail(false, canalePagamento);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e) {
			//System.out.println("Eliminazione CAN: "+e.getMessage());
		}
	}
	
	public void deleteTSE(TipologiaServizio tipologiaServizio) {
		try {
			TipologiaServizioDao tipologiaServizioDao = new TipologiaServizioDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//tipologiaServizioDao.doDelete(tipologiaServizio);
			tipologiaServizioDao.doDeleteTail(false, tipologiaServizio);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch (Exception e) {
			//System.out.println("Eliminazione TSE: "+e.getMessage());
		}
	}
	public void deleteCAR(CartaPagamento cartaPagamento) {
		try {
			CartaPagamentoDao cartaPagamentoDao = new CartaPagamentoDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//cartaPagamentoDao.doDelete(cartaPagamento);
			cartaPagamentoDao.doDeleteTail(false, cartaPagamento);
			//inizio LP 20240909 - PGNTBOLDER-1
		} catch (Exception e) {
			//System.out.println("Eliminazione CAR: "+e.getMessage());
		}
	}
	
	public void deleteGTW(GatewayPagamento gatewayPagamento) {
		try {
			GatewayPagamentoDao gatewayPagamentoDao = new GatewayPagamentoDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//gatewayPagamentoDao.doDelete(gatewayPagamento);
			gatewayPagamentoDao.doDeleteTail(false, gatewayPagamento);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e) {
			//System.out.println("Eliminazione GTW: "+e.getMessage());
		}
	}
	
	public void deleteSER(AnagServizi anagServizi) {
		try {
			AnagServiziDao anagServiziDao = new AnagServiziDao(connection, schema);
			//inizio LP 20240909 - PGNTBOLDER-1
			//anagServiziDao.doDelete(anagServizi);
			anagServiziDao.doDeleteTail(false, anagServizi);
			//fine LP 20240909 - PGNTBOLDER-1
		} catch(Exception e) {
			//System.out.println("Eliminazione SER: "+e.getMessage());
		}
	}


	public boolean updateANE(AnagEnte anagEnte) throws DaoException {
		AnagEnteDao anagEnteDao = new AnagEnteDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//AnagEnte result = anagEnteDao.doDetail(anagEnte.getChiaveEnte());
		AnagEnte result = anagEnteDao.doDetailTail(false, anagEnte.getChiaveEnte());
		//fine LP 20240909 - PGNTBOLDER-1
		if(result != null) {
			//inizio LP 20240909 - PGNTBOLDER-1
			//anagEnteDao.doSave(anagEnte, "");
			anagEnteDao.doSaveTail(false, anagEnte, "");
			//fine LP 20240909 - PGNTBOLDER-1
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateGTW(GatewayPagamento gatewayPagamento) throws DaoException {
		GatewayPagamentoDao gatewayPagamentoDao = new GatewayPagamentoDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//GatewayPagamento result = gatewayPagamentoDao.doDetail(gatewayPagamento.getChiaveGateway());
		GatewayPagamento result = gatewayPagamentoDao.doDetailTail(false, gatewayPagamento.getChiaveGateway());
		//fine LP 20240909 - PGNTBOLDER-1
		if(result != null) {
			//inizio LP 20240909 - PGNTBOLDER-1
			//gatewayPagamentoDao.doSave(gatewayPagamento, "");
			gatewayPagamentoDao.doSaveTail(false, gatewayPagamento, "");
			//fine LP 20240909 - PGNTBOLDER-1
			return true;
		} else {
			return false;
		}
	}
	
	public void insertATM(TransazioneAtm transazioneAtm) throws DaoException, SQLException {
		TxTransazioniAtmDao transazioniAtmDao = new TxTransazioniAtmDao(connection, schema);
		transazioniAtmDao.insertTransazione(transazioneAtm);//LP 20240909 - PGNTBOLDER-1 (vedi nota su TxTransazioniAtmDao)
	}
	
	public void insertPOS(POS pos) throws DaoException, SQLException {
		POSDao posDao = new POSDao(connection, schema);
		//inizio LP 20240909 - PGNTBOLDER-1
		//posDao.insertTransazionePOS(pos);
		posDao.insertTransazionePOSTail(false, pos);
		//fine LP 20240909 - PGNTBOLDER-1
	}
	
}
