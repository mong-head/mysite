package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class CommentInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		long no = Integer.parseInt(request.getParameter("no"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo currentVo = new BoardRepository().findByNo(no);
		
		//update all
		List<BoardVo> list = new BoardRepository().findAll();
		for(BoardVo vo : list) {
			if(currentVo.getOrderNo() < vo.getOrderNo() && currentVo.getGroupNo() == vo.getGroupNo()) {
				new BoardRepository().updateOrderNo(vo);
			}
		}
		
		//insert comment
		BoardVo newVo = new BoardVo();
		newVo.setTitle(title);
		newVo.setContents(content);
		newVo.setHit(0);
		newVo.setGroupNo(currentVo.getGroupNo());
		newVo.setOrderNo(currentVo.getOrderNo()+1);
		newVo.setDepth(currentVo.getDepth()+1);
		newVo.setUserNo(authUser.getNo());
		
		new BoardRepository().Insert(newVo);
		
		MvcUtils.redirect(request.getContextPath()+"/board", request, response);

	}

}
