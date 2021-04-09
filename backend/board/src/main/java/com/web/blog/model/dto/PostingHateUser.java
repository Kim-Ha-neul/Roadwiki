package com.web.blog.model.dto;

public class PostingHateUser {
	private int uid;
	private int pid;
	
	public PostingHateUser() {}

	public PostingHateUser(int uid, int pid) {
		super();
		this.uid = uid;
		this.pid = pid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "PostingHateUser [uid=" + uid + ", pid=" + pid + "]";
	}
	
	
}
