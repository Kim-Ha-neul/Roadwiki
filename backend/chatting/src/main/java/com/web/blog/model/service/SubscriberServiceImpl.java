package com.web.blog.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.blog.model.dto.ChatMsg;

@Service
public class SubscriberServiceImpl implements SubscriberService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	SimpMessageSendingOperations messagingTemplate;
	
	@Override
	public void onMessage(Message message, byte[] pattern){
		try {
			String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
			ChatMsg roomMsg = objectMapper.readValue(publishMessage, ChatMsg.class);
			messagingTemplate.convertAndSend("/sub/chat/room/" + roomMsg.getRoomid(), roomMsg);
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
}
