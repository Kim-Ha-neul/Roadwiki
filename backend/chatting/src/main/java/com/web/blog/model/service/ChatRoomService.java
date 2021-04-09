package com.web.blog.model.service;

import java.util.Map;

import com.web.blog.model.dto.ChatRoom;

public interface ChatRoomService {
	public Map<String, Object> createOpenRoom(String name);
	public Map<String, Object> getChatRoom(String roomid);
	public Map<String, Object> getOpenRoom(String name);
}
