package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.Action;
import com.douzone.mvc.util.MvcUtils;
import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long no = Long.parseLong(request.getParameter("no"));
		String password = request.getParameter("password");
		
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		
		String real_password = new GuestbookRepository().findPassword(vo);
		if(password.equals(real_password)){
			new GuestbookRepository().delete(vo);
			MvcUtils.redirect(request.getContextPath()+"/guestbook",request,response);
		}
		else{
			MvcUtils.redirect(request.getContextPath()+"/guestbook?alert=true",request,response);
		}
	}

}