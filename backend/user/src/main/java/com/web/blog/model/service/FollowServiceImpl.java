package com.web.blog.model.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.dto.Follow;
import com.web.blog.model.dto.User;
import com.web.blog.model.repo.FollowRepo;

@Service
public class FollowServiceImpl implements FollowService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	FollowRepo followRepo;

	@Override
	public Object getInfo(int fromuid, int touid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Follow[] followerList = followRepo.selectFollowers(touid);
			for (int i = 0; i < followerList.length; i++) {
				if (followerList[i].getFromuid() == fromuid) {
					result.put("isFollow", true);
					break;
				} else {
					result.put("isFollow", false);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	public Object getFollowerList(int touid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Follow[] followerList = followRepo.selectFollowers(touid);
			User[] users = new User[followerList.length];
			
			for (int i = 0; i < followerList.length; i++) {
				int uid = followerList[i].getFromuid();
				User[] u = followRepo.selectFollowerUid(uid);
				users[i] = u[0];
			}
			result.put("followerlists", users);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	public Object getFollowingList(int fromuid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Follow[] followerList = followRepo.selectFollowings(fromuid);
			User[] users = new User[followerList.length];

			for (int i = 0; i < followerList.length; i++) {
				int uid = followerList[i].getTouid();
				User[] u = followRepo.selectFollowerUid(uid);
				users[i] = u[0];
			}
			result.put("followinglists", users);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	@Transactional
	public Object userFollow(Follow follow) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			followRepo.insert(follow);
			result.put("msg", "success");
		} catch (Exception e) {
			result.put("msg", "fail");
		}
		return result;
	}

	@Override
	@Transactional
	public Object userUnfollow(Follow follow) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			followRepo.delete(follow);
			result.put("msg", "success");
		} catch (Exception e) {
			result.put("msg", "fail");
		}
		return result;
	}
}
