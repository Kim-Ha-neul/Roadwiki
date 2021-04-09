package com.web.blog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.web.blog.model.service.LoginServiceImpl;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	LoginServiceImpl loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		} else {
			String token = request.getHeader("auth-token");
			if (token != null && token.length() > 0) {
				try {
					loginService.validation(token);
					return true;
				} catch (Exception e) {
					throw e;
				}
			} else {
				throw new RuntimeException("Token does not exist");
			}
		}
	}

}
