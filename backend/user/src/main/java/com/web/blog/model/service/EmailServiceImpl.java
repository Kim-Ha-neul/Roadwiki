package com.web.blog.model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	public JavaMailSender javaMailSender;

	static final int KEY = 106106;
	
	@Override
	public Object email(String email) throws MessagingException {
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++)
			sb.append(rand.nextInt(8) + 1);

		int oriText = Integer.parseInt(sb.toString());
		logger.info("original text : " + oriText);
		int encryptText = oriText ^ KEY;
		logger.info("encrypt text : " + String.valueOf(encryptText));

		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, false);
			helper.setFrom("team_code_C106");
			helper.setTo(email);
			helper.setSubject("certification request");
			helper.setText("certification number : " + oriText);
			javaMailSender.send(message);

			return new HashMap<String, Object>(){{
				put("code", String.valueOf(encryptText));
				put("email", email);
			}};
		} catch (Exception e) {
			throw new RuntimeException("email error");
		}
	}

	@Override
	public Object authorization(Map<String, Object> map) {
		String email = (String) map.get("email");
		String code = (String) map.get("code");
		String inputCode = (String) map.get("inputCode");
		
		if(email.length() == 0 || code.length() < 6) {
			return new HashMap<String, Object>(){{put("msg", "fail");}};
		}

		int decryptText = Integer.parseInt(code)^KEY;
		
		logger.info("email : " + email);
		logger.info("user input : " + inputCode);
		logger.info("encrypt text : " + code);
		logger.info("decrypt text : " + decryptText);
		if(String.valueOf(decryptText).equals(inputCode)) {
			return new HashMap<String, Object>(){{
				put("msg", "success");
			}};	
		} else {
			return new HashMap<String, Object>(){{
				put("msg", "fail");
			}};	
		}
	}
	
}
