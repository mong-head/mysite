package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class SearchAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String looking_for = request.getParameter("looking_for");
		String kwd = request.getParameter("kwd");
		
		System.out.println(looking_for);
		System.out.println(kwd);
		
		List<BoardVo> list = null;
		if("title_contents".equals(looking_for)) {
			list = new BoardRepository().findByTitleContents(kwd);
		} else if("title".equals(looking_for)) {
			list = new BoardRepository().findByTitle(kwd);
		} else if("contents".equals(looking_for)) {
			list = new BoardRepository().findByContents(kwd);
		} else if("writer".equals(looking_for)) {
			list = new BoardRepository().findByWriter(kwd);
		}
		
		request.setAttribute("list", list);
		request.setAttribute("kwd", kwd);
		request.setAttribute("looking_for", looking_for);
		MvcUtils.forward("board/list", request, response);
	}

}
