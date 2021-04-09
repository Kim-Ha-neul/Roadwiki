package com.web.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.service.EmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/email")
@Api(value = "/email")
public class EmailController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	public JavaMailSender javaMailSender;
	
	@Autowired
	EmailService emailServ;
	

	@ApiResponses({
		@ApiResponse(code = 200, message = "success"),
		@ApiResponse(code = 500, message = "fail"),
	})

	
	@ApiOperation(value="인증용 email 발송", response=ResponseEntity.class)
	@GetMapping("/{email}")
	public Object email(@PathVariable String email) throws MessagingException {
		logger.trace("email");
		try {
			logger.info(email);
			Map<String, Object> result = (Map<String, Object>) emailServ.email(email);
			result.put("msg", "success");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", "fail");
			}}, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value="사용자 입력 코드 검증", response=ResponseEntity.class)
	@PostMapping("/")
	public Object authorization(
			@ApiParam(value = "이메일 인증 요청 시 서버에서 보냈던 암호화된 코드와 사용자가 이메일로 받은 원본 코드를 받습니다.")@RequestBody Map<String, Object> map){
		logger.trace("authorization");
		try {
			Map<String, Object> result = (Map<String, Object>) emailServ.authorization(map);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK) ;
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", "fail");
			}}, HttpStatus.OK);
		}
	}

}