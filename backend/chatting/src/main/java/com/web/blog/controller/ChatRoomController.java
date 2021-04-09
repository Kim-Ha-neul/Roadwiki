package com.web.blog.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.dto.ChatMsg;
import com.web.blog.model.dto.ChatRoom;
import com.web.blog.model.service.ChatRoomService;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private static final String FAIL = "fail";
	private static final String SUCCESS = "success";
	
	@Autowired
	ChatRoomService chatRoomServ;
	
	@PostMapping("/create")
	public Object createRoom(@RequestBody String name) {
		logger.trace("create room");
		Map<String, Object> result = null;
		try {
			result = chatRoomServ.createOpenRoom(name);
			result.put("msg", SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/open")
	public Object getAllRoom(@RequestParam(required = false) String name) {
		logger.trace("get open room");
		Map<String, Object> result = null;
		try {
			if(name == null) name = "";
			result = chatRoomServ.getOpenRoom(name);
			result.put("msg", SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/room/{roomid}")
	@ResponseBody
	public Object joinRoom(@PathVariable String roomid) {
		logger.trace("join chat room");
		Map<String, Object> result = null;
		try {			
			result = chatRoomServ.getChatRoom(roomid);
			result.put("msg", SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>() {
				{
					put("errorMsg", e.getMessage());
					put("msg", FAIL);
				}
			}, HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	
}
