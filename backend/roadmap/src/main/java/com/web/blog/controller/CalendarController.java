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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.dto.Curriculum;
import com.web.blog.model.dto.Roadmap;
import com.web.blog.model.service.CalendarService;
import com.web.blog.model.service.LoginServiceImpl;
import com.web.blog.model.service.RoadmapService;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final String FAIL = "fail";
	private static final String SUCCESS = "success";

	@Autowired
	RoadmapService roadmapservice;
	
	@Autowired
	CalendarService calendarservice;
	@Autowired
	LoginServiceImpl loginServ;

	@GetMapping("/get/{uid}")
	public Object getCalendar(@PathVariable String uid, HttpServletRequest request) {
		logger.trace("getCalendar start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			logger.info("uid : " + uid);
			String nowuid = Integer.toString((int) loginServ.getData(request.getHeader("auth-token")).get("uid"));
			result = (Map<String, Object>) calendarservice.getCalendarInfo(nowuid,uid);
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

	@PutMapping("/modify")
	public Object modifyCalendar(@RequestBody Curriculum curriculum, HttpServletRequest request) {
		logger.trace("modifyCalendar start");
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		try {
			logger.info("Curriculum : " + curriculum.toString());
			result = (Map<String, Object>) calendarservice.updateInfo(curriculum);
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