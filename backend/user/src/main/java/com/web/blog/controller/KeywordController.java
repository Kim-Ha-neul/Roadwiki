package com.web.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.dto.Keyword;
import com.web.blog.model.service.KeywordService;

@RestController
@RequestMapping("/keyword")
public class KeywordController {
	
	@Autowired
	KeywordService keywordService;
	
	@GetMapping("/list")
	public Object keywordList() {
		List<Keyword> list = (List<Keyword>) keywordService.getKeywordList();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("keywords", list);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
}
