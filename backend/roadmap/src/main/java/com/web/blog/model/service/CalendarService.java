package com.web.blog.model.service;

import com.web.blog.model.dto.Curriculum;

public interface CalendarService {
	
	public Object getCalendarInfo(String nowuid,String uid);
	public Object updateInfo(Curriculum curriculum);

}	
