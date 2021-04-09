package com.web.blog.model.dto;

public class RoadmapShare {
	private int pid;
	private int uid;
	private int rmid;
	private int likecnt;
	private String title;
	private String createDate;
	private Boolean useroadback;
	public RoadmapShare(int pid, int uid, int rmid, int likecnt, String title, String createDate, Boolean useroadback) {
		super();
		this.pid = pid;
		this.uid = uid;
		this.rmid = rmid;
		this.likecnt = likecnt;
		this.title = title;
		this.createDate = createDate;
		this.useroadback = useroadback;
	}
	public RoadmapShare() {
		super();
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
	public int getRmid() {
		return rmid;
	}
	public void setRmid(int rmid) {
		this.rmid = rmid;
	}
	public int getLikecnt() {
		return likecnt;
	}
	public void setLikecnt(int likecnt) {
		this.likecnt = likecnt;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Boolean getUseroadback() {
		return useroadback;
	}
	public void setUseroadback(Boolean useroadback) {
		this.useroadback = useroadback;
	}
	@Override
	public String toString() {
		return "RoadmapShare [pid=" + pid + ", uid=" + uid + ", rmid=" + rmid + ", likecnt=" + likecnt + ", title="
				+ title + ", createDate=" + createDate + ", useroadback=" + useroadback + "]";
	}
	
	
}
