package com.web.blog.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.dto.RoadmapShare;
import com.web.blog.model.service.LoginServiceImpl;
import com.web.blog.model.service.RoadmapService;
import com.web.blog.model.service.RoadmapshareService;

@RestController
@RequestMapping("/roadmapshare")
public class RoadmapshareController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final String FAIL = "fail";
	private static final String SUCCESS = "success";

	@Autowired
	RoadmapshareService roadmapshareservice;
	
	@Autowired
	RoadmapService roadmapService;
	@Autowired
	LoginServiceImpl loginServ;
	
	
	@GetMapping("/roadmapdatalist")
	public Object getroadmapdatalist(@RequestBody List<Integer> rmidlist) {
		logger.trace("getroadmapdatalist start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			result = (Map<String, Object>) roadmapshareservice.selectRoadmapdataList(rmidlist);
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
	
	@GetMapping("/isshared/{rmid}")
	public Object isShared(@PathVariable int rmid) {
		logger.trace("isShared start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			result = (Map<String, Object>) roadmapshareservice.isShared(rmid);
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
	
	@GetMapping("/get")
	public Object getAllShare() {
		logger.trace("getAllShare start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			result = (Map<String, Object>) roadmapshareservice.selectAll();
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
	
	@GetMapping("/get/name/{name}")
	public Object getShareByName(@PathVariable String name) {
		logger.trace("getShareByName start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			result = (Map<String, Object>) roadmapshareservice.selectByUserName(name);
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
	
	@GetMapping("/get/title/{title}")
	public Object getShareByTitle(@PathVariable String title) {
		logger.trace("getShareByTitle start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			result = (Map<String, Object>) roadmapshareservice.selectByTitle(title);
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
	
	@PostMapping("/insert")
	public Object insertShare(@RequestBody RoadmapShare roadmapshare, HttpServletRequest request) {
		logger.trace("insertShare start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadmapshareservice.insert(roadmapshare, nowuid);
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
	
	@DeleteMapping("/delete/{pid}")
	public Object deleteshare(@PathVariable int pid, HttpServletRequest request) {
		logger.trace("deleteshare start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadmapshareservice.delete(nowuid, pid);
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
	
	@GetMapping("/islike/{pid}")
	public Object getlike(@PathVariable int pid, HttpServletRequest request) {
		logger.trace("getlike start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadmapshareservice.selectlike(nowuid, pid);
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
	@PutMapping("/like/{uid}/{pid}")
	public Object likeshare(@PathVariable int uid,@PathVariable int pid, HttpServletRequest request) {
		logger.trace("likeshare start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadmapshareservice.like(nowuid, uid, pid);
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
	@PutMapping("/dislike/{uid}/{pid}")
	public Object dislikeshare(@PathVariable int uid,@PathVariable int pid, HttpServletRequest request) {
		logger.trace("likeshare start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadmapshareservice.dislike(nowuid, uid, pid);
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
	
	@GetMapping("/main")
	public Object getLikeHigh() {
		logger.trace("getLikeHigh");
		Map<String, Object> result = new HashMap<>();
		int cnt = 5; //프론트에서 받을 수 있음
		HttpStatus status = HttpStatus.OK;
		try {
			result = (Map<String, Object>) roadmapshareservice.getLikeHigh(cnt);
			result.put("msg", SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
}