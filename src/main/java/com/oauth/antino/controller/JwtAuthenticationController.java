package com.oauth.antino.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.antino.config.JwtTokenUtil;
import com.oauth.antino.model.JwtRequest;
import com.oauth.antino.model.JwtResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	private String otp = "";

	@RequestMapping(value = "/authenticate", method = RequestMethod.GET)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody final Map jsonMap) {
		String token = "";
		String mobileandotp = "";
		Map map=new HashMap();
		String regex = "(0/91)?[7-9][0-9]{9}";
		try {
			if(jsonMap.containsKey("mobilenumber")){
				if(jsonMap.get("mobilenumber").toString().matches(regex)){
					//mobileandotp = sendSms(jsonMap.get("mobilenumber").toString());
					token = jwtTokenUtil.generateToken(mobileandotp);
			
			map.put("token", token);
			map.put("otp", otp);
			}else{
				map.put("Mobile Number Format ", "plsease provide valid mobile no");
			}
			}else{
				map.put("Missing key ", "mobilenumber");
				return ResponseEntity.ok(map);
		}} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(map);

	}



	public String getRandomNumberString() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}
}
