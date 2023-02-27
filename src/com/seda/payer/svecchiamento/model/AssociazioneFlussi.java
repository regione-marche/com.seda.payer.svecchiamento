package com.seda.payer.svecchiamento.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssociazioneFlussi {
	
	 private long idMdc;
	 private long idQuadratura;
	 
	 
	public long getIdMdc() {
		return idMdc;
	}
	public void setIdMdc(long idMdc) {
		this.idMdc = idMdc;
	}
	public long getIdQuadratura() {
		return idQuadratura;
	}
	public void setIdQuadratura(long idQuadratura) {
		this.idQuadratura = idQuadratura;
	}
	 
	

	public AssociazioneFlussi(ResultSet data)  throws SQLException {
    	if (data == null)
    		return;
    	
    	idMdc = data.getLong("RMF_PMDCPKEY");
    	idQuadratura = data.getLong("RMF_PQUNPKEY");
	} 

}
