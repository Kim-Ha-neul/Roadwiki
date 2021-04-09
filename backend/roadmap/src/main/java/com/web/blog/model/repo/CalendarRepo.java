package com.web.blog.model.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Curriculum;

@Repository
public interface CalendarRepo {
	
	
	public List<Curriculum> selectBigByUid(int uid);
	public List<Curriculum> selectMiddleByUid(int uid);
	public List<Curriculum> selectSmallByUid(int uid);
	public List<Curriculum> selectCusByUid(int uid);
	
	public int updateBig(Curriculum curriculum);
	public int updateMiddle(Curriculum curriculum);
	public int updateSmall(Curriculum curriculum);
	public int updateCus(Curriculum curriculum);
	
}
