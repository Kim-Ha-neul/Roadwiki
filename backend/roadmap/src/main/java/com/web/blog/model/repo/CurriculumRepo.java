package com.web.blog.model.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.Curriculum;
import com.web.blog.model.dto.Curriculumtext;

@Repository
public interface CurriculumRepo {
	
	
	public Curriculumtext[] selectSmallText(int bdid,int mdid);
	public List<Curriculumtext> selectSmallTextBykeyword(String keyword);
	public Curriculumtext[] selectMiddleText(int bdid);
	public Curriculumtext[] selectBigText();
	
	public List<Curriculumtext> searchSmallText(String text);
	public List<Curriculumtext> searchMiddleText(String text);
	public List<Curriculumtext> searchBigText(String text);
	
	public int insertSmallText(Curriculumtext curriculumtext);
	public int insertMiddleText(Curriculumtext curriculumtext);
	public int insertBigText(Curriculumtext curriculumtext);
	
	public int deleteSmallText(int sdid);
	public int deleteMiddleText(int mdid);
	public int deleteBigText(int bdid);
	
	public List<Curriculum> selectSmallByRmid(int rmid);
	public List<Curriculum> selectMiddleByRmid(int rmid);
	public List<Curriculum> selectBigByRmid(int rmid);
	public List<Curriculum> selectCusByRmid(int rmid);
	
	public int insertSmall(List<Curriculum> curriculumlist);
	public int insertMiddle(List<Curriculum> curriculumlist);
	public int insertBig(List<Curriculum> curriculumlist);
	public int insertCus(List<Curriculum> curriculumlist);
}
