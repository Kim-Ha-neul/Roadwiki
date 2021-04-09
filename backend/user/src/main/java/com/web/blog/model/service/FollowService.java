package com.web.blog.model.service;

import com.web.blog.model.dto.Follow;

public interface FollowService {
	public Object getInfo(int fromuid, int touid) throws Exception;
	public Object getFollowerList(int touid) throws Exception;
	public Object getFollowingList(int touid) throws Exception;
	public Object userFollow(Follow follow);
	public Object userUnfollow(Follow follow);
}
