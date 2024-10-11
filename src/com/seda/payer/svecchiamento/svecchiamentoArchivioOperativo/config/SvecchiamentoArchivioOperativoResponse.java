package com.seda.payer.svecchiamento.svecchiamentoArchivioOperativo.config;

public class SvecchiamentoArchivioOperativoResponse {
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

	public SvecchiamentoArchivioOperativoResponse() {
	}

	public SvecchiamentoArchivioOperativoResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String toString() {
		return "SvecchiamentoArchivioOperativoResponse [code=" + code + " ,message=" + message + "]";
	}

}
