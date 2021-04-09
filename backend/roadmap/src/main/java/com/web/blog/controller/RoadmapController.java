package com.web.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.dto.Roadmap;
import com.web.blog.model.service.LoginServiceImpl;
import com.web.blog.model.service.RoadmapService;

@RestController
@RequestMapping("/roadmap")
public class RoadmapController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final String FAIL = "fail";
	private static final String SUCCESS = "success";

	@Autowired
	RoadmapService roadmapservice;

	@Autowired
	LoginServiceImpl loginServ;

	@GetMapping("/list/{uid}")
	public Object listRoadmap(@PathVariable int uid, HttpServletRequest request) {
		logger.trace("listRoadmap start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			logger.info("uid : " + uid);
			int nowuid = 0;
			if (request.getHeader("auth-token") != null) {
				logger.info(request.getHeader("auth-token"));
				logger.info("nowuid : " + loginServ.getData(request.getHeader("auth-token")).get("uid"));
				nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			}
			result = (Map<String, Object>) roadmapservice.getRoadmapListByUid(nowuid, uid);
			result.put("msg", SUCCESS);
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	@GetMapping("/Official")
	public Object listOfficialRoadmap() {
		logger.trace("listOfficialRoadmap start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			result = (Map<String, Object>) roadmapservice.getOfficialRoadmapList();
			result.put("msg", SUCCESS);
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	@GetMapping("/log/{uid}/{rmorder}")
	public Object listLog(@PathVariable int uid, @PathVariable int rmorder, HttpServletRequest request) {
		logger.trace("listLog start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			logger.info("uid : " + uid + " rmorder : " + rmorder);
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadmapservice.getRoadmapListByRmorder(nowuid, uid, rmorder);
			result.put("msg", SUCCESS);
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	@GetMapping("/get/{rmid}")
	public Object getRoadmap(@PathVariable int rmid, HttpServletRequest request) {
		logger.trace("getRoadmap start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {

			logger.info("rmid : " + rmid);
			// int nowuid = (int)
			// loginServ.getData(request.getHeader("auth-token")).get("uid");
			int nowuid = 0;
			result = (Map<String, Object>) roadmapservice.getRoadmap(nowuid, rmid);
			result.put("msg", SUCCESS);
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	@GetMapping("/get/comment/{rmid}")
	public Object getRoadmapComment(@PathVariable int rmid) {
		logger.trace("getRoadmapComment start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			logger.info("rmid : " + rmid);
			result = (Map<String, Object>) roadmapservice.getRoadmapComment(rmid);
			result.put("msg", SUCCESS);
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	@PostMapping("/create")
	public Object createRoadmap(@RequestBody Roadmap roadmap, HttpServletRequest request) {
		logger.trace("createRoadmap start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		try {
			logger.info("roadmap : " + roadmap.toString());
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadmapservice.create(nowuid, roadmap);
			result.put("msg", SUCCESS);
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	@PostMapping("/update")
	public Object updateRoadmap(@RequestBody Roadmap roadmap, HttpServletRequest request) {
		logger.trace("updateRoadmap start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		try {
			logger.info("roadmap : " + roadmap.toString());
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadmapservice.modify(nowuid, roadmap);
			result.put("msg", SUCCESS);
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	@DeleteMapping("/delete/{uid}/{rmorder}")
	public Object deleteRoadmap(@PathVariable int uid, @PathVariable int rmorder, HttpServletRequest request) {
		logger.trace("deleteRoadmap start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		try {
			logger.info("uid : " + uid + " rmorder : " + rmorder);
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadmapservice.deleteRoadmap(nowuid, uid, rmorder);
			result.put("msg", SUCCESS);
		} catch (NumberFormatException e) {
			logger.error("input data type error");
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

}