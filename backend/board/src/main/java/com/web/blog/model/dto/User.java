package com.web.blog.model.dto;

import java.util.Arrays;
import java.util.List;

public class User {
	private int uid;
	private String password;
	private String email;
	private String name;
	private String createDate;
	private String keyword[];
	private String address;
	private String oneline;
	private String major;
	
	public User() {}

	public User(int uid, String password, String email, String name, String createDate, String[] keyword,
			String address, String oneline, String major) {
		super();
		this.uid = uid;
		this.password = password;
		this.email = email;
		this.name = name;
		this.createDate = createDate;
		this.keyword = keyword;
		this.address = address;
		this.oneline = oneline;
		this.major = major;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String[] getKeyword() {
		return keyword;
	}

	public void setKeyword(String[] keyword) {
		this.keyword = keyword;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOneline() {
		return oneline;
	}

	public void setOneline(String oneline) {
		this.oneline = oneline;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", password=" + password + ", email=" + email + ", name=" + name + ", createDate="
				+ createDate + ", keyword=" + Arrays.toString(keyword) + ", address=" + address + ", oneline=" + oneline
				+ ", major=" + major + "]";
	}
}
