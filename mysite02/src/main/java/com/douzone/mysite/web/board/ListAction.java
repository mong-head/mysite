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

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		계산해야함		
//		totalPage = ceil(10/3) : 4 page
//		firstPageNo = 3;
//		lastPageNo = 7;
//		nextPageNo = 8;
//		prevPageNo = 2;
//		currentPageNo = 4;
		
//		map = new ~~
//		map.put("lastPageNo", lastPageNo)등등
		
//		request.setAttribute("PageInfo",map);
		
		// 1 process request
		List<BoardVo> list = new BoardRepository().findAll();
							
		// 2 request 범위에 data 저장
		request.setAttribute("list",list);
		
		int	numList = list.size();
		int totalPage = (int) Math.ceil(numList/5f);
		//int currentPageNo = 
		
		MvcUtils.forward("board/list", request, response);
	}

}
