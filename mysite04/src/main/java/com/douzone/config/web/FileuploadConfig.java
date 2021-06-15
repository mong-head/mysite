package com.douzone.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FileuploadConfig extends WebMvcConfigurerAdapter {
	
	// multipart resolver
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		multipartResolver.setMaxUploadSize(52428800); //최대 업로드 가능 바이트
		multipartResolver.setMaxInMemorySize(52428800); // 임시 파일 생성 전 메모리 저장 최대 바이트
		multipartResolver.setDefaultEncoding("utf-8"); 
		
		return multipartResolver;
	}

	// file upload mapping
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
				.addResourceLocations("file:/upload-mysite/");
	
	}
	
}
