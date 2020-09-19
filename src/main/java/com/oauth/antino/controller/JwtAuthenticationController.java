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
					mobileandotp = sendSms(jsonMap.get("mobilenumber").toString());
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

	public String sendSms(String number) {
		String apiKey = "";
		String sendId = "";
		String message = "";
		String language = "english";
		String route = "p";
		String myUrl = "";
		URL url = null;
		;
		try {
			otp=getRandomNumberString();
			apiKey = "LRdQC5kvtrT8AJliwIxge0s9Mb7HS6YFn1GhX2PuEZDozWjm3qb" + "KOU8YfNR7PZGEtmJsBxroL1MgSqaj";
			sendId = "FSTSMS";
			message = URLEncoder.encode(otp, "UTF-8");
			language = "english";
			route = "p";
			myUrl = "https://www.fast2sms.com/dev/bulk?authorization=" + apiKey + "&sender_id=" + sendId + "&message="
					+ message + "&language=" + language + "&route=" + route + "&numbers=" + number;
			 url = new URL(myUrl);
			 HttpsURLConnection con = (HttpsURLConnection)
			 url.openConnection();
			 con.setRequestMethod("GET");
			 con.setRequestProperty("User-Agent", "Mozilla/5.0");
			 con.setRequestProperty("cache-control", "no-cache");
			 System.out.println("Wait..............");
			 int code = con.getResponseCode();
			 System.out.println("Response code : " + code);
			 StringBuffer response = new StringBuffer();
			 BufferedReader br = new BufferedReader(new
		      InputStreamReader(con.getInputStream()));
			 while (true) {
			 String line = br.readLine();
			 if (line == null) {
			 break;
			 }
			 response.append(line);
			 }
			 System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number + "#" + message;

	}

	public String getRandomNumberString() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}
}
