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

import com.web.blog.model.dto.Follow;
import com.web.blog.model.service.FollowService;
import com.web.blog.model.service.LoginServiceImpl;

@RestController
@RequestMapping("/follow")
public class FollowController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final String FAIL = "fail";
	private static final String SUCCESS = "success";
	
	@Autowired
	FollowService followService;
	
	@Autowired
	LoginServiceImpl loginServ;

	@GetMapping("/list/{uid}")
	public Object getInfo(@PathVariable int uid, HttpServletRequest request) {
		try {
			int nowUid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			Map<String, Object> result = (Map<String, Object>) followService.getInfo(nowUid, uid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.OK);
		}
	}
	
	@GetMapping("/followerlist/{uid}")
	public Object getFollowList(@PathVariable int uid, HttpServletRequest request) {
		try {
			Map<String, Object> result = (Map<String, Object>) followService.getFollowerList(uid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.OK);
		}
	}
	
	@GetMapping("/followinglist/{uid}")
	public Object getFollowingList(@PathVariable int uid, HttpServletRequest request) {
		try {
			Map<String, Object> result = (Map<String, Object>) followService.getFollowingList(uid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.OK);
		}
	}
	
	
	@PostMapping("/userfollow")
	public Object userFollow(@RequestBody Follow follow, HttpServletRequest request) {
		try {
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			follow.setFromuid(uid);
			Map<String, Object> result = (Map<String, Object>) followService.userFollow(follow);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/userunfollow/{touid}")
	public Object userUnfollow(@PathVariable int touid, HttpServletRequest request) {
		try {
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			Map<String, Object> result = (Map<String, Object>) followService.userUnfollow(new Follow(uid, touid));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.OK);
		}
	}

}
