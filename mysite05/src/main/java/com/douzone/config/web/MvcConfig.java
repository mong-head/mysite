package com.douzone.config.web;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	// View Resolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		// property 설정
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setExposeContextBeansAsAttributes(true); 
		
		return viewResolver;
	}
	
	
	// Message Converters
	@Bean //string type : utf-8 설정(한글 되도록)
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
		messageConverter.setSupportedMediaTypes(
				Arrays.asList(new MediaType(
						"text","html",Charset.forName("utf-8")
		)));
		
		return messageConverter;
	}
	@Bean //json type 설정
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {

		// json detailing
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.indentOutput(true)  /* 들여쓰기 설정 */
			   .dateFormat(new SimpleDateFormat("yyyy-mm-dd")); // 시간 format 설정
		
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(builder.build());
		messageConverter.setSupportedMediaTypes(
				Arrays.asList(new MediaType(
						"application","json",Charset.forName("utf-8")
		)));
		
		return messageConverter;
	}
	@Override // (message converter) bean 등록
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}

	
	// Default Servelt Handler 등록 작업
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
}
