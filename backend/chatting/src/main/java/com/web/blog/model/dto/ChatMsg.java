package com.web.blog.model.dto;

public class ChatMsg {
	public enum MsgType {
		TALK, JOIN
	}
	private MsgType type;
	private String roomid;
	private String sender;
	private String msg;
	public ChatMsg() {
		super();
	}
	public ChatMsg(MsgType type, String roomid, String sender, String msg) {
		super();
		this.type = type;
		this.roomid = roomid;
		this.sender = sender;
		this.msg = msg;
	}
	public MsgType getType() {
		return type;
	}
	public void setType(MsgType type) {
		this.type = type;
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "ChatMsg [type=" + type + ", roomid=" + roomid + ", sender=" + sender + ", msg=" + msg + "]";
	}
}
