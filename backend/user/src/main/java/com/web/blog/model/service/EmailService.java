package com.web.blog.model.service;

import java.util.Map;

import javax.mail.MessagingException;

public interface EmailService {
	public Object email(String email) throws MessagingException;
	public Object authorization(Map<String, Object> map);
}
