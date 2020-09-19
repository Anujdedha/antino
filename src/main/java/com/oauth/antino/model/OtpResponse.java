package com.oauth.antino.model;

import java.io.Serializable;

public class OtpResponse implements Serializable {
	private final String validtyresponse;

	public OtpResponse(String validtyresponse) {
		this.validtyresponse = validtyresponse;
	}

	public String getResponse() {
		return this.validtyresponse;
	}
}
