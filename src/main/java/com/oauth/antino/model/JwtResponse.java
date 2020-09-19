package com.oauth.antino.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String otp;

	public JwtResponse(String jwttoken,String otp) {
		this.jwttoken = jwttoken;
		this.otp=otp;
	}

	public String getToken() {
		return this.jwttoken;
	}

	@Override
	public String toString() {
		return "JwtResponse [jwttoken=" + jwttoken + ", otp=" + otp + "]";
	}
	
}