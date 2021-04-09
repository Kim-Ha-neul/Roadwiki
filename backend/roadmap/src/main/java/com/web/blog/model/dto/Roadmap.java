package com.web.blog.model.dto;

public class Roadmap {
	private int rmid;
	private int uid;
	private String name;
	private int rmorder;
	private String createDate;
	private String tmp;
	private int permission;
	private int term;
	public Roadmap(int rmid, int uid, String name, int rmorder, String createDate, String tmp, int permission,
			int term) {
		super();
		this.rmid = rmid;
		this.uid = uid;
		this.name = name;
		this.rmorder = rmorder;
		this.createDate = createDate;
		this.tmp = tmp;
		this.permission = permission;
		this.term = term;
	}
	public Roadmap() {
		super();
	}
	public int getRmid() {
		return rmid;
	}
	public void setRmid(int rmid) {
		this.rmid = rmid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRmorder() {
		return rmorder;
	}
	public void setRmorder(int rmorder) {
		this.rmorder = rmorder;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getTmp() {
		return tmp;
	}
	public void setTmp(String tmp) {
		this.tmp = tmp;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	@Override
	public String toString() {
		return "Roadmap [rmid=" + rmid + ", uid=" + uid + ", name=" + name + ", rmorder=" + rmorder + ", createDate="
				+ createDate + ", tmp=" + tmp + ", permission=" + permission + ", term=" + term + "]";
	}
	
	
}
