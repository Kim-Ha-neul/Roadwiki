package com.web.blog.model.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Curriculum;

@Repository
public interface RoadcommentRepo {
	
	public List<Curriculum> selectRoadComment(int rmid);
	
	public int insert(Curriculum roadcomment);
	
	public int delete(int key,int rmid);
	public int deleteAll(int rmid);
	
	public int update(Curriculum roadcomment);
	
}
