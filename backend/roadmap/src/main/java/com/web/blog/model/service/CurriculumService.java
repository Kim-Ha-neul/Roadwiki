package com.web.blog.model.service;

import com.google.gson.JsonArray;
import com.web.blog.model.dto.Curriculumtext;

public interface CurriculumService {
	
	public Object insertText(int uid,Curriculumtext curriculumtext);
	public Object deleteText(int uid,Curriculumtext curriculumtext);
	public Object getSuggest(int uid) throws Exception;
	public Object getSuggestBybdid(int uid,int bdid) throws Exception;
	public Object getSuggestBybdidmdid(int uid,int bdid, int mdid) throws Exception;

	
	public void create(int rmid,JsonArray nodeDataArray);
	public JsonArray getCurriculumByrmid(int rmid);
	public Object getSuggestBySearch(String text);
	public JsonArray getCurriculumCommentByrmid(int rmid);
}	
