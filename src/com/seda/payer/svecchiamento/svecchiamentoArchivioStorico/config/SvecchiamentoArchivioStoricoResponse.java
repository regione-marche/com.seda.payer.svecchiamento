package com.seda.payer.svecchiamento.svecchiamentoArchivioStorico.config;

public class SvecchiamentoArchivioStoricoResponse {
	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SvecchiamentoArchivioStoricoResponse() {
	}

	public SvecchiamentoArchivioStoricoResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String toString() {
		return "SvecchiamentoArchivioStoricoResponse [code=" + code + " ,message=" + message
				+ "]";
	}

}
