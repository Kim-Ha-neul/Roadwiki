package com.web.blog.model.dto;

import java.util.List;

public class Keyword {
	private int kwid;
	private String word;
	
	public Keyword() {
	}

	public Keyword(int kwid, String word) {
		super();
		this.kwid = kwid;
		this.word = word;
	}

	public int getKwid() {
		return kwid;
	}

	public void setKwid(int kwid) {
		this.kwid = kwid;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public String toString() {
		return "Keyword [kwid=" + kwid + ", word=" + word + "]";
	}
	
	
}
