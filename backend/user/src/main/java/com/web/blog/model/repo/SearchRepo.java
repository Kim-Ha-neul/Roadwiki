package com.web.blog.model.repo;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Posting;
import com.web.blog.model.dto.User;

@Repository
public interface SearchRepo {
	public User[] selectUser(String word) throws SQLException;
	public Posting[] selectPosting(String word) throws SQLException;
}
