package com.web.blog.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.dto.Posting;
import com.web.blog.model.dto.PostingHateUser;
import com.web.blog.model.dto.PostingLikeUser;
import com.web.blog.model.repo.PostingLikeUserRepo;
import com.web.blog.model.repo.PostingRepo;

@Service
public class BoardLikeServieImpl implements BoardLikeService {
	
	@Autowired
	PostingRepo postingRepo;
	
	@Autowired
	PostingLikeUserRepo postinglikeuserRepo;

	@Override
	public Object getPostingLikeUser(PostingLikeUser postingLikeUser) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			PostingLikeUser postLike = postinglikeuserRepo.select(postingLikeUser);
			result.put("msg", "success");
			result.put("like", postLike);
			result.put("likeCnt", postinglikeuserRepo.totallike(postingLikeUser.getPid()));
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public Object getPostingLikeList(int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Posting[] postings = postinglikeuserRepo.selectList(uid);
			result.put("msg", "success");
			result.put("likepostings", postings);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	@Transactional
	public Object registPostingLike(PostingLikeUser postingLikeUser) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			postinglikeuserRepo.insert(postingLikeUser);
			result.put("msg", "success");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}


	@Override
	@Transactional
	public Object deletePostingLike(PostingLikeUser postingLikeUser) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			postinglikeuserRepo.delete(postingLikeUser);
			result.put("msg", "success");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	public Object getPostingHateUser(PostingHateUser postingHateUser) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			PostingHateUser postHate = postinglikeuserRepo.selectHate(postingHateUser);
			result.put("msg", "success");
			result.put("like", postHate);
			result.put("dislikeCnt", postinglikeuserRepo.totalhate(postingHateUser.getPid()));
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object registPostingHate(PostingHateUser postingHateUser) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			postinglikeuserRepo.insertHate(postingHateUser);
			result.put("msg", "success");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}


	@Override
	@Transactional
	public Object deletePostingHate(PostingHateUser postingHateUser) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			postinglikeuserRepo.deleteHate(postingHateUser);
			result.put("msg", "success");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	@Transactional
	public Object postingLikeUp(int pid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put("pid", pid);
			data.put("likeCnt", (postingRepo.select(pid).getLikeCnt() + 1));
			postinglikeuserRepo.updateLikeCnt(data);
			result.put("msg", "success");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	@Transactional
	public Object postingLikeDown(int pid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put("pid", pid);
			data.put("likeCnt", (postingRepo.select(pid).getLikeCnt() - 1));
			postinglikeuserRepo.updateLikeCnt(data);
			result.put("msg", "success");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
}
