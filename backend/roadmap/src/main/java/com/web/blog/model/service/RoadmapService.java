package com.web.blog.model.service;

import com.web.blog.model.dto.Roadmap;

public interface RoadmapService {
	
	public Object create(int nowuid,Roadmap map) throws Exception;
	public Object modify(int nowuid,Roadmap map);
	public Object getRoadmapListByUid(int nowuid,int uid); // rmorder createdate가장나중
	public Object getRoadmap(int nowuid,int rmid);
	public Object getRoadmapListByRmorder(int nowuid, int uid,int rmorder); // 추가적인 요소 끄트 ?
	public Object deleteRoadmap(int nowuid,int uid,int rmorder);
	public Object getOfficialRoadmapList();
	public Object getRoadmapComment(int rmid);
}	
