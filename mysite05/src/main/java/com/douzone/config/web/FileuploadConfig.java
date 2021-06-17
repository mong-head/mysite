package com.douzone.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:com/douzone/mysite/config/web/fileupload.properties")
public class FileuploadConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private Environment env; 
	
	// multipart resolver
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		multipartResolver.setMaxUploadSize(env.getProperty("fileupload.maxUploadSize",Long.class)); //최대 업로드 가능 바이트
		multipartResolver.setMaxInMemorySize(env.getProperty("fileupload.maxInMemorySize",Integer.class)); // 임시 파일 생성 전 메모리 저장 최대 바이트
		multipartResolver.setDefaultEncoding(env.getProperty("fileupload.defaultEncoding")); 
		
		return multipartResolver;
	}

	// file upload mapping
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(env.getProperty("fileupload.resourceMapping"))
				.addResourceLocations("file:" + env.getProperty("fileupload.uploadLocation"));
	
	}
	
}
