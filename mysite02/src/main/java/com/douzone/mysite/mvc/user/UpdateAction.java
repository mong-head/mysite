package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.Action;
import com.douzone.mvc.util.MvcUtils;
import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long no = Integer.parseInt(request.getParameter("no"));  
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		
		
		UserVo vo = new UserVo();
		vo.setNo(no);
		vo.setName(name);
		vo.setPassword(password);
		vo.setEmail(email);
		vo.setGender(gender);
		
		new UserRepository().update(vo);
		
		UserVo userVo= new UserRepository().findByEmailAndPassword(email,password);
		
		/*인증처리 (session 처리)*/
		//System.out.println(userVo); //확인용
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser",userVo);
		
		//main으로 redirect
		MvcUtils.redirect(request.getContextPath(), request, response);

	}

}
