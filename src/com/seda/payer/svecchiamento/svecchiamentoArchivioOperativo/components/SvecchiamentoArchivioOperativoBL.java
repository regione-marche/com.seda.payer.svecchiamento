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
		userDao.doSave(user, "");
	}
	
	public void insertPRF(ApplProf applProf) throws DaoException {
		ApplProfDao applProfDao = new ApplProfDao(connection, schema);
		applProfDao.doSave(applProf, "");
	}

	public boolean updateUSR(PyUser user) throws DaoException {
		PyUserDao userDao = new PyUserDao(connection, schema);
		PyUser result = userDao.selectPyUserByKey(user.getChiaveUtente());
		if(result != null) {
			userDao.updatePyUser(user);
			return true;
		} else {
			return false;
		}
	}
	
	public void insertSOC(Company company) throws DaoException {
		CompanyDao companyDao = new CompanyDao(connection, schema);
		companyDao.doSave(company, "");
	}
	
	public void insertAPC(AnagProvCom anagProvCom) throws DaoException {
		AnagProvComDao anagProvComDao = new AnagProvComDao(connection, schema);
		anagProvComDao.doSave(anagProvCom, "");
	}
	
	public void insertENT(Ente ente) throws DaoException {
		EnteDao enteDao = new EnteDao(connection, schema);
		enteDao.doSave(ente, "");
	}
	
	public void insertBOL(Bollettino bol) throws DaoException {
		BollettinoDao bolDao = new BollettinoDao(connection, schema);
		bolDao.doSave(bol, "");
	}
	
	public void insertCFE(ConfigUtenteTipoServizioEnte cfe) throws DaoException {
		ConfigUtenteTipoServizioEnteDao cfeDao = new ConfigUtenteTipoServizioEnteDao(connection, schema);
		cfeDao.doSave(cfe, "");
	}
	
	public void insertCFS(ConfigUtenteTipoServizio cfs) throws DaoException {
		ConfigUtenteTipoServizioDao cfsDao = new ConfigUtenteTipoServizioDao(connection, schema);
		cfsDao.doSave(cfs, "");
	}

	public void insertCES(AbilitaCanalePagamentoTipoServizioEnte ces) throws DaoException {
		AbilitaCanalePagamentoTipoServizioEnteDao cesDao = new AbilitaCanalePagamentoTipoServizioEnteDao(connection, schema);
		AbilitaCanalePagamentoTipoServizioEnte data = cesDao.doDetail(ces.getEnte().getUser().getCompany().getCompanyCode(), ces.getEnte().getUser().getUserCode(), 
				ces.getEnte().getAnagEnte().getChiaveEnte(), ces.getCanale().getChiaveCanalePagamento(), 
				ces.getTipoServizio().getCodiceTipologiaServizio());
		if(data == null) {
			cesDao.doSave(ces, "1");
		} else{
			cesDao.doSave(ces, "2");
		}
	}
	
	public void insertCAN(CanalePagamento canalePagamento) throws DaoException {
		CanalePagamentoDao canalePagamentoDao = new CanalePagamentoDao(connection, schema);
		canalePagamentoDao.doSave(canalePagamento, null);
	}
	
	public void insertTSE(TipologiaServizio tipologiaServizio) throws DaoException {
		TipologiaServizioDao tipologiaServizioDao = new TipologiaServizioDao(connection, schema);
		tipologiaServizioDao.doSave(tipologiaServizio, "");
	}
	public void insertCAR(CartaPagamento cartaPagamento) throws DaoException {
		CartaPagamentoDao cartaPagamentoDao = new CartaPagamentoDao(connection, schema);
		cartaPagamentoDao.doSave(cartaPagamento, "");
	}

	public void insertTRA(Transazione transazione) throws DaoException, SQLException {
		TxTransazioniDao transazioniDao = new TxTransazioniDao(connection, schema);
		transazioniDao.insertTransazione(transazione);
	}
	
	public void insertSER(AnagServizi anagServizi) throws DaoException {
		AnagServiziDao anagServiziDao = new AnagServiziDao(connection, schema);
		anagServiziDao.doSave(anagServizi, "");
	}
	
	
	public void insertTDT(TransazioneIV transazioneTDT) throws DaoException, SQLException {
		TxTransazioniIVDao transazioniTDTDao = new TxTransazioniIVDao(connection, schema);
		transazioniTDTDao.insertTransazioneIV(transazioneTDT);
	}
	
	public void insertMIN(ModuloIntegrazionePagamentiNodo moduloIntegrazionePagamenti) throws DaoException, SQLException {
		ModuloIntegrazionePagamentiNodoDao moduloPagDao = new ModuloIntegrazionePagamentiNodoDao(connection, schema);
		moduloPagDao.doSave(moduloIntegrazionePagamenti);
	}
	
	public void insertMIP(ModuloIntegrazionePagamenti moduloIntegrazionePagamenti) throws DaoException, SQLException {
		ModuloIntegrazionePagamentiDao moduloIntegrazionePagamentoDao = new ModuloIntegrazionePagamentiDao(connection, schema);
		moduloIntegrazionePagamentoDao.doSave(moduloIntegrazionePagamenti);
	}
	
	public void insertTFR(TransazioneFreccia transazioneFreccia) throws DaoException, SQLException {
		TxTransazioniFrecciaDao transazioniFrecciaDao = new TxTransazioniFrecciaDao(connection, schema);
		transazioniFrecciaDao.insertTransazioneFreccia(transazioneFreccia);
	}
	
	public void insertTIC(TransazioneIci transazioneIci) throws DaoException, SQLException {
		TxTransazioniIciDao transazioneIciDao = new TxTransazioniIciDao(connection, schema);
		transazioneIciDao.insertTransazioneICI(transazioneIci);
	}
	
	public void deleteSOC(Company company) {
		try {
			CompanyDao companyDao = new CompanyDao(connection, schema);
			companyDao.doDelete(company);
		} catch(Exception e){
			//System.out.println("Eliminazione SOC: "+e.getMessage());
		}
	}
	
	public void deletePRF(ApplProf applProf) {
		try {
			ApplProfDao applProfDao = new ApplProfDao(connection, schema);
			applProfDao.doDelete(applProf);
		} catch(Exception e){
			//System.out.println("Eliminazione PRF: "+e.getMessage());
		}
	}
	
	public void deleteUSR(PyUser user) {
		try {
			PyUserDao pyUserDao = new PyUserDao(connection, schema);
			pyUserDao.deletePyUser(user.getChiaveUtente());
		} catch(Exception e){
			//System.out.println("Eliminazione USR: "+e.getMessage());
		}
	}
	
	public void deleteUTE(User user) {
		try {
			UserDao userDao = new UserDao(connection, schema);
			userDao.doDelete(user);
		} catch(Exception e){
			//System.out.println("Eliminazione UTE: "+e.getMessage());
		}
	}
	
	public void deleteAPC(AnagProvCom anagProvCom) {
		try {
			AnagProvComDao anagProvComDao = new AnagProvComDao(connection, schema);
			anagProvComDao.doDelete(anagProvCom);
		} catch(Exception e) {
			//System.out.println("Eliminazione APC: "+e.getMessage());
		}
	}
	
	public void deleteANE(AnagEnte anagEnte) {
		try {
			AnagEnteDao anagEnteDao = new AnagEnteDao(connection, schema);
			anagEnteDao.doDelete(anagEnte);
		} catch(Exception e){
			//System.out.println("Eliminazione ANE: "+e.getMessage());
		}
	}
	
	public void deleteENT(Ente ente) {
		try {
			EnteDao enteDao = new EnteDao(connection, schema);
			enteDao.doDelete(ente);
		} catch(Exception e){
			//System.out.println("Eliminazione ENT: "+e.getMessage());
		}
	}
	
	public void deleteBOL(Bollettino bol) {
		try {
			BollettinoDao bolDao = new BollettinoDao(connection, schema);
			bolDao.deleteRecord(bol);
		} catch(Exception e) {
			//System.out.println("Eliminazione BOL: "+e.getMessage());
		}
	}
	
	public void deleteCFE(ConfigUtenteTipoServizioEnte cfe) {
		try {
			ConfigUtenteTipoServizioEnteDao cfeDao = new ConfigUtenteTipoServizioEnteDao(connection, schema);
			cfeDao.doDelete(cfe);
		} catch(Exception e){
			//System.out.println("Eliminazione CFE: "+e.getMessage());
		}
	}
	
	public void deleteCFS(ConfigUtenteTipoServizio cfs) throws DaoException {
		try{
			ConfigUtenteTipoServizioDao cfsDao = new ConfigUtenteTipoServizioDao(connection, schema);
			cfsDao.doDelete(cfs);
		} catch(Exception e){
			//System.out.println("Eliminazione CFS: "+e.getMessage());
		}
	}

	public void deleteCES(AbilitaCanalePagamentoTipoServizioEnte ces) {
		try{
			AbilitaCanalePagamentoTipoServizioEnteDao cesDao = new AbilitaCanalePagamentoTipoServizioEnteDao(connection, schema);
			cesDao.doDelete(ces);
		} catch(Exception e){
			//System.out.println("Eliminazione CES " +e.getMessage());
		}
	}
	
	public void deleteCAN(CanalePagamento canalePagamento) {
		try {
			CanalePagamentoDao canalePagamentoDao = new CanalePagamentoDao(connection, schema);
			canalePagamentoDao.doDelete(canalePagamento);
		} catch(Exception e) {
			//System.out.println("Eliminazione CAN: "+e.getMessage());
		}
	}
	
	public void deleteTSE(TipologiaServizio tipologiaServizio) {
		try {
			TipologiaServizioDao tipologiaServizioDao = new TipologiaServizioDao(connection, schema);
			tipologiaServizioDao.doDelete(tipologiaServizio);
		} catch (Exception e) {
			//System.out.println("Eliminazione TSE: "+e.getMessage());
		}
	}
	public void deleteCAR(CartaPagamento cartaPagamento) {
		try {
			CartaPagamentoDao cartaPagamentoDao = new CartaPagamentoDao(connection, schema);
			cartaPagamentoDao.doDelete(cartaPagamento);
		} catch (Exception e) {
			//System.out.println("Eliminazione CAR: "+e.getMessage());
		}
	}
	
	public void deleteGTW(GatewayPagamento gatewayPagamento) {
		try {
			GatewayPagamentoDao gatewayPagamentoDao = new GatewayPagamentoDao(connection, schema);
			gatewayPagamentoDao.doDelete(gatewayPagamento);
		} catch(Exception e) {
			//System.out.println("Eliminazione GTW: "+e.getMessage());
		}
	}
	
	public void deleteSER(AnagServizi anagServizi) {
		try {
			AnagServiziDao anagServiziDao = new AnagServiziDao(connection, schema);
			anagServiziDao.doDelete(anagServizi);
		} catch(Exception e) {
			//System.out.println("Eliminazione SER: "+e.getMessage());
		}
	}


	public boolean updateANE(AnagEnte anagEnte) throws DaoException {
		AnagEnteDao anagEnteDao = new AnagEnteDao(connection, schema);
		AnagEnte result = anagEnteDao.doDetail(anagEnte.getChiaveEnte());
		if(result != null) {
			anagEnteDao.doSave(anagEnte, "");
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateGTW(GatewayPagamento gatewayPagamento) throws DaoException {
		GatewayPagamentoDao gatewayPagamentoDao = new GatewayPagamentoDao(connection, schema);
		GatewayPagamento result = gatewayPagamentoDao.doDetail(gatewayPagamento.getChiaveGateway());
		if(result != null) {
			gatewayPagamentoDao.doSave(gatewayPagamento, "");
			return true;
		} else {
			return false;
		}
	}
	
	public void insertATM(TransazioneAtm transazioneAtm) throws DaoException, SQLException {
		TxTransazioniAtmDao transazioniAtmDao = new TxTransazioniAtmDao(connection, schema);
		transazioniAtmDao.insertTransazione(transazioneAtm);
	}
	
	public void insertPOS(POS pos) throws DaoException, SQLException {
		POSDao posDao = new POSDao(connection, schema);
		posDao.insertTransazionePOS(pos);
	}
	
}
