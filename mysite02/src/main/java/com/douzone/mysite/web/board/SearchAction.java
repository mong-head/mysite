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

public class SearchAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// current page
		String p = request.getParameter("p");
		if (p == null) {
			p = "1";
		}
		int currentPageNo = Integer.parseInt(p);

		//list
		String looking_for = request.getParameter("looking_for");
		String kwd = request.getParameter("kwd");

		List<BoardVo> list = null;
		if ("title_contents".equals(looking_for)) {
			list = new BoardRepository().findByTitleContents(kwd,currentPageNo);
		} else if ("title".equals(looking_for)) {
			list = new BoardRepository().findByTitle(kwd,currentPageNo);
		} else if ("contents".equals(looking_for)) {
			list = new BoardRepository().findByContents(kwd,currentPageNo);
		} else if ("writer".equals(looking_for)) {
			list = new BoardRepository().findByWriter(kwd,currentPageNo);
		}
		
		// page parameters
		int totalPage = (int) Math.ceil(new BoardRepository().CountArticle() / 5f);
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

		request.setAttribute("list", list);
		request.setAttribute("pageInfo", map);
		request.setAttribute("kwd", kwd);
		request.setAttribute("looking_for", looking_for);
		
		//forward
		MvcUtils.forward("board/list", request, response);
	}

}
