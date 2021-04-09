package com.web.blog.model.service;

import com.web.blog.model.dto.PostingHateUser;
import com.web.blog.model.dto.PostingLikeUser;

public interface BoardLikeService {
	public Object getPostingLikeUser(PostingLikeUser postingLikeUser) throws Exception;
	public Object getPostingLikeList(int uid) throws Exception;
	public Object registPostingLike(PostingLikeUser postingLikeUser) throws Exception;
	public Object deletePostingLike(PostingLikeUser postingLikeUser) throws Exception;
	public Object getPostingHateUser(PostingHateUser postingHateUser) throws Exception;
	public Object registPostingHate(PostingHateUser postingHateUser) throws Exception;
	public Object deletePostingHate(PostingHateUser postingHateUser) throws Exception;
	public Object postingLikeUp(int pid) throws Exception;
	public Object postingLikeDown(int pid) throws Exception;
}