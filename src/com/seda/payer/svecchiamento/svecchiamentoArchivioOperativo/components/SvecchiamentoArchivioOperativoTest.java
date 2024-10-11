package com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.components;

import org.apache.log4j.Logger;
import com.seda.bap.components.core.BapException;
import com.seda.bap.components.core.spi.ClassPrinting;

public class SvecchiamentoArchivioOperativoTest {

	public static void main(String[] args) throws Exception {
		SvecchiamentoArchivioOperativoCore core = new SvecchiamentoArchivioOperativoCore();
		ClassPrinting classPrinting = null;
		Logger logger = Logger.getLogger(SvecchiamentoArchivioOperativoCore.class);
		//String jobId = "";
		
		//inizio LP 20240820 - PGNTBOLDER-1
		String[] params = new String[]{ 
				"CONFIGPATH  E://ConfigFiles/svecchiamentoArchivioOperativo.properties",
				"DATE      31/12/2015"
				};
		//fine LP 20240820 - PGNTBOLDER-1
		try {
			//core.run(params, null, null, classPrinting ,logger, jobId);
			core.run(params, classPrinting ,logger);
		
		} catch (BapException e) {
			e.printStackTrace();
		}
	}

}
