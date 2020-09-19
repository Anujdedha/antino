package com.oauth.antino.util;

import java.util.HashMap;

public class User {
	public HashMap<String, String> OTP = new HashMap();
	private static User user = null;
	public static String otp = "";

	private User() {

	}

	public static User getInstance() {
		if (user == null)
			user = new User();

		return user;
	}
}
