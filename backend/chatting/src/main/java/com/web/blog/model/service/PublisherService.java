package com.web.blog.model.service;

import org.springframework.data.redis.listener.ChannelTopic;

import com.web.blog.model.dto.ChatMsg;

public interface PublisherService {
	public void publish(ChannelTopic topic, ChatMsg message);
}
