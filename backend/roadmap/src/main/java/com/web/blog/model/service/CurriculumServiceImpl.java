package com.web.blog.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.web.blog.model.dto.Curriculum;
import com.web.blog.model.dto.Curriculumtext;
import com.web.blog.model.repo.CurriculumRepo;
import com.web.blog.model.repo.RoadcommentRepo;
import com.web.blog.model.repo.UserRepo;

@Service
public class CurriculumServiceImpl implements CurriculumService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	CurriculumRepo curriculumrepo;

	@Autowired
	UserRepo userrepo;

	@Autowired
	RoadcommentRepo roadcommentrepo;

	@Override
	@Transactional
	public Object insertText(int uid, Curriculumtext curriculumtext) {
		// sdid , mdid, bdid 순으로 검사하면서 있으면 그 위치에 넣는 repo 실행
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (uid > 0)
				throw new RuntimeException("Access denied");

			if (curriculumtext.getMdid() != 0)
				if (curriculumrepo.insertSmallText(curriculumtext) != 1)
					throw new RuntimeException("Query wrong");
				else if (curriculumtext.getBdid() != 0)
					if (curriculumrepo.insertMiddleText(curriculumtext) != 1)
						throw new RuntimeException("Query wrong");
					else if (curriculumrepo.insertBigText(curriculumtext) != 1)
						throw new RuntimeException("Query wrong");
		} catch (Exception e) {
			logger.error("Service insertText : Something wrong");
			throw e;
		}
		return result;

	}

	@Override
	@Transactional
	public Object deleteText(int uid, Curriculumtext curriculumtext) {
		// sdid,mdid,bdid 순으로 검사하면서 있으면 그 위치에 꺼 삭제하는 repo 실행
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (uid > 0)
				throw new RuntimeException("Access denied");

			if (curriculumtext.getSdid() != 0)
				if (curriculumrepo.deleteSmallText(curriculumtext.getSdid()) != 1)
					throw new RuntimeException("Query wrong");
				else if (curriculumtext.getMdid() != 0)
					if (curriculumrepo.deleteMiddleText(curriculumtext.getMdid()) != 1)
						throw new RuntimeException("Query wrong");
					else if (curriculumtext.getBdid() != 0)
						if (curriculumrepo.deleteBigText(curriculumtext.getBdid()) != 1)
							throw new RuntimeException("Query wrong");
		} catch (Exception e) {
			logger.error("Service deleteText : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object getSuggest(int uid) throws Exception { // big
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Curriculumtext> usersmall = new ArrayList<Curriculumtext>();
			for (String keyword : userrepo.selectkeywordtext(uid))
				usersmall.addAll(curriculumrepo.selectSmallTextBykeyword(keyword));

			Curriculumtext[] big = curriculumrepo.selectBigText();
			List<Curriculumtext> randombox = new ArrayList<Curriculumtext>();
			
			for (Curriculumtext bi : big) {
				randombox.add(bi);
				for (Curriculumtext us : usersmall) {
					if (us.getBdid() == bi.getBdid())
						for(int i = 0 ; i < 5;  i++)
							randombox.add(bi);
				}
			}
			int rand = (int)(Math.random()*randombox.size());
			result.put("recommend", randombox.get(rand));
			result.put("suggest", big);
		} catch (Exception e) {
			logger.error("Service getSuggestBybdid : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object getSuggestBybdid(int uid, int bdid) throws Exception { // middle
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Curriculumtext> usersmall = new ArrayList<Curriculumtext>();
			for (String keyword : userrepo.selectkeywordtext(uid))
				usersmall.addAll(curriculumrepo.selectSmallTextBykeyword(keyword));

			Curriculumtext[] middle = curriculumrepo.selectMiddleText(bdid);
			List<Curriculumtext> randombox = new ArrayList<Curriculumtext>();
			
			for (Curriculumtext mid : middle) {
				randombox.add(mid);
				for (Curriculumtext us : usersmall) {
					if (us.getMdid() == mid.getMdid())
						for(int i = 0 ; i < 5;  i++)
							randombox.add(mid);
				}
			}
			int rand = (int)(Math.random()*randombox.size());
			// 함수
			
			
			result.put("recommend", randombox.get(rand));
			result.put("suggest", middle);
			String[] keywords = userrepo.selectkeywordtext(uid);

		} catch (Exception e) {
			logger.error("Service getSuggestBybdid : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object getSuggestBybdidmdid(int uid, int bdid, int mdid) throws Exception { // small
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Curriculumtext> usersmall = new ArrayList<Curriculumtext>();
			for (String keyword : userrepo.selectkeywordtext(uid))
				usersmall.addAll(curriculumrepo.selectSmallTextBykeyword(keyword));

			Curriculumtext[] small = curriculumrepo.selectSmallText(bdid, mdid);
			List<Curriculumtext> randombox = new ArrayList<Curriculumtext>();
			
			for (Curriculumtext sm : small) {
				randombox.add(sm);
				for (Curriculumtext us : usersmall) {
					if (us.getMdid() == sm.getMdid())
						for(int i = 0 ; i < 5;  i++)
							randombox.add(sm);
				}
			}
			int rand = (int)(Math.random()*randombox.size());
			result.put("recommend", randombox.get(rand));
			result.put("suggest", small);

		} catch (Exception e) {
			logger.error("Service getSuggestBybdid : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	public Object getSuggestBySearch(String text) { // search
		Map<String, Object> result = new HashMap<String, Object>();
		List<Curriculumtext> currilist = new ArrayList<Curriculumtext>();
		try {
			currilist.addAll(curriculumrepo.searchBigText(text));
			currilist.addAll(curriculumrepo.searchMiddleText(text));
			currilist.addAll(curriculumrepo.searchSmallText(text));
			result.put("suggest", currilist);
		} catch (Exception e) {
			logger.error("Service getSuggestBybdid : Something wrong");
			throw e;
		}
		return result;
	}

	// 여기까지 추천 용
	// 여기부터 로드맵 용

	@Override
	@Transactional
	public void create(int rmid, JsonArray nodeDataArray) {
		// small, middle, big 나누어서 따로 list 만들어 저장하고 각각 repo실행
		List<Curriculum> small = new ArrayList<Curriculum>();
		List<Curriculum> middle = new ArrayList<Curriculum>();
		List<Curriculum> big = new ArrayList<Curriculum>();
		List<Curriculum> cus = new ArrayList<Curriculum>();

		try {
			for (int i = 0; i < nodeDataArray.size(); i++) {
				Curriculum curr = new Curriculum();
				JsonObject nodedata = nodeDataArray.get(i).getAsJsonObject();
				curr.setCategory(nodedata.get("category").getAsString());
				curr.setContent(nodedata.get("content").getAsString());
				curr.setLoc(nodedata.get("loc").getAsString());
				curr.setEnddate(nodedata.get("enddate").getAsString());
				curr.setStartdate(nodedata.get("startdate").getAsString());
				curr.setMemo(nodedata.get("memo").getAsString());
				curr.setRmid(rmid);
				curr.setKey(nodedata.get("key").getAsInt());
				curr.setText(nodedata.get("text").getAsString());
				curr.setMdid(nodedata.get("mdid").getAsInt());
				curr.setBdid(nodedata.get("bdid").getAsInt());
				curr.setSdid(nodedata.get("sdid").getAsInt());
				System.out.println(curr);
				if (curr.getSdid() != 0)
					small.add(curr);
				else if (curr.getMdid() != 0)
					middle.add(curr);
				else if (curr.getBdid() != 0)
					big.add(curr);
				else
					cus.add(curr);
			}
			if (small.size() != 0)
				if (curriculumrepo.insertSmall(small) == 0)
					throw new RuntimeException("small Query wrong");
			if (middle.size() != 0)
				if (curriculumrepo.insertMiddle(middle) == 0)
					throw new RuntimeException("middle Query wrong");
			if (big.size() != 0)
				if (curriculumrepo.insertBig(big) == 0)
					throw new RuntimeException("big Query wrong");
			if (cus.size() != 0)
				if (curriculumrepo.insertCus(cus) == 0)
					throw new RuntimeException("cus Query wrong");
		} catch (Exception e) {
			logger.error("Curri Service create : Something wrong");
			throw e;
		}
	}

	@Override
	public JsonArray getCurriculumByrmid(int rmid) {
		// small, middle, big 에서 rmid로 싹 긁어오고 json으로 만들어서 보내주기
		JsonArray nodeDataArray = new JsonArray();
		List<Curriculum> Curriculumlist = new ArrayList<Curriculum>();

		try {
			Curriculumlist.addAll(curriculumrepo.selectSmallByRmid(rmid));
			Curriculumlist.addAll(curriculumrepo.selectMiddleByRmid(rmid));
			Curriculumlist.addAll(curriculumrepo.selectBigByRmid(rmid));
			Curriculumlist.addAll(curriculumrepo.selectCusByRmid(rmid));

			for (Curriculum curri : Curriculumlist) {
				JsonObject obj = new JsonObject();
				obj.addProperty("category", curri.getCategory());
				obj.addProperty("content", curri.getContent());
				obj.addProperty("loc", curri.getLoc());
				obj.addProperty("enddate", curri.getEnddate());
				obj.addProperty("startdate", curri.getStartdate());
				obj.addProperty("memo", curri.getMemo());
				obj.addProperty("rmid", curri.getRmid());
				obj.addProperty("key", curri.getKey());
				obj.addProperty("text", curri.getText());
				obj.addProperty("mdid", curri.getMdid());
				obj.addProperty("bdid", curri.getBdid());
				obj.addProperty("sdid", curri.getSdid());
				nodeDataArray.add(obj);
			}
		} catch (Exception e) {
			logger.error("Service getCurriculumByrmid : Something wrong");
			throw e;
		}
		return nodeDataArray;
	}

	@Override
	public JsonArray getCurriculumCommentByrmid(int rmid) {
		// small, middle, big 에서 rmid로 싹 긁어오고 json으로 만들어서 보내주기
		JsonArray nodeDataArray = new JsonArray();
		List<Curriculum> Curriculumlist = new ArrayList<Curriculum>();

		try {
			Curriculumlist.addAll(curriculumrepo.selectSmallByRmid(rmid));
			Curriculumlist.addAll(curriculumrepo.selectMiddleByRmid(rmid));
			Curriculumlist.addAll(curriculumrepo.selectBigByRmid(rmid));
			Curriculumlist.addAll(curriculumrepo.selectCusByRmid(rmid));
			Curriculumlist.addAll(roadcommentrepo.selectRoadComment(rmid));

			for (Curriculum curri : Curriculumlist) {
				JsonObject obj = new JsonObject();
				obj.addProperty("category", curri.getCategory());
				obj.addProperty("content", curri.getContent());
				obj.addProperty("loc", curri.getLoc());
				obj.addProperty("enddate", curri.getEnddate());
				obj.addProperty("startdate", curri.getStartdate());
				obj.addProperty("memo", curri.getMemo());
				obj.addProperty("rmid", curri.getRmid());
				obj.addProperty("key", curri.getKey());
				obj.addProperty("text", curri.getText());
				obj.addProperty("mdid", curri.getMdid());
				obj.addProperty("bdid", curri.getBdid());
				obj.addProperty("sdid", curri.getSdid());
				nodeDataArray.add(obj);
			}
		} catch (Exception e) {
			logger.error("Service getCurriculumByrmid : Something wrong");
			throw e;
		}
		return nodeDataArray;
	}
	
	public List<Curriculumtext> 데이터분석(){
		
		return null;
	}
}
