package com.web.blog.model.service;

import java.util.Map;

import com.web.blog.model.dto.Comment;
import com.web.blog.model.dto.Posting;
import com.web.blog.model.dto.Recomment;

public interface FreeBoardService {
	public Object getPostingListAll(String page_s, String classifier, String...tags) throws Exception;
	public Object getPostingListUid(int uid) throws Exception;
	public Object getPostingListByName(String page_s, String classifier, String word, String...tags) throws Exception;
	public Object getPostingListByTitle(String page_s, String classifier, String word, String...tags) throws Exception;
	public Object getPostingListByContent(String page_s, String classifier, String word, String...tags) throws Exception;
	public Object getPosting(String pid) throws Exception;
	public Object registPosting(Posting posting, int uid) throws Exception;
	public Object editPosting(Posting posting, int uid) throws Exception;
	public Object deletePosting(String pid, int uid) throws Exception;
	public Object registComment(Comment comment, int uid) throws Exception;
	public Object editComment(Comment comment, int uid) throws Exception;
	public Object deleteComment(String cid, int uid) throws Exception;
	public Object registRecomment(Recomment recomment) throws Exception;
	public Object editRecomment(Recomment recomment) throws Exception;
	public Object deleteRecomment(String rcid) throws Exception;
	public Object totalCount() throws Exception;
	public Object getPostingByCommentUid(int uid) throws Exception;	
	public Object getLikeHigh(int cnt) throws Exception;
	public Object postingListUid(int uid) throws Exception;
	public Object getPostingLikeListUid(int uid) throws Exception;
	public Object getPostingCommentListUid(int uid) throws Exception;
}
