package com.douzone.mysite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController extends HttpServlet {

	@Autowired
	private BoardService boardService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "")
	public String list(@RequestParam(value = "p", required = true, defaultValue = "1") int currentPageNo,
			@RequestParam(value = "looking_for", required = true, defaultValue = "") String looking_for,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd, Model model) {

		Map<String,Object> result = boardService.getMessageList(currentPageNo, looking_for, kwd);
		
		// page parameters
		int totalPage = (int) Math.ceil((int)result.get("size") / 5f);
		int firstPageNo = currentPageNo - (currentPageNo - 1) % 5;
		int lastPageNo = firstPageNo + 4;
		int nextPageNo = lastPageNo + 1;
		int prevPageNo = firstPageNo - 1;
		if (prevPageNo < 1) {
			prevPageNo = 1;
		}
		if (nextPageNo > totalPage) {
			nextPageNo = totalPage;
		}

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("currentPageNo", currentPageNo);
		map.put("totalPage", totalPage);
		map.put("firstPageNo", firstPageNo);
		map.put("lastPageNo", lastPageNo);
		map.put("nextPageNo", nextPageNo);
		map.put("prevPageNo", prevPageNo);

		model.addAttribute("list", (List<BoardVo>)result.get("list"));
		model.addAttribute("size", (int)result.get("size"));
		model.addAttribute("pageInfo", map);
		model.addAttribute("kwd", kwd);
		model.addAttribute("looking_for", looking_for);
		
		return "board/list";
	}

}
