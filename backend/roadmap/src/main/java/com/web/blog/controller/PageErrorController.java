package com.web.blog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageErrorController implements ErrorController{
 
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
    private static final String ERROR_PATH = "/error";
 
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping("/error")
    public Object handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
		Map<String, Object> result = new HashMap<String, Object>(){{
			put("code", status.toString());
			put("msg", httpStatus.getReasonPhrase());
			put("timestamp", new Date());
		}};
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
 
}