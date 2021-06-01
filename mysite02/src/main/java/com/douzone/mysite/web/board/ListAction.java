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

		//current page
		String p = request.getParameter("p");
		if(p == null) {
			p = "1";
		}
		int currentPageNo = Integer.parseInt(p);
		
		//list
		List<BoardVo> list = new BoardRepository().findByPage(currentPageNo);

		//page parameters
		int totalPage = (int) Math.ceil(new BoardRepository().CountArticle()/5f);
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
		request.setAttribute("pageInfo", map);
		
		MvcUtils.forward("board/list", request, response);
	}

}
