package com.seda.payer.svecchiamento.svecchiamentoArchivioStorico.config;

import java.text.MessageFormat;
import java.util.ResourceBundle;


public enum PropertiesPath {
	jdbcDriverBK,
	jdbcUrlBK,
	jdbcUserBK,
	jdbcPasswordBK,
	schemaBK,
	wsEmailSenderEndpointURL,
	oggettoEmailErrori,
	emailAdminErrori,
	outputDir;
	private static ResourceBundle rb;

    public String format( Object... args ) {
        synchronized(PropertiesPath.class) {
            if(rb==null)
                rb = ResourceBundle.getBundle(PropertiesPath.class.getName());
            return MessageFormat.format(rb.getString(name()),args);
        }
    }
}
