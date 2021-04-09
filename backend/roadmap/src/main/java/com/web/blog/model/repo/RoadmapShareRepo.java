package com.web.blog.model.repo;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Roadmap;
import com.web.blog.model.dto.RoadmapShare;

@Repository
public interface RoadmapShareRepo {
	public int insert(RoadmapShare share);
	public int delete(int uid,int pid);

	public int isShared(int rmid);
	
	public RoadmapShare[] selectAll();
	public RoadmapShare[] selectByUserName(String name);
	public RoadmapShare[] selectByTitle(String title);
	
	public int updatelike(int uid,int pid);
	public int insertlike(int uid, int pid);
	
	public int updatedislike(int uid, int pid);
	public int deletelike(int uid,int pid);
	
	public int selectlike(int uid, int pid);
	
	public RoadmapShare[] selectLikeHigh(int cnt);
	
}
