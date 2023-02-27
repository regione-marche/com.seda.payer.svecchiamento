package com.seda.payer.svecchiamento.model;


import java.sql.ResultSet;
import java.sql.SQLException;



public class AssociazioneTransazioni {
	 private long idMdc;
	 private long idNodoScript;
	 
	 
	public long getIdMdc() {
		return idMdc;
	}
	
	public void setIdMdc(long idMdc) {
		this.idMdc = idMdc;
	}
	
	public long getIdNodoScript() {
		return idNodoScript;
	}
	
	public void setIdNodoScript(long idNodoScript) {
		this.idNodoScript = idNodoScript;
	}
	
	
	
	public AssociazioneTransazioni(ResultSet data)  throws SQLException {
    	if (data == null)
    		return;
    	
    	idMdc = data.getLong("RMT_PMDCPKEY");
		idNodoScript = data.getLong("RMT_PRPTPKEY");	
	}

	 
	 

}
