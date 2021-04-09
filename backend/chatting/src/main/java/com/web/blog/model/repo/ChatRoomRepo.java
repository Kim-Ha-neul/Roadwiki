package com.web.blog.model.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;import org.springframework.data.redis.connection.RedisSubscribedConnectionException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.ChatRoom;
import com.web.blog.model.service.SubscriberService;

@Repository
public class ChatRoomRepo {
	@Autowired
	RedisMessageListenerContainer redisMessageListener;
	
	@Autowired
	SubscriberService subscriberServ;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	private static final String CHAT_ROOMS = "CHAT_ROOM";
	private HashOperations<String, String, ChatRoom> opsHashChatRoom;
	private Map<String, ChannelTopic> topics;
	
	@PostConstruct
	private void init() {
		opsHashChatRoom = redisTemplate.opsForHash();
		topics = new HashMap<>();
	}
	
	public List<ChatRoom> selectAll(){
		List<ChatRoom> rooms =  opsHashChatRoom.values(CHAT_ROOMS);
		if(rooms.size() == 0) {
			insert(new ChatRoom(UUID.randomUUID().toString(), "잡담", 1));
			insert(new ChatRoom(UUID.randomUUID().toString(), "java", 1));
			insert(new ChatRoom(UUID.randomUUID().toString(), "C++", 1));
			insert(new ChatRoom(UUID.randomUUID().toString(), "python", 1));
		}
		return opsHashChatRoom.values(CHAT_ROOMS);
	}
	
	public ChatRoom select(String roomid) {
		return opsHashChatRoom.get(CHAT_ROOMS, roomid);
	}
	
	public ChatRoom insert(ChatRoom chatRoom) {
		opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomid(), chatRoom);
		return chatRoom;
	}
	
	public void enterChatRoom(String roomid) {
		ChannelTopic topic = topics.get(roomid);
		if(topic == null) {
			topic = new ChannelTopic(roomid);
			redisMessageListener.addMessageListener(subscriberServ, topic);
			topics.put(roomid, topic);
		}
	}
	
	public ChannelTopic getTopic(String roomid) {
		return topics.get(roomid);
	}
	
	
}
