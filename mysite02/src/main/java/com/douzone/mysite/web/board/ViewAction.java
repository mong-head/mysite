package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long no = Integer.parseInt(request.getParameter("no"));

		BoardVo boardVo = new BoardRepository().findByNo(no);
		
		boardVo.setHit(boardVo.getHit()+1);
		new BoardRepository().updateHit(boardVo);
		
		request.setAttribute("boardVo", boardVo);
		MvcUtils.forward("board/view", request, response);
	}

}
