package com.web.blog.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.web.blog.model.dto.ChatMsg;

@Service
public class PublisherServiceImpl implements PublisherService{

	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	public void publish(ChannelTopic topic, ChatMsg message) {
		try {
			redisTemplate.convertAndSend(topic.getTopic(), message);			
		} catch(Exception e) {
			throw e;
		}
	}
}
