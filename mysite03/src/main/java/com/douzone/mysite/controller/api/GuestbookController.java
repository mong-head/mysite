package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@RestController("guestbookControllerApi")
@RequestMapping("/guestbook/api")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@GetMapping("/{no}")
	public JsonResult list(@PathVariable Long no) {
		List<GuestbookVo> list = guestbookService.getMessageList(no);
		return JsonResult.success(list);
	}
	
	@PostMapping("")
	public JsonResult add(@RequestBody GuestbookVo vo) {
		guestbookService.addMessage(vo);
		vo.setPassword(""); // 비번은 없애기
		return JsonResult.success(vo);
	}
	
	@DeleteMapping("/{no}")
	public JsonResult ex3(@PathVariable("no") Long no,  @RequestParam(value="password", required=true, defaultValue="") String password) {
		// delete 작업 (GuestbookService)
		Long data = null;
		boolean result = guestbookService.deleteMessage(no, password);
		if(!result) {
			// 1. delete 안 됨
			System.out.println("false");
			data = -1L;
		} else {
			// 2. delete 됨
			data = no;
		}
		return JsonResult.success(data);
	}
}
