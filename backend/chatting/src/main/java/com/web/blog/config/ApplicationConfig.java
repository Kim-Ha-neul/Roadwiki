package com.web.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("*")
		.allowedMethods("*")
		.allowedHeaders("*")
		.exposedHeaders("auth-token");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//      registry.addInterceptor(loginItc)
//      .addPathPatterns("/user/**")
//      .excludePathPatterns(Arrays.asList("/user/login", "/user/join"));
//		registry.addInterceptor(loginItc)
//			.addPathPatterns("/user/login")
//			//.excludePathPatterns(Arrays.asList("/user/login", "/user/join", "/user/**"));
//			.excludePathPatterns(Arrays.asList("/user/**"));
	}
	
	
}
