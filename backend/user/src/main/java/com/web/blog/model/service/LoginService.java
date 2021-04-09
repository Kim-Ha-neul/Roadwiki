package com.web.blog.model.service;

import java.util.Map;

import com.web.blog.model.dto.User;

public interface LoginService {
	public String generate(User user);
	public void validation(String token);
	public Map<String, Object> getData(String token);
}
