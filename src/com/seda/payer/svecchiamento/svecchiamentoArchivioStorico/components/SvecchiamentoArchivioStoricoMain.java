package com.seda.payer.svecchiamento.svecchiamentoArchivioStorico.components;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.seda.bap.components.core.BapException;
import com.seda.bap.components.core.spi.ClassPrinting;
import com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.components.SvecchiamentoArchivioOperativoCore;
import com.seda.payer.svecchiamento.svecchiamentoArchivioStorico.config.SvecchiamentoArchivioStoricoResponse;

public class SvecchiamentoArchivioStoricoMain {

	public static void main(String[] args) throws Exception {
		SvecchiamentoArchivioStoricoCore core = new SvecchiamentoArchivioStoricoCore();
        String configPath=args[0];
        String dataSvecchiamento=args[1];
		System.out.println("ConfigPath = " + configPath);
		System.out.println("Data Svecchiamento = " + dataSvecchiamento);
		ClassPrinting classPrinting = null;
		Logger logger = Logger.getLogger(SvecchiamentoArchivioOperativoCore.class);
		/*
		String[] params = new String[]{ 
		"CONFIGPATH      D:/VIEWs/SUITE_S_AS_SVILUPPO_001_cerquozzi/PayER_VOB/com.seda.payer.svecchiamento/src/com/seda/payer/svecchiamento/svecchiamentoArchivioStorico/config/svecchiamentoArchivioStorico.properties",
		"DATE      31/12/2018"
		};
		*/
		String[] params = new String[]{"CONFIGPATH"+ StringUtils.leftPad(new String(""), 6,' ')+configPath,
				"DATE"+StringUtils.leftPad(new String(""), 6,' ')+dataSvecchiamento
		};	
		
		SvecchiamentoArchivioStoricoResponse res;
		
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
