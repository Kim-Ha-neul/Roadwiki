package com.web.blog.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.dto.User;

public interface UserService {
	public Object getInfo(int uid, int loginuser);
	public Object join(User user) throws Exception;
	public Object checkName(String name) throws SQLException;
	public Object checkUser(String email) throws SQLException;
	public Object modify(User user);
	public Object withdraw(String email);
	public Object login(User user);
	public void upload(MultipartFile[] files, Model model, HttpSession session) throws Exception, IOException;
	public Object getName(String uid) throws Exception;
	public Object updatePassword(String email, String password) throws SQLException;
}
