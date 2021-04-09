package com.web.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.dto.Comment;
import com.web.blog.model.dto.Posting;
import com.web.blog.model.dto.PostingHateUser;
import com.web.blog.model.dto.PostingLikeUser;
import com.web.blog.model.dto.Recomment;
import com.web.blog.model.service.BoardLikeService;
import com.web.blog.model.service.FreeBoardService;
import com.web.blog.model.service.LoginService;

@RestController
@RequestMapping("/freeboard")
public class FreeBoardController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private static final String FAIL = "fail";
	private static final String SUCCESS = "success";
	
	@Autowired
	FreeBoardService fBoardServ;
	
	@Autowired
	LoginService loginServ;
	
	@Autowired
	BoardLikeService boardlikeService;
	
	@GetMapping(value = {
			"/list/{classifier}/{selector}/{word}/{page}",
			"/list/{classifier}/{selector}/{word}/{page}/{tag}",
		})
	public Object getList(@PathVariable String classifier, 
			@PathVariable String selector, 
			@PathVariable String word, 
			@PathVariable String page,
			@PathVariable(required = false) String tag) {
		logger.trace("getList");
		try {
			Map<String, Object> result;
			if(word == " ") word = "";
			if(selector.equals("none")) {
				if(tag == null) result = (Map<String, Object>) fBoardServ.getPostingListAll(page, classifier);
				else result = (Map<String, Object>) fBoardServ.getPostingListAll(page, classifier, tag);
			}
			else if(selector.equals("name")) {
				if(tag == null) result = (Map<String, Object>) fBoardServ.getPostingListByName(page, classifier, word);
				else result = (Map<String, Object>) fBoardServ.getPostingListByName(page, classifier, word, tag);
			}
			else if(selector.equals("title")){
				if(tag == null) result = (Map<String, Object>) fBoardServ.getPostingListByTitle(page, classifier, word);
					else result = (Map<String, Object>) fBoardServ.getPostingListByTitle(page, classifier, word, tag);
			}
			else if(selector.equals("content")){
				if(tag == null) result = (Map<String, Object>) fBoardServ.getPostingListByContent(page, classifier, word);
				else result = (Map<String, Object>) fBoardServ.getPostingListByContent(page, classifier, word, tag);
			} else {
				throw new RuntimeException("wrong select tag");
			}
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@GetMapping("/posting/{pid}")
	public Object getPosting(@PathVariable String pid) {
		try {
			Map<String, Object> result;
			result = (Map<String, Object>) fBoardServ.getPosting(pid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@PostMapping("/posting")
	public Object registPosting(@RequestBody Posting posting, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) fBoardServ.registPosting(posting, uid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@PutMapping("/posting")
	public Object editPosting(@RequestBody Posting posting, HttpServletRequest request) {
		try {
			System.out.println(posting);
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) fBoardServ.editPosting(posting, uid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/posting/{pid}")
	public Object deletePosting(@PathVariable String pid, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) fBoardServ.deletePosting(pid, uid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@PostMapping("/comment")
	public Object registComment(@RequestBody Comment comment, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) fBoardServ.registComment(comment, uid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@PutMapping("/comment")
	public Object editComment(@RequestBody Comment comment, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) fBoardServ.editComment(comment, uid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/comment/{cid}")
	public Object deleteComment(@PathVariable String cid, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) fBoardServ.deleteComment(cid, uid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@PostMapping("/recomment")
	public Object registRecomment(@RequestBody Recomment recomment, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			recomment.setUid(uid);
			result = (Map<String, Object>) fBoardServ.registRecomment(recomment);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@PutMapping("/recomment")
	public Object editRecomment(@RequestBody Recomment recomment, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			recomment.setUid(uid);
			result = (Map<String, Object>) fBoardServ.editRecomment(recomment);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/recomment/{rcid}")
	public Object deleteRecomment(@PathVariable String rcid, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) fBoardServ.deleteRecomment(rcid);
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@GetMapping("/totalCount")
	public Object totalCount() {
		try {
			Map<String, Object> result;
			result = (Map<String, Object>) fBoardServ.totalCount();
			result.put("msg", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	// Posting Like
	@GetMapping("/postinglike/{pid}")
	public Object getPostingLike(@PathVariable int pid, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) boardlikeService.getPostingLikeUser(new PostingLikeUser(uid, pid));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@PostMapping("/postinglike")
	public Object getPostingLike(@RequestBody PostingLikeUser postingLikeUser, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			postingLikeUser.setUid(uid);
			result = (Map<String, Object>) boardlikeService.registPostingLike(postingLikeUser);
			result = (Map<String, Object>) boardlikeService.postingLikeUp(postingLikeUser.getPid());
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/postinglikecancel/{pid}")
	public Object getPostingLikeCancel(@PathVariable int pid, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) boardlikeService.postingLikeDown(pid);
			result = (Map<String, Object>) boardlikeService.deletePostingLike(new PostingLikeUser(uid, pid));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@GetMapping("/postinghate/{pid}")
	public Object getPostingHate(@PathVariable int pid, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) boardlikeService.getPostingHateUser(new PostingHateUser(uid, pid));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@PostMapping("/postinghate")
	public Object getPostingHate(@RequestBody PostingHateUser postingHateUser, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			postingHateUser.setUid(uid);
			result = (Map<String, Object>) boardlikeService.registPostingHate(postingHateUser);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/postinghatecancel/{pid}")
	public Object getPostingHateCancel(@PathVariable int pid, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			int uid = (int) loginServ.getData(request.getHeader("auth-token")).get("uid");
			result = (Map<String, Object>) boardlikeService.deletePostingHate(new PostingHateUser(uid, pid));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@GetMapping("/main")
	public Object getLikeHigh() {
		logger.trace("getLikeHigh");
		Map<String, Object> result = new HashMap<>();
		int cnt = 5; //프론트에서 받을 수 있음
		HttpStatus status = HttpStatus.OK;
		try {
			result = (Map<String, Object>) fBoardServ.getLikeHigh(cnt);
			result.put("msg", SUCCESS);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.put("msg", FAIL);
			result.put("errorMsg", e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	@PutMapping("/postinglikeUp/{pid}")
	public Object postingLikeUp(@PathVariable int pid, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			result = (Map<String, Object>) boardlikeService.postingLikeUp(pid);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@PutMapping("/postinglikeDown/{pid}")
	public Object postingLikeDown(@PathVariable int pid, HttpServletRequest request) {
		try {
			Map<String, Object> result;
			result = (Map<String, Object>) boardlikeService.postingLikeDown(pid);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@GetMapping("/list/{uid}")
	public Object postingListUid(@PathVariable int uid) {
		try {
			Map<String, Object> result;
			result = (Map<String, Object>) fBoardServ.getPostingListUid(uid);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@GetMapping("/likelist/{uid}")
	public Object postingLikeListUid(@PathVariable int uid) {
		try {
			Map<String, Object> result;
			result = (Map<String, Object>) fBoardServ.getPostingLikeListUid(uid);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
	
	@GetMapping("/commentlist/{uid}")
	public Object postingCommentListUid(@PathVariable int uid) {
		try {
			Map<String, Object> result;
			result = (Map<String, Object>) fBoardServ.getPostingCommentListUid(uid);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(new HashMap<String, Object>(){{
				put("errorMsg", e.getMessage());
				put("msg", FAIL);
			}}, HttpStatus.OK);
		}
	}
}