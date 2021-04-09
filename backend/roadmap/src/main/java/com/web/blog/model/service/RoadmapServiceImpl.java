package com.web.blog.model.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.blog.model.dto.Roadmap;
import com.web.blog.model.repo.RoadmapRepo;

@Service
public class RoadmapServiceImpl implements RoadmapService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RoadmapRepo roadmaprepo;
	@Autowired
	CurriculumService curriservice;

	@Override
	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	public Object create(int nowuid, Roadmap map) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int uidnum = map.getUid();
			if (nowuid != uidnum)
				throw new RuntimeException("wrong user");
			
			JsonObject jsonObject = new JsonParser().parse(map.getTmp()).getAsJsonObject();
			JsonArray nodeDataArray = jsonObject.getAsJsonArray("nodeDataArray");
			jsonObject.remove("nodeDataArray");
			map.setTmp(jsonObject.toString());
			
			if (roadmaprepo.insert(map) != 1)
				throw new RuntimeException("Query wrong");
			int rmid = roadmaprepo.selectlastRmidByUid(nowuid);
			curriservice.create(rmid, nodeDataArray);
		} catch (Exception e) {
			logger.error("Service create : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	public Object modify(int nowuid, Roadmap map) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (nowuid != map.getUid())
				throw new RuntimeException("wrong user");

			JsonObject jsonObject = new JsonParser().parse(map.getTmp()).getAsJsonObject();
			JsonArray nodeDataArray = jsonObject.getAsJsonArray("nodeDataArray");
			jsonObject.remove("nodeDataArray");
			map.setTmp(jsonObject.toString());
			
			if (roadmaprepo.update(map) != 1)
				throw new RuntimeException("Query wrong");
			
			int rmid = roadmaprepo.selectlastRmidByUid(nowuid);
			curriservice.create(rmid, nodeDataArray);
			
		} catch (Exception e) {
			logger.error("Service modify : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object deleteRoadmap(int nowuid, int uid, int rmorder) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (nowuid != uid)
				throw new RuntimeException("wrong user");

			if (roadmaprepo.delete(rmorder, uid) == 0)
				throw new RuntimeException("Query wrong");

		} catch (Exception e) {
			logger.error("Service deleteRoadmap : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object getRoadmapListByUid(int nowuid, int uid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (nowuid == uid)
				result.put("roadmaps", roadmaprepo.selectMyRoadmapListByUid(uid));
			else
				result.put("roadmaps", roadmaprepo.selectOtherRoadmapListByUid(uid));

		} catch (Exception e) {
			logger.error("Service getRoadmapListByUid : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object getRoadmapListByRmorder(int nowuid, int uid, int rmorder) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (uid != nowuid)
				throw new RuntimeException("wrong user");

			result.put("roadmaps", roadmaprepo.selectRoadmapListByRmorder(rmorder, uid));
		} catch (Exception e) {
			logger.error("Service getRoadmapListByRmorder : Something wrong");
			throw e;
		}
		
		return result;
	}

	@Override
	public Object getRoadmap(int nowuid, int rmid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int uidnum = roadmaprepo.selectUidByRmid(rmid);
			if (uidnum < 0) {
				nowuid = uidnum;
			}
			Roadmap roadmap = null;
			if (nowuid == uidnum)
				roadmap = roadmaprepo.selectMyRoadmap(rmid);
			else
				roadmap = roadmaprepo.selectOtherRoadmap(rmid);

			if (roadmap == null)
				new RuntimeException("access denied");
			JsonObject jsonObject = new JsonParser().parse(roadmap.getTmp()).getAsJsonObject();
			JsonArray nodeDataArray = curriservice.getCurriculumByrmid(rmid);
			jsonObject.add("nodeDataArray", nodeDataArray);
			roadmap.setTmp(jsonObject.toString());
			result.put("roadmaps", roadmap);
		} catch (Exception e) {
			logger.error("Service getRoadmap : Something wrong");
			throw e;
		}
		return result;
	}
	
	@Override
	public Object getRoadmapComment(int rmid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Roadmap roadmap = null;
			roadmap = roadmaprepo.selectMyRoadmap(rmid);

			if (roadmap == null)
				new RuntimeException("access denied");
			JsonObject jsonObject = new JsonParser().parse(roadmap.getTmp()).getAsJsonObject();
			JsonArray nodeDataArray = curriservice.getCurriculumCommentByrmid(rmid);
			jsonObject.add("nodeDataArray", nodeDataArray);
			roadmap.setTmp(jsonObject.toString());
			result.put("roadmaps", roadmap);
		} catch (Exception e) {
			logger.error("Service getRoadmapComment : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object getOfficialRoadmapList() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("roadmaps", roadmaprepo.selectOfficialListRoadmap());
		} catch (Exception e) {
			logger.error("Service getRoadmapListByUid : Something wrong");
			throw e;
		}
		return result;
	}

}
