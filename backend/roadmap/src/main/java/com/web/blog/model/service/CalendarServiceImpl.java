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

import com.web.blog.model.dto.Curriculum;
import com.web.blog.model.repo.CalendarRepo;

@Service
public class CalendarServiceImpl implements CalendarService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	CalendarRepo calendarRepo;

	@Override
	public Object getCalendarInfo(String nowuid, String uid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int uidnum = Integer.parseInt(uid);
			int nowuidnum = Integer.parseInt(nowuid);

			if (nowuidnum != uidnum)
				throw new RuntimeException("Access Denied");
			List<Curriculum> list = new ArrayList<Curriculum>();
			
			list.addAll(calendarRepo.selectBigByUid(uidnum));
			list.addAll(calendarRepo.selectMiddleByUid(uidnum));
			list.addAll(calendarRepo.selectSmallByUid(uidnum));
			list.addAll(calendarRepo.selectCusByUid(uidnum));
			result.put("calendars", list);
			

		} catch (Exception e) {
			logger.error("Service getCalendarInfo : Something wrong");
			throw e;
		}
		return result;
	}

	@Override
	@Transactional
	public Object updateInfo(Curriculum curriculum) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(curriculum.getSdid() != 0) 
				calendarRepo.updateSmall(curriculum);
			else if(curriculum.getMdid() != 0) 
				calendarRepo.updateMiddle(curriculum);
			else if(curriculum.getBdid() != 0) 
				calendarRepo.updateBig(curriculum);
			else
				calendarRepo.updateCus(curriculum);

		} catch (Exception e) {
			logger.error("Service getCalendarInfo : Something wrong");
			throw e;
		}
		return result;
	}


}
