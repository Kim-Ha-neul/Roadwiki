package com.web.blog.component;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisComponent implements ApplicationRunner{
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public void run(ApplicationArguments args) throws Exception{
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		values.set("chatting", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " chatting server start");
	}
}
