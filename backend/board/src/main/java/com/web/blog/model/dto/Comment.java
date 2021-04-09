package com.web.blog.model.dto;

public class Comment {
	private int cid;
	private int pid;
	private int uid;
	private String userName;
	private String content;
	private String createDate;
	private String modifyDate;
	private int likeCnt;
	
	public Comment() {
		super();
	}

	public Comment(int cid, int pid, int uid, String userName, String content, String createDate, String modifyDate,
			int likeCnt) {
		super();
		this.cid = cid;
		this.pid = pid;
		this.uid = uid;
		this.userName = userName;
		this.content = content;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.likeCnt = likeCnt;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	@Override
	public String toString() {
		return "Comment [cid=" + cid + ", pid=" + pid + ", uid=" + uid + ", userName=" + userName + ", content="
				+ content + ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", likeCnt=" + likeCnt + "]";
	}
	
}
