package com.web.blog.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.lang.Assert;

@RestController
@RequestMapping("/upload")
public class UploadController {
	
	@GetMapping("/pic")
	public Object getRes(HttpServletRequest request) {
		System.out.println();
		return "good";
	}
	
	@PostMapping("/pic2")
	public Object getImg(@RequestPart(value="file", required=true) MultipartFile file) throws Exception {
//		System.out.println("in");
//		System.out.println(file);
//		System.out.println(file.toString());
		
//		System.out.println(dto.toString());
		
		// parent directory를 찾는다.
		Path directory = Paths.get(".\\uploads\\").toAbsolutePath().normalize();

		// directory 해당 경로까지 디렉토리를 모두 만든다.
		Files.createDirectories(directory);
	    
		// 파일명을 바르게 수정한다.
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		// 파일명에 '..' 문자가 들어 있다면 오류를 발생하고 아니라면 진행(해킹및 오류방지)
		Assert.state(!fileName.contains(".."), "Name of file cannot contain '..'");
		// 파일을 저장할 경로를 Path 객체로 받는다.
		Path targetPath = directory.resolve(fileName).normalize();

		// 파일이 이미 존재하는지 확인하여 존재한다면 오류를 발생하고 없다면 저장한다.
		Assert.state(!Files.exists(targetPath), fileName + " File alerdy exists.");
		file.transferTo(targetPath);
		
		
		return "good";
	}

}