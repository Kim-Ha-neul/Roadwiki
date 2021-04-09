package com.web.blog.model.repo;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Recomment;

@Repository
public interface RecommentRepo {
	public Recomment select(int rcid) throws SQLException;
	public String selectUserName(int rcid) throws SQLException;
	public Recomment[] selectListCid(int cid) throws SQLException;
	public int insert(Recomment recomment) throws SQLException;
	public int update(Recomment recomment) throws SQLException;
	public int delete(int rcid) throws SQLException;
}
