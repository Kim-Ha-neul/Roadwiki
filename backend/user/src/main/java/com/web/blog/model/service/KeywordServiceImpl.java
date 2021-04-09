package com.web.blog.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.model.dto.Keyword;
import com.web.blog.model.repo.KeywordRepo;

@Service
public class KeywordServiceImpl implements KeywordService {

	@Autowired
	KeywordRepo keywordRepo;
	
	@Override
	public Object getKeywordList() {
		try {
			List<Keyword> list = keywordRepo.selectAll();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
}
