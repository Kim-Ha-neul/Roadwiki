package com.web.blog.model.dto;

import java.util.Arrays;

public class Posting {
	private int pid;
	private String classifier;
	private String[] tags;
	private String title;
	private int uid;
	private String content;
	private String createDate;
	private String modifyDate;
	private String image;
	private int likeCnt;
	
	public Posting() {
		super();
	}

	public Posting(int pid, String classifier, String[] tags, String title, int uid, String content, String createDate,
			String modifyDate, String image, int likeCnt) {
		super();
		this.pid = pid;
		this.classifier = classifier;
		this.tags = tags;
		this.title = title;
		this.uid = uid;
		this.content = content;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.image = image;
		this.likeCnt = likeCnt;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getClassifier() {
		return classifier;
	}

	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	@Override
	public String toString() {
		return "Posting [pid=" + pid + ", classifier=" + classifier + ", tag=" + Arrays.toString(tags) + ", title="
				+ title + ", uid=" + uid + ", content=" + content + ", createDate=" + createDate + ", modifyDate="
				+ modifyDate + ", image=" + image + ", likeCnt=" + likeCnt + "]";
	}
}
