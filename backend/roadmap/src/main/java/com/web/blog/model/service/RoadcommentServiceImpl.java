package com.web.blog.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.dto.Curriculum;
import com.web.blog.model.repo.RoadcommentRepo;
import com.web.blog.model.repo.RoadmapRepo;
import com.web.blog.model.repo.UserRepo;

@Service
public class RoadcommentServiceImpl implements RoadcommentService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	
	@Autowired
	UserRepo userrepo;
	
	@Autowired
	RoadcommentRepo roadcommentrepo;
	
	@Autowired
	RoadmapRepo roadmaprepo;

	@Override
	@Transactional
	public Object insert(Curriculum curriculum) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int uid = roadmaprepo.selectUidByRmid(curriculum.getRmid());
			curriculum.setUid(uid);
			if (roadcommentrepo.insert(curriculum) != 1)
				throw new RuntimeException("Query wrong");
		} catch (Exception e) {
			logger.error("Service insert : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object delete(int nowuid, int key, int rmid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int uid = roadmaprepo.selectUidByRmid(rmid);
//			if(nowuid != uid)
//				throw new RuntimeException("wrong user");
			if (roadcommentrepo.delete(key,rmid) != 1)
				throw new RuntimeException("Query wrong");
		} catch (Exception e) {
			logger.error("Service delete : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object deleteAll(int nowuid, int rmid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int uid = roadmaprepo.selectUidByRmid(rmid);
	//		if(nowuid != uid)
	//			throw new RuntimeException("wrong user");
			roadcommentrepo.deleteAll(rmid);
		} catch (Exception e) {
			logger.error("Service deleteAll : Something wrong");
			throw e;
		}
		return result;
	}
	
	@Override
	@Transactional
	public Object update(Curriculum curriculum) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (roadcommentrepo.update(curriculum) != 1)
				throw new RuntimeException("Query wrong");
		} catch (Exception e) {
			logger.error("Service update : Something wrong");
			throw e;
		}
		return result;
	}
	

}
