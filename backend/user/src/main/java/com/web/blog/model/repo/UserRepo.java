package com.web.blog.model.repo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.User;

@Repository
public interface UserRepo {
	public User select(String email) throws SQLException;
	public User selectUid(int uid) throws SQLException;
	public int[] selectkeyword(int uid) throws SQLException;
	public String[] selectkeywordtext(int uid) throws SQLException;
	public int insert(User user) throws SQLException;
	public int insertkeyword(Map data) throws SQLException;
	public int update(User user) throws SQLException;
	public int updatekeyword(Map data) throws SQLException;
	public int delete(String email) throws SQLException;
	public int deletekeyword(int uid) throws SQLException;
	public String getName(int uid) throws SQLException;
	public User canName(String name) throws SQLException;
	public int updatepassword(Map data) throws SQLException;
}
