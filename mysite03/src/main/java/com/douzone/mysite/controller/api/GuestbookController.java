package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller("guestbookControllerApi")
@RequestMapping("/guestbook/api")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@ResponseBody
	@RequestMapping("/list/{no}")
	public JsonResult ex1(@PathVariable Long no) {
		List<GuestbookVo> list = guestbookService.getMessageList(no);
		return JsonResult.success(list);
	}
}
