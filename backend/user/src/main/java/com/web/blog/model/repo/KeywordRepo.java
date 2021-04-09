package com.web.blog.model.repo;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Keyword;

@Repository
public interface KeywordRepo {
	public List<Keyword> selectAll() throws SQLException;
}
