package com.web.blog.model.dto;

import java.io.Serializable;

public class ChatRoom implements Serializable{
	private String roomid;
	private String name;
	private int type;
	
	public ChatRoom(String roomid, String name, int type) {
		this.roomid = roomid;
		this.name = name;
		this.type = type;
	}
	
	public ChatRoom(String roomid, String name) {
		this.roomid = roomid;
		this.name = name;
		this.type = 1;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ChatRoom [roomid=" + roomid + ", name=" + name + ", type=" + type + "]";
	}
}
