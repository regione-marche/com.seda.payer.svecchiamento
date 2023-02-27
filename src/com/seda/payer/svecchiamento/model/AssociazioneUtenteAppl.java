package com.seda.payer.svecchiamento.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssociazioneUtenteAppl {
	private long chiaveUtente;
	private String codiceApplicazione;
		
	public long getChiaveUtente() {
		return chiaveUtente;
	}
	public void setChiaveUtente(long chiaveUtente) {
		this.chiaveUtente = chiaveUtente;
	}
	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}
	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}
	
	public AssociazioneUtenteAppl(ResultSet data)  throws SQLException {
    	if (data == null)
    		return;
    	
    	chiaveUtente = data.getLong("MNA_KUSRKUSR");
    	codiceApplicazione = data.getString("MNA_CMNAAPPL");	
	}

}
