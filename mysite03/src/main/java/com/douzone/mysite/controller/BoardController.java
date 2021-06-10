package com.douzone.mysite.controller;

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
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

//@Auth
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
		
		model.addAttribute("list", (List<BoardVo>)result.get("list"));
		model.addAttribute("size", (int)result.get("size"));
		model.addAttribute("pageInfo", (Map<String,Integer>)result.get("pageInfo"));
		model.addAttribute("kwd", kwd);
		model.addAttribute("looking_for", looking_for);
		
		return "board/list";
	}
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(@RequestParam(value="no", required=true, defaultValue="") long no, Model model) {
		BoardVo boardVo = boardService.getArticle(no);
		model.addAttribute("boardVo",boardVo);
		return "board/view";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(
			@RequestParam(value="no", required=true, defaultValue="") long no,
			@RequestParam(value="p", required=true, defaultValue="") long currentPageNo,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="looking_for", required=true, defaultValue="") String looking_for,
			Model model
			) {
		
		boardService.deleteArticle(no);
		
		model.addAttribute("p",currentPageNo);
		model.addAttribute("kwd",kwd);
		model.addAttribute("looking_for",looking_for);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping("/write")
	public String write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(
			@AuthUser UserVo authUser,
			@RequestParam(value="no",required=false) Long no,
			BoardVo boardVo) {
		
		boardVo.setUserNo(authUser.getNo());
		boardService.write(no, boardVo);
		
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@RequestParam(value="no",required=false) Long no, Model model) {
		BoardVo boardVo = boardService.getArticle(no);
		model.addAttribute("no", no);
		model.addAttribute("boardVo",boardVo);
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@RequestParam(value="no",required=false) Long no,BoardVo boardVo) {
		boardService.update(boardVo);
		return "redirect:/board";
	}
	
	
}
