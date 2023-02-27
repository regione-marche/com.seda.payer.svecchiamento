package com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.config;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public enum PropertiesPath {
	jdbcDriver,
	jdbcUrl,
	jdbcUser,
	jdbcPassword,
	schema,
	jdbcDriverBK,
	jdbcUrlBK,
	jdbcUserBK,
	jdbcPasswordBK,
	schemaBK,
	jdbcDriverSE,
	jdbcUrlSE,
	jdbcUserSE,
	jdbcPasswordSE,
	schemaSE,
	wsEmailSenderEndpointURL,
	oggettoEmailErrori,
	emailAdminErrori,
	codSocPartenza,
	codSocDestinazione;
	private static ResourceBundle rb;

    public String format( Object... args ) {
        synchronized(PropertiesPath.class) {
            if(rb==null)
                rb = ResourceBundle.getBundle(PropertiesPath.class.getName());
            return MessageFormat.format(rb.getString(name()),args);
        }
    }
}
