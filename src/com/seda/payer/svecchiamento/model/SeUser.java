package com.seda.payer.svecchiamento.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


@SuppressWarnings("serial")
public class SeUser implements Serializable{

	private String username = null;
	private String codiceSocieta = null;
	private String tipologiaUtente = null;
	private String nome = null;
	private String cognome = null;
	private String sesso = null;
	private String codiceFiscale = null;
	private java.util.Date dataNascita = null;
	private String provincia = null;
	private String codbelfNascita = null;
	private String flagesteroNascita = null;
	private String tipoDocumento = null;
	private String numeroDocumento = null;
	private String enteRilascio = null;
	private java.util.Date dataRilascio = null;
	private String indirizzoResidenza = null;
	private String provinciaResidenza = null;
	private String codbelfResidenza = null;
	private String capcomuneResidenza = null;
	private String flagesteroResidenza = null;
	private String indirizzoDomicilio = null;
	private String provinciaDomicilio = null;
	private String codbelfDomicilio = null;
	private String capcomuneDomicilio = null;
	private String flagesteroDomicilio = null;
	private String emailPersonaFisica = null;
	private String emailPECPersonaFisica = null;
	private String cellularePersonaFisica = null;
	private String telefonoPersonaFisica = null;
	private String faxPersonaFisica = null;
	private String partitaIVA = null;
	private String ragioneSociale = null;
	private String codiceClassificazioneMerceologica = null;
	private String numeroAutorizzazione = null;
	private String indirizzoSedeLegale = null;
	private String provinciaSedeLegale = null;
	private String codbelfSedeLegale = null;
	private String capcomuneSedeLegale = null;
	private String flagesteroSedeLegale = null;
	private String indirizzoSedeOperativa = null;
	private String provinciaSedeOperativa = null;
	private String codbelfSedeOperativa = null;
	private String capcomuneSedeOperativa = null;
	private String emailPartitaIVA = null;
	private String emailPECPartitaIVA = null;
	private String cellularePartitaIVA = null;
	private String telefonoPartitaIVA = null;
	private String faxPartitaIVA = null;
	private String password = null;
	private String password2 = null;
	private String password3 = null;
	private String puk = null;
	private String utenzaAttiva = null;
	private Timestamp dataInizioValiditaUtenza = null;
	private Timestamp dataFineValiditaUtenza = null;
	private String primoAccesso = null;
	private Timestamp dataScadenzaPassword = null;
	private Timestamp dataUltimoAccesso = null;
	private Timestamp dataInserimentoUtenza = null;
	private String note = null;
	private Timestamp dataUltimoAggiornamento = null;
	private String operatoreUltimoAggiornamento = null;
	private String flagOperatoreBackOffice = null;
	
		
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCodiceSocieta() {
		return codiceSocieta;
	}

	public void setCodiceSocieta(String codiceSocieta) {
		this.codiceSocieta = codiceSocieta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}
	
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getSesso() {
		return sesso;
	}

	public void setDataNascita(java.util.Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public java.util.Date getDataNascita() {
		return dataNascita;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword3() {
		return password3;
	}

	public void setPassword3(String password3) {
		this.password3 = password3;
	}

	public String getPuk() {
		return puk;
	}

	public void setPuk(String puk) {
		this.puk = puk;
	}

	public String getTipologiaUtente() {
		return tipologiaUtente;
	}

	public void setTipologiaUtente(String tipologiaUtente) {
		this.tipologiaUtente = tipologiaUtente;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getUtenzaAttiva() {
		return utenzaAttiva;
	}

	public void setUtenzaAttiva(String utenzaAttiva) {
		this.utenzaAttiva = utenzaAttiva;
	}

	public Timestamp getDataInizioValiditaUtenza() {
		return dataInizioValiditaUtenza;
	}

	public void setDataInizioValiditaUtenza(Timestamp dataInizioValiditaUtenza) {
		this.dataInizioValiditaUtenza = dataInizioValiditaUtenza;
	}

	public Timestamp getDataFineValiditaUtenza() {
		return dataFineValiditaUtenza;
	}

	public void setDataFineValiditaUtenza(Timestamp dataFineValiditaUtenza) {
		this.dataFineValiditaUtenza = dataFineValiditaUtenza;
	}

	public String getPrimoAccesso() {
		return primoAccesso;
	}

	public void setPrimoAccesso(String primoAccesso) {
		this.primoAccesso = primoAccesso;
	}

	public Timestamp getDataScadenzaPassword() {
		return dataScadenzaPassword;
	}

	public void setDataScadenzaPassword(Timestamp dataScadenzaPassword) {
		this.dataScadenzaPassword = dataScadenzaPassword;
	}

	public Timestamp getDataUltimoAccesso() {
		return dataUltimoAccesso;
	}

	public void setDataUltimoAccesso(Timestamp dataUltimoAccesso) {
		this.dataUltimoAccesso = dataUltimoAccesso;
	}

	public Timestamp getDataInserimentoUtenza() {
		return dataInserimentoUtenza;
	}

	public void setDataInserimentoUtenza(Timestamp dataInserimentoUtenza) {
		this.dataInserimentoUtenza = dataInserimentoUtenza;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}

	public void setDataUltimoAggiornamento(Timestamp dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	public String getOperatoreUltimoAggiornamento() {
		return operatoreUltimoAggiornamento;
	}

	public void setOperatoreUltimoAggiornamento(String operatoreUltimoAggiornamento) {
		this.operatoreUltimoAggiornamento = operatoreUltimoAggiornamento;
	}
	
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCodbelfNascita() {
		return codbelfNascita;
	}

	public void setCodbelfNascita(String codbelfNascita) {
		this.codbelfNascita = codbelfNascita;
	}

	public String getFlagesteroNascita() {
		return flagesteroNascita;
	}

	public void setFlagesteroNascita(String flagesteroNascita) {
		this.flagesteroNascita = flagesteroNascita;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getEnteRilascio() {
		return enteRilascio;
	}

	public void setEnteRilascio(String enteRilascio) {
		this.enteRilascio = enteRilascio;
	}

	public java.util.Date getDataRilascio() {
		return dataRilascio;
	}

	public void setDataRilascio(java.util.Date dataRilascio) {
		this.dataRilascio = dataRilascio;
	}

	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}

	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public String getCodbelfResidenza() {
		return codbelfResidenza;
	}

	public void setCodbelfResidenza(String codbelfResidenza) {
		this.codbelfResidenza = codbelfResidenza;
	}

	public String getCapcomuneResidenza() {
		return capcomuneResidenza;
	}

	public void setCapcomuneResidenza(String capcomuneResidenza) {
		this.capcomuneResidenza = capcomuneResidenza;
	}

	public String getFlagesteroResidenza() {
		return flagesteroResidenza;
	}

	public void setFlagesteroResidenza(String flagesteroResidenza) {
		this.flagesteroResidenza = flagesteroResidenza;
	}

	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}

	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
	}

	public String getProvinciaDomicilio() {
		return provinciaDomicilio;
	}

	public void setProvinciaDomicilio(String provinciaDomicilio) {
		this.provinciaDomicilio = provinciaDomicilio;
	}

	public String getCodbelfDomicilio() {
		return codbelfDomicilio;
	}

	public void setCodbelfDomicilio(String codbelfDomicilio) {
		this.codbelfDomicilio = codbelfDomicilio;
	}

	public String getCapcomuneDomicilio() {
		return capcomuneDomicilio;
	}

	public void setCapcomuneDomicilio(String capcomuneDomicilio) {
		this.capcomuneDomicilio = capcomuneDomicilio;
	}

	public String getFlagesteroDomicilio() {
		return flagesteroDomicilio;
	}

	public void setFlagesteroDomicilio(String flagesteroDomicilio) {
		this.flagesteroDomicilio = flagesteroDomicilio;
	}

	public String getEmailPersonaFisica() {
		return emailPersonaFisica;
	}

	public void setEmailPersonaFisica(String emailPersonaFisica) {
		this.emailPersonaFisica = emailPersonaFisica;
	}

	public String getEmailPECPersonaFisica() {
		return emailPECPersonaFisica;
	}

	public void setEmailPECPersonaFisica(String emailPECPersonaFisica) {
		this.emailPECPersonaFisica = emailPECPersonaFisica;
	}

	public String getCellularePersonaFisica() {
		return cellularePersonaFisica;
	}

	public void setCellularePersonaFisica(String cellularePersonaFisica) {
		this.cellularePersonaFisica = cellularePersonaFisica;
	}

	public String getTelefonoPersonaFisica() {
		return telefonoPersonaFisica;
	}

	public void setTelefonoPersonaFisica(String telefonoPersonaFisica) {
		this.telefonoPersonaFisica = telefonoPersonaFisica;
	}

	public void setFaxPersonaFisica(String faxPersonaFisica) {
		this.faxPersonaFisica = faxPersonaFisica;
	}

	public String getFaxPersonaFisica() {
		return faxPersonaFisica;
	}

	public String getPartitaIVA() {
		return partitaIVA;
	}

	public void setPartitaIVA(String partitaIVA) {
		this.partitaIVA = partitaIVA;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getCodiceClassificazioneMerceologica() {
		return codiceClassificazioneMerceologica;
	}

	public void setCodiceClassificazioneMerceologica(
			String codiceClassificazioneMerceologica) {
		this.codiceClassificazioneMerceologica = codiceClassificazioneMerceologica;
	}

	public String getNumeroAutorizzazione() {
		return numeroAutorizzazione;
	}

	public void setNumeroAutorizzazione(String numeroAutorizzazione) {
		this.numeroAutorizzazione = numeroAutorizzazione;
	}

	public String getIndirizzoSedeLegale() {
		return indirizzoSedeLegale;
	}

	public void setIndirizzoSedeLegale(String indirizzoSedeLegale) {
		this.indirizzoSedeLegale = indirizzoSedeLegale;
	}

	public String getProvinciaSedeLegale() {
		return provinciaSedeLegale;
	}

	public void setProvinciaSedeLegale(String provinciaSedeLegale) {
		this.provinciaSedeLegale = provinciaSedeLegale;
	}

	public String getCodbelfSedeLegale() {
		return codbelfSedeLegale;
	}

	public void setCodbelfSedeLegale(String codbelfSedeLegale) {
		this.codbelfSedeLegale = codbelfSedeLegale;
	}

	public String getCapcomuneSedeLegale() {
		return capcomuneSedeLegale;
	}

	public void setCapcomuneSedeLegale(String capcomuneSedeLegale) {
		this.capcomuneSedeLegale = capcomuneSedeLegale;
	}

	public String getFlagesteroSedeLegale() {
		return flagesteroSedeLegale;
	}

	public void setFlagesteroSedeLegale(String flagesteroSedeLegale) {
		this.flagesteroSedeLegale = flagesteroSedeLegale;
	}

	public String getIndirizzoSedeOperativa() {
		return indirizzoSedeOperativa;
	}

	public void setIndirizzoSedeOperativa(String indirizzoSedeOperativa) {
		this.indirizzoSedeOperativa = indirizzoSedeOperativa;
	}

	public String getProvinciaSedeOperativa() {
		return provinciaSedeOperativa;
	}

	public void setProvinciaSedeOperativa(String provinciaSedeOperativa) {
		this.provinciaSedeOperativa = provinciaSedeOperativa;
	}

	public String getCodbelfSedeOperativa() {
		return codbelfSedeOperativa;
	}

	public void setCodbelfSedeOperativa(String codbelfSedeOperativa) {
		this.codbelfSedeOperativa = codbelfSedeOperativa;
	}

	public String getCapcomuneSedeOperativa() {
		return capcomuneSedeOperativa;
	}

	public void setCapcomuneSedeOperativa(String capcomuneSedeOperativa) {
		this.capcomuneSedeOperativa = capcomuneSedeOperativa;
	}

	public String getEmailPartitaIVA() {
		return emailPartitaIVA;
	}

	public void setEmailPartitaIVA(String emailPartitaIVA) {
		this.emailPartitaIVA = emailPartitaIVA;
	}

	public String getEmailPECPartitaIVA() {
		return emailPECPartitaIVA;
	}

	public void setEmailPECPartitaIVA(String emailPECPartitaIVA) {
		this.emailPECPartitaIVA = emailPECPartitaIVA;
	}

	public String getCellularePartitaIVA() {
		return cellularePartitaIVA;
	}

	public void setCellularePartitaIVA(String cellularePartitaIVA) {
		this.cellularePartitaIVA = cellularePartitaIVA;
	}

	public String getTelefonoPartitaIVA() {
		return telefonoPartitaIVA;
	}

	public void setTelefonoPartitaIVA(String telefonoPartitaIVA) {
		this.telefonoPartitaIVA = telefonoPartitaIVA;
	}

	public void setFaxPartitaIVA(String faxPartitaIVA) {
		this.faxPartitaIVA = faxPartitaIVA;
	}

	public String getFaxPartitaIVA() {
		return faxPartitaIVA;
	}

	public String getFlagOperatoreBackOffice() {
		return flagOperatoreBackOffice;
	}

	public void setFlagOperatoreBackOffice(String flagOperatoreBackOffice) {
		this.flagOperatoreBackOffice = flagOperatoreBackOffice;
	}

	public static SeUser getBean(ResultSet data) throws SQLException
	{
		if(data == null)return null;
		
		SeUser bean = null;
		if(data.next())
		{
			bean = new SeUser();
			
			bean.username = data.getString("USR_CUSRUSER");
			bean.codiceSocieta = data.getString("USR_CSOCCSOC");
			bean.tipologiaUtente = data.getString("USR_TUSRUSER");
			bean.nome = data.getString("USR_DUSRNOME");
			bean.cognome = data.getString("USR_DUSRCOGN");
			bean.sesso = data.getString("USR_CUSRSESS");
			bean.codiceFiscale = data.getString("USR_CUSRCFIS");
			bean.dataNascita = data.getDate("USR_GUSRNASC");
			bean.provincia = data.getString("USR_CUSRPROV");
			bean.codbelfNascita = data.getString("USR_DUSRNABF");
			bean.flagesteroNascita = data.getString("USR_FUSRNAES");
			bean.tipoDocumento = data.getString("USR_TUSRDOCU");
			bean.numeroDocumento = data.getString("USR_CUSRDOCU");
			bean.enteRilascio = data.getString("USR_DUSRENTE");
			bean.dataRilascio = data.getDate("USR_GUSRRILA");
			bean.indirizzoResidenza = data.getString("USR_DUSRREIN");
			bean.provinciaResidenza = data.getString("USR_DUSRREPV");
			bean.codbelfResidenza = data.getString("USR_DUSRREBF");
			bean.capcomuneResidenza = data.getString("USR_DUSRRECA");
			bean.flagesteroResidenza = data.getString("USR_FUSRREES");
			bean.indirizzoDomicilio = data.getString("USR_DUSRDOIN");
			bean.provinciaDomicilio = data.getString("USR_DUSRDOPV");
			bean.codbelfDomicilio = data.getString("USR_DUSRDOBF");
			bean.capcomuneDomicilio = data.getString("USR_DUSRDOCA");
			bean.flagesteroDomicilio = data.getString("USR_FUSRDOES");
			bean.emailPersonaFisica = data.getString("USR_EUSRMAIL");
			bean.emailPECPersonaFisica = data.getString("USR_EUSREPEC");
			bean.cellularePersonaFisica = data.getString("USR_CUSRNSMS");
			bean.telefonoPersonaFisica = data.getString("USR_CUSRNTEL");
			bean.faxPersonaFisica = data.getString("USR_CUSRNFAX");
			bean.partitaIVA = data.getString("USR_CUSRPIVA");
			bean.ragioneSociale = data.getString("USR_DUSRRAGS");
			bean.codiceClassificazioneMerceologica = data.getString("USR_KMERKMER");
			bean.numeroAutorizzazione = data.getString("USR_CUSRAUTO");
			bean.indirizzoSedeLegale = data.getString("USR_DUSRLEIN");
			bean.provinciaSedeLegale = data.getString("USR_DUSRLEPV");
			bean.codbelfSedeLegale = data.getString("USR_DUSRLEBF");
			bean.capcomuneSedeLegale = data.getString("USR_DUSRLECA");
			bean.flagesteroSedeLegale = data.getString("USR_FUSRLEES");
			bean.indirizzoSedeOperativa = data.getString("USR_DUSROPIN");
			bean.provinciaSedeOperativa = data.getString("USR_DUSROPPV");
			bean.codbelfSedeOperativa = data.getString("USR_DUSROPBF");
			bean.capcomuneSedeOperativa = data.getString("USR_DUSROPCA");
			bean.emailPartitaIVA = data.getString("USR_EUSRPIEM");
			bean.emailPECPartitaIVA = data.getString("USR_EUSRPIPE");
			bean.cellularePartitaIVA = data.getString("USR_CUSRPICE");
			bean.faxPartitaIVA = data.getString("USR_CUSRPIFX");
			bean.telefonoPartitaIVA = data.getString("USR_CUSRPITE");
			bean.password = data.getString("USR_CUSRPWRD");
			bean.password2 = data.getString("USR_CUSRPWD2");
			bean.password3 = data.getString("USR_CUSRPWD3");
			bean.puk = data.getString("USR_CUSRCPUK");
			bean.utenzaAttiva = data.getString("USR_FUSRATTI");
			bean.dataInizioValiditaUtenza = data.getTimestamp("USR_GUSRINIZ");
			bean.dataFineValiditaUtenza = data.getTimestamp("USR_GUSRFINE");
			bean.primoAccesso = data.getString("USR_FUSRPRIM");
			bean.dataScadenzaPassword = data.getTimestamp("USR_GUSRSCAD");
			bean.dataUltimoAccesso = data.getTimestamp("USR_GUSRACCE");
			bean.dataInserimentoUtenza = data.getTimestamp("USR_GUSRGINS");
			bean.note = data.getString("USR_DUSRNOTE");
			bean.dataUltimoAggiornamento = data.getTimestamp("USR_GUSRGAGG");
			bean.operatoreUltimoAggiornamento = data.getString("USR_CUSRCOPE");
			bean.flagOperatoreBackOffice = data.getString("USR_FUSRBKOF");
			
		}
		return bean;
	}
	
}
