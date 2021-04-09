package com.web.blog.model.dto;

public class PostingLikeUser {
	private int uid;
	private int pid;
	
	public PostingLikeUser() {}

	public PostingLikeUser(int uid, int pid) {
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
		return "PostingLikeUser [uid=" + uid + ", pid=" + pid + "]";
	}
	
	
}
