package com.seda.payer.svecchiamento.svecchiamentoArchivioStorico.components;

import org.apache.log4j.Logger;

import com.seda.bap.components.core.BapException;
import com.seda.bap.components.core.spi.ClassPrinting;
import com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.components.SvecchiamentoArchivioOperativoCore;


public class SvecchiamentoArchivioStoricoTest {
	
	public static void main(String[] args) throws Exception {
		SvecchiamentoArchivioStoricoCore core = new SvecchiamentoArchivioStoricoCore();
		ClassPrinting classPrinting = null;
		Logger logger = Logger.getLogger(SvecchiamentoArchivioOperativoCore.class);
		
		String[] params = new String[]{ 
				"CONFIGPATH      D:/VIEWs/SUITE_S_AS_SVILUPPO_001_cerquozzi/PayER_VOB/com.seda.payer.svecchiamento/src/com/seda/payer/svecchiamento/svecchiamentoArchivioStorico/config/svecchiamentoArchivioStorico.properties",
				"DATE      31/12/2020"
				};
		try {
			//core.run(params, null, null, classPrinting ,logger, jobId);
			core.run(params, classPrinting ,logger);
		
		} catch (BapException e) {
			e.printStackTrace();
		}
		
	}


}
