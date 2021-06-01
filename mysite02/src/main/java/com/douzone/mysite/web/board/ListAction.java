package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//get parameter
		String p = request.getParameter("p");
		if(p == null) {
			p = "1";
		}
		int currentPageNo = Integer.parseInt(p);
		String looking_for = request.getParameter("looking_for");
		String kwd = request.getParameter("kwd");
		
		int size = 0;
		
		if(kwd == null && looking_for == null) {
			kwd = "";
			looking_for = "";
		}
		
		//list
		List<BoardVo> list = null;
		
		if(kwd != "") {
			if ("title_contents".equals(looking_for)) {
				list = new BoardRepository().findByTitleContents(kwd,currentPageNo);
				size = new BoardRepository().CountTitleContents(kwd);
			} else if ("title".equals(looking_for)) {
				list = new BoardRepository().findByTitle(kwd,currentPageNo);
				size = new BoardRepository().CountTitle(kwd);
			} else if ("contents".equals(looking_for)) {
				list = new BoardRepository().findByContents(kwd,currentPageNo);
				size = new BoardRepository().CountContents(kwd);
			} else if ("writer".equals(looking_for)) {
				list = new BoardRepository().findByWriter(kwd,currentPageNo);
				size = new BoardRepository().CountWriter(kwd);
			}
			request.setAttribute("kwd", kwd);
			request.setAttribute("looking_for", looking_for);
		}
		else {
			list = new BoardRepository().findByPage(currentPageNo);
			size = new BoardRepository().CountArticle();
		}
		
		//page parameters
		
		int totalPage = (int) Math.ceil(size/5f);
		int firstPageNo = currentPageNo - (currentPageNo-1)%5; 
		int lastPageNo = firstPageNo + 4;
		int nextPageNo = lastPageNo + 1;
		int prevPageNo = firstPageNo -1;
		if(prevPageNo < 1) {
			prevPageNo = 1;
		}
		if(nextPageNo > totalPage) {
			nextPageNo = totalPage;
		}
		
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("currentPageNo", currentPageNo);
		map.put("totalPage", totalPage);
		map.put("firstPageNo", firstPageNo);
		map.put("lastPageNo", lastPageNo);
		map.put("nextPageNo", nextPageNo);
		map.put("prevPageNo", prevPageNo);
		
		request.setAttribute("list",list);
		request.setAttribute("size",size);
		request.setAttribute("pageInfo", map);
		
		MvcUtils.forward("board/list", request, response);
	}

}
