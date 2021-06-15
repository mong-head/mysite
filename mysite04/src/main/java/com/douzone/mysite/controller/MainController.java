package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {
	
	@Autowired
	private ServletContext application;
	
	@Autowired
	private SiteService siteService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo siteVo = siteService.getMainElement();
		model.addAttribute("siteVo",siteVo);
		application.setAttribute("title", siteVo.getTitle());
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg")
	public String msg() {
		return "안녕"; //기본 내장 Simple Http Message Converter는 한글 X
		// spring-servlet 에서 설정해줌
	}
	
	@ResponseBody
	@RequestMapping("/msg2")
	public Object msg2() {
		UserVo vo = new UserVo();
		vo.setNo(10L);
		vo.setEmail("mong@gmail.com");
		return vo;
	}
}
