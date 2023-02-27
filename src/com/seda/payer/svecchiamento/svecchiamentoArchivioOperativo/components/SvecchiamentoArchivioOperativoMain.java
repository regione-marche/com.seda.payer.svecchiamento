package com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.components;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.seda.bap.components.core.BapException;
import com.seda.bap.components.core.spi.ClassPrinting;
import com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.components.SvecchiamentoArchivioOperativoCore;
import com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.config.SvecchiamentoArchivioOperativoResponse;

public class SvecchiamentoArchivioOperativoMain {

	public static void main(String[] args) throws Exception {
		SvecchiamentoArchivioOperativoCore core = new SvecchiamentoArchivioOperativoCore();
        String configPath=args[0];
        String dataSvecchiamento=args[1];
		System.out.println("ConfigPath = " + configPath);
		System.out.println("Data Svecchiamento = " + dataSvecchiamento);
		ClassPrinting classPrinting = null;
		Logger logger = Logger.getLogger(SvecchiamentoArchivioOperativoCore.class);
		/*
		String[] params = new String[]{ 
		"CONFIGPATH      D:/VIEWs/SUITE_S_AS_SVILUPPO_001_cerquozzi/PayER_VOB/com.seda.payer.svecchiamento/src/com/seda/payer/svecchiamento/svecchiamentoArchivioOperativo/config/svecchiamentoArchivioOperativo.properties",
		"DATE      31/12/2015"
		};
		*/
		String[] params = new String[]{"CONFIGPATH"+ StringUtils.leftPad(new String(""), 6,' ')+configPath,
				"DATE"+StringUtils.leftPad(new String(""), 6,' ')+dataSvecchiamento
		};	
		
		SvecchiamentoArchivioOperativoResponse res;
		
		try {
			res = core.run(params, classPrinting ,logger);
			if (!res.getCode().equals("00")) {
				System.exit(1);
			} else {
				System.exit(0);
			}
		} catch (BapException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
