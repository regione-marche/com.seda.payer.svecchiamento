package com.seda.payer.svecchiamento.svecchiamentoArchivioStorico.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.seda.payer.svecchiamento.svecchiamentoArchivioStorico.config.PropertiesPath;

public class SvecchiamentoArchivioStoricoContext {
	private static final long serialVersionUID = 1L;

	private Properties config;
	private Logger logger;
	protected HashMap<String, List<String>> parameters = new HashMap<String, List<String>>();
	
	public SvecchiamentoArchivioStoricoContext() {}
	
	 public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	 
	public Properties getProperties() {
		return config;
	}
	public void setConfig(Properties config) {
		this.config = config;
	}
		
	
	public int addParameter(String name, String value) {
		if(!this.parameters.containsKey(name)) {
			this.parameters.put(name, new LinkedList<String>());
		}
		this.parameters.get(name).add(value); //Aggiunge un valore alla lista delle ripetizioni
		return this.parameters.get(name).size();

	}
	public String getParameter(String name) {
		if(parameters.containsKey(name))
			return (String)parameters.get(name).get(0);
		else
			return "";
	}
	public void loadSchedeBap(String[] params) {
		for (int i=0;i < params.length; i++ ) {
			String[] p = params[i].split("\\s+");
			if (p[0].equals("END")) {
				if (p[1].trim().equals("")) {
					addParameter(p[0].trim(), "");
				} else {
					addParameter(p[0].trim(), p[1].trim());
				}
			} else {
				addParameter(p[0].trim(), p[1].trim());//Nome parametro - valore(Aggiunge Lista di valori per schede con ripetizione)	
			}
		}
	}
	public String formatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	public String formattaData(java.util.Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	public String getJdbcDriverBK() {
		return config.getProperty(PropertiesPath.jdbcDriverBK.format());
	}
	public String getJdbcUrlBK() {
		return config.getProperty(PropertiesPath.jdbcUrlBK.format());
	}
	public String getJdbcUserBK() {
		return config.getProperty(PropertiesPath.jdbcUserBK.format());
	}
	public String getJdbcPasswordBK() {
		return config.getProperty(PropertiesPath.jdbcPasswordBK.format());
	}
	public String getSchemaBK() {
		return config.getProperty(PropertiesPath.schemaBK.format());
	}
	public String getWsEmailSenderEndpointURL() {
		return config.getProperty(PropertiesPath.wsEmailSenderEndpointURL.format());
	}
	public String getOggettoEmailErrori() {
		return config.getProperty(PropertiesPath.oggettoEmailErrori.format());
	}
	public String getEmailAdminErrori() {
		return config.getProperty(PropertiesPath.emailAdminErrori.format());
	}
	public String getOutputDir() {
		return config.getProperty(PropertiesPath.outputDir.format());
	}
}
