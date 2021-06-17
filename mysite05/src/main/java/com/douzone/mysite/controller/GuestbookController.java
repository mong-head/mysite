package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute("list",list);
		return "guestbook/list";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.addMessage(vo);
		return "redirect:/guestbook"; // index
	}
	
	//deleteform으로 가도록
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no",no);
		return "guestbook/deleteform";
	}
	
	//delete
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") Long no, @RequestParam(value="password", required=true, defaultValue="") String password) {
		guestbookService.deleteMessage(no, password);
		return "redirect:/guestbook";
	}
	
//	//AOP
//	@ExceptionHandler(Exception.class) // Exception.class: 모든 예외가 발생시
//	public String handlerException() {
//		// 1. logging
//
//		// 2. 사과
//		return "error/exception"; // WEB-INF/views/error/exception.jsp 에서 사과 화면띄우도록
//	}
}
