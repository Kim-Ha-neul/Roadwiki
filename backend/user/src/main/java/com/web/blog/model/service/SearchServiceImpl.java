package com.web.blog.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.model.dto.Posting;
import com.web.blog.model.dto.User;
import com.web.blog.model.repo.SearchRepo;

@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	SearchRepo searchRepo;
	
	@Override
	public Object getSearchList(String word) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("user", searchRepo.selectUser("%"+word+"%"));
			result.put("posting", searchRepo.selectPosting("%"+word+"%"));
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

}
