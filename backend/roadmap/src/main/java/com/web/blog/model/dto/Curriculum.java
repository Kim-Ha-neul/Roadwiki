package com.web.blog.model.dto;

public class Curriculum {
	private int key;
	private int rmid;
	private int sdid;
	private int mdid;
	private int bdid;
	private int uid;
	private String loc;
	private String memo;
	private String text;
	private String content;
	private String startdate;
	private String enddate;
	private String category;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getRmid() {
		return rmid;
	}
	public void setRmid(int rmid) {
		this.rmid = rmid;
	}
	public int getSdid() {
		return sdid;
	}
	public void setSdid(int sdid) {
		this.sdid = sdid;
	}
	public int getMdid() {
		return mdid;
	}
	public void setMdid(int mdid) {
		this.mdid = mdid;
	}
	public int getBdid() {
		return bdid;
	}
	public void setBdid(int bdid) {
		this.bdid = bdid;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Curriculum(int key, int rmid, int sdid, int mdid, int bdid, String loc, String memo, String text,
			String content, String startdate, String enddate, String category) {
		super();
		this.key = key;
		this.rmid = rmid;
		this.sdid = sdid;
		this.mdid = mdid;
		this.bdid = bdid;
		this.loc = loc;
		this.memo = memo;
		this.text = text;
		this.content = content;
		this.startdate = startdate;
		this.enddate = enddate;
		this.category = category;
	}
	public Curriculum() {
		super();
	}
	@Override
	public String toString() {
		return "Curriculum [key=" + key + ", rmid=" + rmid + ", sdid=" + sdid + ", mdid=" + mdid + ", bdid=" + bdid
				+ ", loc=" + loc + ", memo=" + memo + ", text=" + text + ", content=" + content + ", startdate="
				+ startdate + ", enddate=" + enddate + ", category=" + category + "]";
	}
	
}
