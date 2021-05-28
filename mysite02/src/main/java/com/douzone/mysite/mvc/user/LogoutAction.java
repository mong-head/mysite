package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.Action;
import com.douzone.mvc.util.MvcUtils;

public class LogoutAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//로그인하지도 않은 상태에서 로그아웃하려고 하는 경우
		if(session == null) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		
		/* logout */
		session.removeAttribute("authUser"); //userVo 없애고
		session.invalidate(); //HttpSession도 없애고
		
		MvcUtils.redirect(request.getContextPath(), request, response);
	}

}
