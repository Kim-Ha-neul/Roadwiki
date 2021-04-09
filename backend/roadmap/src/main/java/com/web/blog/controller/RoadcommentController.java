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

import com.web.blog.model.dto.Curriculum;
import com.web.blog.model.dto.RoadmapShare;
import com.web.blog.model.service.LoginServiceImpl;
import com.web.blog.model.service.RoadcommentService;
import com.web.blog.model.service.RoadmapService;
import com.web.blog.model.service.RoadmapshareService;

@RestController
@RequestMapping("/roadcomment")
public class RoadcommentController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final String FAIL = "fail";
	private static final String SUCCESS = "success";

	@Autowired
	RoadcommentService roadcommentService;
	
	@Autowired
	LoginServiceImpl loginServ;
	
	
	@PutMapping("/insert")
	public Object insertComment(@RequestBody Curriculum curriculum) {
		logger.trace("insertComment start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			result = (Map<String, Object>) roadcommentService.insert(curriculum);
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
	
	@PutMapping("/update")
	public Object updateComment(@RequestBody Curriculum curriculum) {
		logger.trace("updateComment start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			result = (Map<String, Object>) roadcommentService.update(curriculum);
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
	
	@PostMapping("/delete")
	public Object deleteComment(@RequestBody Curriculum curriculum, HttpServletRequest request) {
		logger.trace("deleteComment start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadcommentService.delete(nowuid, curriculum.getKey(), curriculum.getRmid());
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
	
	@DeleteMapping("/deleteall/{rmid}")
	public Object deleteAllComment(@PathVariable int rmid, HttpServletRequest request) {
		logger.trace("deleteAllComment start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			int nowuid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) roadcommentService.deleteAll(nowuid, rmid);
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