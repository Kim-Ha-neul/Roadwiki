package com.web.blog.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.dto.Comment;
import com.web.blog.model.dto.Posting;
import com.web.blog.model.dto.Recomment;
import com.web.blog.model.repo.CommentRepo;
import com.web.blog.model.repo.PostingLikeUserRepo;
import com.web.blog.model.repo.PostingRepo;
import com.web.blog.model.repo.RecommentRepo;
import com.web.blog.model.repo.UserRepo;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	PostingRepo postingRepo;

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	RecommentRepo recommentRepo;
	
	@Autowired
	PostingLikeUserRepo postingLikeUserRepo;
	
	@Autowired
	UserRepo userRepo;

	final static String[] TAG = new String[] { "tag" };
	final static int[] PAGESIZE = new int[] { 10 };

	@Override
	public Object getPostingListAll(String page_s, String classifier, String... tags) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int cnt = PAGESIZE[0];
			int page = (Integer.parseInt(page_s) - 1) * cnt;

			if (tags.length == 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", page);
				map.put("cnt", cnt);
				Posting[] p = postingRepo.selectListAll(map);
				for (int i = 0; i < p.length; i++) {
					int likeCnt = postingRepo.selectPostingLike(p[i].getPid());
					p[i].setLikeCnt(likeCnt);
				}
				result.put("postings", p);
			} else {
				result.put("postings", postingRepo.selectListAllTag(page, cnt, classifier, tags[0]));
			}
			Posting[] Postings = (Posting[]) result.get("postings");
			String[] names = new String[Postings.length];
			int[] commentCnt = new int[Postings.length];
			for (int i = 0; i < Postings.length; i++) {
				names[i] = userRepo.getName(Postings[i].getUid()).getName();
				commentCnt[i] = commentRepo.countComment(Postings[i].getPid());
			}
			result.put("names", names);
			result.put("commentCnts", commentCnt);
			result.put("msg", "success");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object getPostingListUid(int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Posting[] p = postingRepo.selectListUid(uid);
			for (int i = 0; i < p.length; i++) {
				int likeCnt = postingRepo.selectPostingLike(p[i].getPid());
				p[i].setLikeCnt(likeCnt);
			}
			result.put("postings", p);
			result.put("commentCnt", commentRepo.totalCount(uid));
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object getPostingListByName(String page_s, String classifier, String word, String... tags) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int cnt = PAGESIZE[0];
			int page = 1 + (Integer.parseInt(page_s) - 1) * cnt;
			if (tags.length == 0) {
				result.put("postings", postingRepo.selectListName(page, cnt, classifier, word));
			} else {
				result.put("postings", postingRepo.selectListNameTag(page, cnt, classifier, word, tags[0]));
			}
			Posting[] Postings = (Posting[]) result.get("postings");
			String[] names = new String[Postings.length];
			int[] commentCnt = new int[Postings.length];
			for (int i = 0; i < Postings.length; i++) {
				names[i] = userRepo.getName(Postings[i].getUid()).getName();
				commentCnt[i] = commentRepo.countComment(Postings[i].getPid());
			}
			result.put("names", names);
			result.put("commentCnts", commentCnt);
			result.put("msg", "success");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object getPostingListByTitle(String page_s, String classifier, String word, String... tags)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		System.out.println(word);
		try {
			int cnt = PAGESIZE[0];
			int page = (Integer.parseInt(page_s) - 1) * cnt;
			if (tags.length == 0) {
				result.put("postings", postingRepo.selectListTitle(page, cnt, classifier, word));
			} else {
				result.put("postings", postingRepo.selectListTitleTag(page, cnt, classifier, word, tags[0]));
			}
			Posting[] Postings = (Posting[]) result.get("postings");
			String[] names = new String[Postings.length];
			int[] commentCnt = new int[Postings.length];
			for (int i = 0; i < Postings.length; i++) {
				names[i] = userRepo.getName(Postings[i].getUid()).getName();
				commentCnt[i] = commentRepo.countComment(Postings[i].getPid());
			}
			result.put("names", names);
			result.put("commentCnts", commentCnt);
			result.put("msg", "success");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object getPostingListByContent(String page_s, String classifier, String word, String... tags)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int cnt = PAGESIZE[0];
			int page = 1 + (Integer.parseInt(page_s) - 1) * cnt;
			if (tags.length == 0) {
				result.put("postings", postingRepo.selectListContent(page, cnt, classifier, word));
			} else {
				result.put("postings", postingRepo.selectListContentTag(page, cnt, classifier, word, tags[0]));
			}
			Posting[] Postings = (Posting[]) result.get("postings");
			String[] names = new String[Postings.length];
			int[] commentCnt = new int[Postings.length];
			for (int i = 0; i < Postings.length; i++) {
				names[i] = userRepo.getName(Postings[i].getUid()).getName();
				commentCnt[i] = commentRepo.countComment(Postings[i].getPid());
			}
			result.put("names", names);
			result.put("commentCnts", commentCnt);
			result.put("msg", "success");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object getPosting(String pid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Posting posting = postingRepo.select(Integer.parseInt(pid));
			if (posting == null)
				throw new RuntimeException("cannot fine posting");
			// 태그 삽입
			String[] tags = postingRepo.selectTags(Integer.parseInt(pid));
			posting.setTags(tags);
			result.put("posting", posting);
				
			List<Comment> comments = new ArrayList<>();
			for (Comment c : commentRepo.selectListPid(Integer.parseInt(pid))) {
				String name = commentRepo.selectUserName(c.getCid());
				c.setUserName(name);
				comments.add(c);
			}
			result.put("comments", comments);

			List<Recomment>[] recomments = new ArrayList[comments.size()];
			for (int i = 0; i < recomments.length; i++) {
				recomments[i] = new ArrayList<Recomment>();
			}

			for (int i = 0; i < recomments.length; i++) {
				for (Recomment rc : recommentRepo.selectListCid(comments.get(i).getCid())) {
					String recommentName = recommentRepo.selectUserName(rc.getRcid());
					rc.setUserName(recommentName);
					recomments[i].add(rc);
				}
			}
			result.put("recomments", recomments);
			result.put("user", userRepo.getName(posting.getUid()));
//			result.put("oneline", userRepo.getOneline(posting.getUid()));

		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object registPosting(Posting posting, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (posting.getUid() != uid)
				throw new RuntimeException("wrong user");

			if (postingRepo.insert(posting) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");

			String[] tags = posting.getTags();
			int pid = postingRepo.selectPid()[0].getPid();
			// tag 삽입
			for (int i = 0; i < tags.length; i++) {
				Map<String, Object> postingTagMap = new HashMap<String, Object>();
				postingTagMap.put("pid", pid);
				postingTagMap.put("tagid", i + 1);
				postingTagMap.put("tagname", tags[i]);
				postingRepo.insertTag(postingTagMap);
			}
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object editPosting(Posting posting, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (postingRepo.select(posting.getPid()).getUid() != uid)
				throw new RuntimeException("wrong user");
			if (postingRepo.update(posting) == 1) {
				String[] tags = posting.getTags();
				int pid = postingRepo.selectPid()[0].getPid();
				// 먼저 지우고
				postingRepo.deleteTags(pid);
				// tag 재삽입
				for (int i = 0; i < tags.length; i++) {
					Map<String, Object> postingTagMap = new HashMap<String, Object>();
					postingTagMap.put("pid", pid);
					postingTagMap.put("tagid", i + 1);
					postingTagMap.put("tagname", tags[i]);
					postingRepo.insertTag(postingTagMap);
				}
				result.put("msg", "success");
			}
			else
				result.put("msg", "fail");
			
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object deletePosting(String pid_s, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int pid = Integer.parseInt(pid_s);
			if (postingRepo.select(pid).getUid() != uid)
				throw new RuntimeException("wrong user");
			if (postingRepo.delete(pid) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object registComment(Comment comment, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
//			if(commentRepo.select(comment.getUid() != uid) throw new RuntimeException("wrong user");
			if (commentRepo.insert(comment) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object editComment(Comment comment, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
//			if(commentRepo.select(comment.getCid()).getUid() != uid) throw new RuntimeException("wrong user");
			if (commentRepo.update(comment) == 1) {
				result.put("msg", "success");
			}
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object deleteComment(String cid, int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (commentRepo.select(Integer.parseInt(cid)).getUid() != uid)
				throw new RuntimeException("wrong user");
			if (commentRepo.delete(Integer.parseInt(cid)) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object registRecomment(Recomment recomment) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (recommentRepo.insert(recomment) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object editRecomment(Recomment recomment) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (recommentRepo.update(recomment) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object deleteRecomment(String rcid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
//			if (recommentRepo.select(Integer.parseInt(rcid)).getUid() != uid)
//				throw new RuntimeException("wrong user");
			if (recommentRepo.delete(Integer.parseInt(rcid)) == 1)
				result.put("msg", "success");
			else
				result.put("msg", "fail");
		} catch (NumberFormatException e) {
			throw new RuntimeException("input data type error");
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public Object totalCount() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int total = postingRepo.totalCount();
			result.put("total", total);
			result.put("msg", "success");
		} catch (SQLException e) {
			logger.error("sql error");
			result.put("msg", "fail");
		}
		return result;
	}
	
	@Override
	public Object getPostingByCommentUid(int uid) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Posting[] commentpostings = commentRepo.selectList(uid);
			result.put("commentpostings", commentpostings);
			result.put("msg", "success");
		} catch (SQLException e) {
			logger.error("sql error");
			result.put("msg", "fail");
		}
		return result;
	}
	
	@Override
	@Transactional
	public Object getLikeHigh(int cnt) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("postings" , postingRepo.selectLikeHigh(cnt));
		} catch (Exception e) {
			logger.error("sql error");
			throw e;
		}
		return result;
	}

	@Override
	public Object postingListUid(int uid) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("postings" , postingRepo.postingListUid(uid));
		} catch (Exception e) {
			logger.error("postingListUid sql error");
			throw e;
		}
		return result;
	}
	
	@Override
	public Object getPostingLikeListUid(int uid) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("likepostings" , postingLikeUserRepo.selectList(uid));
		} catch (Exception e) {
			logger.error("postingListUid sql error");
			throw e;
		}
		return result;
	}
	
	@Override
	public Object getPostingCommentListUid(int uid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("commentpostings" , commentRepo.selectList(uid));
		} catch (Exception e) {
			logger.error("postingListUid sql error");
			throw e;
		}
		return result;
	}
}
