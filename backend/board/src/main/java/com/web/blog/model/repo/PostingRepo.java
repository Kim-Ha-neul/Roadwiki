package com.web.blog.model.repo;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Posting;

@Repository
public interface PostingRepo {
	public Posting select(int pid) throws SQLException;
	public Posting[] selectPid() throws SQLException;
	public String[] selectTags(int pid) throws SQLException;
	public int selectPostingLike(int pid) throws SQLException;
	public Posting[] selectListAll(Map<String, Object> map) throws SQLException;
	public Posting[] selectListUid(int uid) throws SQLException;
	public Posting[] selectListAllTag(int page, int cnt, String classifier, String tag) throws SQLException;
	public Posting[] selectListName(int page, int cnt, String classifier, String word) throws SQLException;
	public Posting[] selectListNameTag(int page, int cnt, String classifier, String word, String tag) throws SQLException;
	public Posting[] selectListTitle(int page, int cnt, String classifier, String word) throws SQLException;
	public Posting[] selectListTitleTag(int page, int cnt, String classifier, String word, String tag) throws SQLException;
	public Posting[] selectListContent(int page, int cnt, String classifier, String word) throws SQLException;
	public Posting[] selectListContentTag(int page, int cnt, String classifier, String word, String tag) throws SQLException;
	public int insert(Posting posting) throws SQLException;
	public int insertTag(Map<String, Object> map) throws SQLException;
	public int update(Posting posting) throws SQLException;
	public int delete(int pid) throws SQLException;
	public int totalCount() throws SQLException;	
	public Posting[] selectLikeHigh(int cnt);
	public Posting[] postingListUid(int uid);
	public Posting[] selectList(int uid);
	public int deleteTags(int pid) throws SQLException;
}
