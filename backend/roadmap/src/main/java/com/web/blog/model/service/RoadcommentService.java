package com.web.blog.model.service;

import com.web.blog.model.dto.Curriculum;

public interface RoadcommentService {
	
	public Object insert(Curriculum curriculum) throws Exception;
	public Object delete(int nowuid,int key,int rmid) throws Exception;
	public Object deleteAll(int nowuid, int rmid) throws Exception;
	public Object update(Curriculum curriculum) throws Exception;
	
	
}	
