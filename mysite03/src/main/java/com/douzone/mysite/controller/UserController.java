package com.douzone.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//joinform
	@RequestMapping("/join")
	public String join() {
		return "/WEB-INF/views/user/joinform.jsp";
	}
	
	//join
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@RequestParam(value="no", required=true, defaultValue="") Long no,
			@RequestParam(value="password", required=true, defaultValue="") String password,
			@RequestParam(value="email", required=true, defaultValue="") String email, Model model) {
		
		return "redirect:/";
	}
}
