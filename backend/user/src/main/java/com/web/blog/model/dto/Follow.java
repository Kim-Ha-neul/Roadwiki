package com.web.blog.model.dto;

public class Follow {
	private int fromuid;
	private int touid;
	
	public Follow() {
		super();
	}

	public Follow(int fromuid, int touid) {
		super();
		this.fromuid = fromuid;
		this.touid = touid;
	}

	public int getFromuid() {
		return fromuid;
	}

	public void setFromuid(int fromuid) {
		this.fromuid = fromuid;
	}

	public int getTouid() {
		return touid;
	}

	public void setTouid(int touid) {
		this.touid = touid;
	}

	@Override
	public String toString() {
		return "Follow [fromuid=" + fromuid + ", touid=" + touid + "]";
	}

}
