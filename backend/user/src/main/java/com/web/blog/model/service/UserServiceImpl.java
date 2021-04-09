package com.web.blog.model.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.dto.FileInfoDto;
import com.web.blog.model.dto.User;
import com.web.blog.model.repo.UserRepo;
import com.web.blog.util.SecurityUtil;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    UserRepo userRepo;

    @Autowired
    LoginService loginServ;

    @Autowired
    ServletContext servletContext;

    @Override
    public Object getInfo(int uid, int loginuser) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            User tmp = userRepo.selectUid(uid);
            if (tmp == null) {
                result.put("msg", "fail");
                return result;
            }
            logger.info("getInfo Start");
            result.put("uid", tmp.getUid());
            result.put("email", tmp.getEmail());
            result.put("name", tmp.getName());
            result.put("createDate", tmp.getCreateDate());
            result.put("address", tmp.getAddress());
            result.put("oneline", tmp.getOneline());
            result.put("major", tmp.getMajor());
            result.put("msg", "success");
            result.put("keywords", userRepo.selectkeyword(tmp.getUid()));
            result.put("keywordtexts", userRepo.selectkeywordtext(tmp.getUid()));
            if (uid == loginuser) {
                result.put("isEqual", true);
            } else {
                result.put("isEqual", false);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("sql error");
        }
    }

    @Transactional
    @Override
    public Object join(User user) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	user.setPassword(SecurityUtil.encryptSHA256(user.getPassword()));
            if (userRepo.insert(user) == 1) {
                int uid = userRepo.select(user.getEmail()).getUid();
                for (int i = 0; i < user.getKeyword().length; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("uid", Integer.toString(uid));
                    map.put("kwid", user.getKeyword()[i]);
                    map.put("priority", Integer.toString(i + 1));
                    userRepo.insertkeyword(map);
                }
                result.put("msg", "success");
            } else {
                result.put("msg", "join fail");
            }

        } catch (Exception e) {
            logger.error("user join error");
            throw e;
        }
        return result;
    }
    
    @Override
    public Object checkName(String name) throws SQLException {
        User user = userRepo.canName(name);
        if (user == null) {
            logger.error("existent user");
        }
        return user;
    }
    
    @Override
    public Object checkUser(String email) throws SQLException {
        User user = userRepo.select(email);
        if (user == null) {
            logger.error("existent user");
        }
        return user;
    }

    @Transactional
    @Override
    public Object modify(User user) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            if (userRepo.update(user) == 1) {
                logger.info("user update end");
                userRepo.deletekeyword(user.getUid());
                for (int i = 0; i < user.getKeyword().length; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("uid", Integer.toString(user.getUid()));
                    map.put("kwid", user.getKeyword()[i]);
                    map.put("priority", Integer.toString(i + 1));
                    userRepo.insertkeyword(map);
                }
                result.put("msg", "success");
            }
            else
                result.put("msg", "Non-existent user");
            return result;
        } catch (Exception e) {
            throw new RuntimeException("sql error");
        }
    }

    @Transactional
    @Override
    public Object withdraw(String email) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            if (userRepo.delete(email) == 1)
                result.put("msg", "success");
            else
                result.put("msg", "Non-existent user");
            return result;
        } catch (Exception e) {
            throw new RuntimeException("sql error");
        }
    }

    @Override
    public Object login(User user) {
        try {
            User cur = userRepo.select(user.getEmail());
            if (!SecurityUtil.encryptSHA256(user.getPassword()).equals(cur.getPassword())) {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("msg", "login fail");
                logger.error("error msg");
                return result;
            }
            Map<String, Object> result = new HashMap<String, Object>();
            String token = loginServ.generate(cur);
            result.put("authorizationToken", token);
            result.put("uid", cur.getUid());
            result.put("email", cur.getEmail());
            result.put("name", cur.getName());
            result.put("createDate", cur.getCreateDate());
            result.put("address", cur.getAddress());
            result.put("oneline", cur.getOneline());
            result.put("major", cur.getMajor());
            result.put("msg", "SUCCESS");
            return result;
        } catch (Exception e) {
            throw new RuntimeException("login error");
        }
    }

    @Override
    public Object getName(String uid) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String name = userRepo.getName(Integer.parseInt(uid));
            result.put("name", name);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return result;
    }

    @Transactional
    @Override
    public void upload(MultipartFile[] files, Model model, HttpSession session) throws Exception, IOException {
        String realPath = servletContext.getRealPath("/upload");
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = File.separator + today;
        File folder = new File(saveFolder);
        if (!folder.exists())
            folder.mkdirs();
        List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
        for (MultipartFile mfile : files) {
            FileInfoDto fileInfoDto = new FileInfoDto();
            String originalFileName = mfile.getOriginalFilename();
            if (!originalFileName.isEmpty()) {
                String saveFileName = UUID.randomUUID().toString()
                        + originalFileName.substring(originalFileName.lastIndexOf('.'));
                fileInfoDto.setSaveFolder(today);
                fileInfoDto.setOriginFile(originalFileName);
                fileInfoDto.setSaveFile(saveFileName);
                mfile.transferTo(new File(folder, saveFileName));
            }
            fileInfos.add(fileInfoDto);
        }
    }
    
    @Override
    public Object updatePassword(String email, String password) throws SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("email", email);
            map.put("password", SecurityUtil.encryptSHA256(password));
            userRepo.updatepassword(map);
            result.put("msg", "success");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return result;
    }
}

