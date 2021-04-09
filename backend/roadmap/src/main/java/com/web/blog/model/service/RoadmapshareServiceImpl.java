package com.web.blog.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.dto.Roadmap;
import com.web.blog.model.dto.RoadmapShare;
import com.web.blog.model.repo.RoadmapShareRepo;
import com.web.blog.model.repo.UserRepo;

@Service
public class RoadmapshareServiceImpl implements RoadmapshareService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RoadmapShareRepo roadmapshareRepo;
	
	@Autowired
	UserRepo userrepo;
	
	@Autowired
	RoadmapService roadmapService;
	
	
	@Override
	public Object isShared(int rmid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			boolean shared = roadmapshareRepo.isShared(rmid) == 0 ? false : true;
			result.put("isShared", shared);
		} catch (Exception e) {
			logger.error("Service selectByUserName : Something wrong");
			throw e;
		}
		return result;
	}
	
	@Override
	public Object selectRoadmapdataList(List<Integer> rmidlist) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Roadmap> roadmaplist = new ArrayList<Roadmap>();
		try {
			for(int rmid : rmidlist) {
				roadmaplist.add((Roadmap)roadmapService.getRoadmap(0, rmid));
			}
			result.put("roadmapdatalist", roadmaplist);
			
		} catch (Exception e) {
			logger.error("Service selectByUserName : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object selectAll() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			RoadmapShare[] share = roadmapshareRepo.selectAll();
			String[] name = new String[share.length];
			
			for(int i = 0 ; i < name.length ; i++)
				name[i] = userrepo.getName(share[i].getUid());
			result.put("roadmapshares", share);
			result.put("username", name);
		} catch (Exception e) { 
			logger.error("Service selectAll : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object selectByUserName(String uname) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			RoadmapShare[] share = roadmapshareRepo.selectByUserName(uname);
			String[] name = new String[share.length];
			
			for(int i = 0 ; i < name.length ; i++)
				name[i] = userrepo.getName(share[i].getUid());
			result.put("roadmapshares", share);
			result.put("username", name);
		} catch (Exception e) {
			logger.error("Service selectByUserName : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object selectByTitle(String title) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			RoadmapShare[] share = roadmapshareRepo.selectByTitle(title);
			String[] name = new String[share.length];
			
			for(int i = 0 ; i < name.length ; i++)
				name[i] = userrepo.getName(share[i].getUid());
			result.put("roadmapshares", share);
			result.put("username", name);
		} catch (Exception e) {
			
			logger.error("Service selectByTitle : Something wrong");
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object insert(RoadmapShare roadmapshare,int nowuid) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(roadmapshare.getUid() != nowuid)
				throw new RuntimeException("wrong user");
			if(roadmapshareRepo.insert(roadmapshare) != 1)
				throw new RuntimeException("query wrong");
		} catch (Exception e) {
			logger.error("Service insert : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object delete(int nowuid, int pid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(roadmapshareRepo.delete(nowuid,pid) != 1)
				throw new RuntimeException("not your roadmap");
		} catch (Exception e) {
			logger.error("Service delete : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object selectlike(int uid, int pid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(roadmapshareRepo.selectlike(uid, pid) == 0)
				result.put("islike", false);
			else
				result.put("islike", true);
		} catch (Exception e) {
			logger.error("Service selectlike : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object like(int nowuid, int uid, int pid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			System.out.println(uid + " " + pid);
			if(nowuid != uid)
				throw new RuntimeException("wrong user");
			if(roadmapshareRepo.updatelike(uid, pid) != 1)
				throw new RuntimeException("wrong query");
			if(roadmapshareRepo.insertlike(uid, pid) != 1)
				throw new RuntimeException("wrong query");
			
		} catch (Exception e) {
			
			logger.error("Service like : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object dislike(int nowuid, int uid, int pid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(nowuid != uid)
				throw new RuntimeException("wrong user");
			if(roadmapshareRepo.deletelike(uid, pid) != 1)
				throw new RuntimeException("wrong query");
			if(roadmapshareRepo.updatedislike(uid, pid) != 1)
				throw new RuntimeException("wrong query");
			
		} catch (Exception e) {
			logger.error("Service like : Something wrong");
			throw e;
		}
		return result;
	}
	
	@Override
	@Transactional
	public Object getLikeHigh(int cnt) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("roadmapshares" , roadmapshareRepo.selectLikeHigh(cnt));
		} catch (Exception e) {
			logger.error("Service getLikeHigh : Something wrong");
			throw e;
		}
		return result;
	}

}
