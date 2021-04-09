package com.web.blog.model.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.web.blog.model.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class LoginServiceImpl implements LoginService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private String signature = "original ks";
	private Long expirationMin =365L * 24 * 60 * 60 * 1000;
	
	public String generate(User user){
		JwtBuilder jwtBuilder = Jwts.builder();
		
		jwtBuilder.setHeaderParams(new HashMap<String, Object>(){{
			put("typ", "JWT");
			put("alg", "HS256");
		}});
		
		jwtBuilder.setSubject("login token");
		jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + expirationMin));
		jwtBuilder.addClaims(new HashMap<String, Object>(){{
			put("uid", user.getUid());
			put("name", user.getName());
			put("email", user.getEmail());
		}});
		
		jwtBuilder.signWith(SignatureAlgorithm.HS256, signature.getBytes());
		
		String token = jwtBuilder.compact();
		return token;
	}

	
	public void validation(String token) {
		try {
			Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(token);
		} catch(Exception e) {
			throw new RuntimeException("Token is not valid");
		}
	}
	
	
	public Map<String, Object> getData(String token) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(token);
		} catch (Exception e) {
			throw new RuntimeException("Data cannot be read.");
		}
		return claims.getBody();
	}
}
