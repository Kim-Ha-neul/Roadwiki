package com.web.blog.model.repo;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Comment;
import com.web.blog.model.dto.Posting;

@Repository
public interface CommentRepo {
	public Comment select(int cid) throws SQLException;
	public Posting[] selectList(int uid) throws SQLException;
	public int countComment(int pid) throws SQLException;
	public String selectUserName(int cid) throws SQLException;
	public Comment[] selectListPid(int pid) throws SQLException;
	public int insert(Comment comment) throws SQLException;
	public int update(Comment comment) throws SQLException;
	public int delete(int cid) throws SQLException;
	public int totalCount(int uid) throws SQLException;
}