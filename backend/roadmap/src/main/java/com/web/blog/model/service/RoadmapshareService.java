package com.web.blog.model.service;

import java.util.List;

import com.web.blog.model.dto.RoadmapShare;

public interface RoadmapshareService {
	
	public Object selectRoadmapdataList(List<Integer> rmidlist) throws Exception;
	
	public Object selectAll() throws Exception;
	public Object selectByUserName(String name) throws Exception;
	public Object selectByTitle(String title) throws Exception;
	
	public Object insert(RoadmapShare roadmapshare,int nowuid) throws Exception;
	public Object delete(int uid, int pid) throws Exception;
	
	public Object selectlike(int uid,int pid) throws Exception;
	public Object like(int nowuid,int uid,int pid) throws Exception;
	public Object dislike(int nowuid,int uid,int pid) throws Exception;

	public Object isShared(int rmid) throws Exception;
	public Object getLikeHigh(int cnt) throws Exception;
	
}	
