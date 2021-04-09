package com.web.blog.model.repo;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Follow;
import com.web.blog.model.dto.User;

@Repository
public interface FollowRepo {
	public Follow[] selectFollowers(int toUid) throws SQLException;
	public User[] selectFollowerUid(int uid) throws SQLException;
	public Follow[] selectFollowings(int fromUid) throws SQLException;
	public int insert(Follow follow) throws SQLException;
	public int delete(Follow follow) throws SQLException;
}
