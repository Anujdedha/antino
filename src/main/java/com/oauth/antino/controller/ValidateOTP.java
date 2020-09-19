package com.oauth.antino.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.antino.model.JwtResponse;
import com.oauth.antino.model.OtpResponse;
import com.oauth.antino.util.User;

@RestController
@RequestMapping("/checkotp")
@CrossOrigin(origins = "*")
public class ValidateOTP {
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ResponseEntity<?> update(@RequestBody final Map jsonMap) {
		User user = User.getInstance();
		String otp = "";
		String responsemessage = "";
		String mobileandotp = "";
		String regex = "[0-9]+";
		try {
			otp = user.otp;
			if (jsonMap.containsKey("otp")) {

				if (otp.equalsIgnoreCase(jsonMap.get("otp").toString())) {
					responsemessage = "Successfully Authenticated";
				} else {
					responsemessage = "“Failed to Authenticate";
				}

			} else {
				responsemessage = "Please provide key otp";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(new OtpResponse(responsemessage));
	}
}
