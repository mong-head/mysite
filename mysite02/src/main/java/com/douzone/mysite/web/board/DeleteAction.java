package com.douzone.mysite.web.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		long no = Integer.parseInt(request.getParameter("no"));
		String currentPageNo = request.getParameter("p");
		String kwd =  URLEncoder.encode(request.getParameter("kwd"),"UTF-8"); 
		String looking_for = request.getParameter("looking_for");
		
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		
		new BoardRepository().delete(vo);
		
		MvcUtils.redirect(request.getContextPath()+"/board?p=" + currentPageNo + "&kwd=" + kwd + "&looking_for=" + looking_for , request, response);
	}

}
