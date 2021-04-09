package com.web.blog.model.service;

import com.web.blog.model.dto.Posting;
import com.web.blog.model.dto.User;

public interface SearchService {
	public Object getSearchList(String word) throws Exception;
}
