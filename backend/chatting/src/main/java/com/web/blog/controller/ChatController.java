package com.web.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.web.blog.model.dto.ChatMsg;
import com.web.blog.model.repo.ChatRoomRepo;
import com.web.blog.model.service.PublisherService;
import com.web.blog.model.service.SubscriberService;

@Controller
public class ChatController {
	
	@Autowired
	PublisherService publisherServ;
	
	@Autowired
	SubscriberService subscriberServ;
	
	@Autowired
	ChatRoomRepo chatRoomRepo;
	
	@MessageMapping("/chat/message")
	public void message(ChatMsg message) {
		if(ChatMsg.MsgType.JOIN.equals(message.getType())) {
			chatRoomRepo.enterChatRoom(message.getRoomid());
			message.setMsg(message.getSender() + "님이 입장하셨습니다.");
			message.setSender("[공지]");
		}
		publisherServ.publish(chatRoomRepo.getTopic(message.getRoomid()), message);
	}
}
