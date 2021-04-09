package com.web.blog.model.dto;

public class Recomment {
	private int rcid;
	private int cid;
	private int uid;
	private String userName;
	private String content;
	private String createDate;
	private String modifyDate;
	private int likeCnt;
	
	public Recomment() {
		super();
	}
	
	public Recomment(int rcid, int cid, int uid, String userName, String content, String createDate, String modifyDate,
			int likeCnt) {
		super();
		this.rcid = rcid;
		this.cid = cid;
		this.uid = uid;
		this.userName = userName;
		this.content = content;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.likeCnt = likeCnt;
	}
	
	public int getRcid() {
		return rcid;
	}
	public void setRcid(int rcid) {
		this.rcid = rcid;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
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
		return "Recomment [rcid=" + rcid + ", cid=" + cid + ", uid=" + uid + ", userName=" + userName + ", content="
				+ content + ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", likeCnt=" + likeCnt + "]";
	}
}
