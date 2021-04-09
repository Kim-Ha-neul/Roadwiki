package com.web.blog.model.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public interface SubscriberService extends MessageListener{
	public void onMessage(Message message, byte[] pattern);
}
