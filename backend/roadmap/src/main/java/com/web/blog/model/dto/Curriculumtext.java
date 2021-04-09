package com.web.blog.model.dto;

public class Curriculumtext{
	private int bdid;
	private int mdid;
	private int sdid;
	private String text;
	private String content;
	
	
	public int getBdid() {
		return bdid;
	}


	public void setBdid(int bdid) {
		this.bdid = bdid;
	}


	public int getMdid() {
		return mdid;
	}


	public void setMdid(int mdid) {
		this.mdid = mdid;
	}


	public int getSdid() {
		return sdid;
	}


	public void setSdid(int sdid) {
		this.sdid = sdid;
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


	public Curriculumtext() {
	}


	public Curriculumtext(int bdid, int mdid, int sdid, String text, String content) {
		this.bdid = bdid;
		this.mdid = mdid;
		this.sdid = sdid;
		this.text = text;
		this.content = content;
	}


	@Override
	public String toString() {
		return "Curriculumtext [bdid=" + bdid + ", mdid=" + mdid + ", sdid=" + sdid + ", text=" + text + ", content="
				+ content + "]";
	}
	
	
}