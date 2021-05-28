package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.Action;
import com.douzone.mvc.ActionFactory;
import com.douzone.mysite.mvc.main.MainAction;

public class UserActionFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName){
		Action action = null;
		if("joinform".equals(actionName)) {
			action = new JoinformAction();
		} else if("joinsuccess".equals(actionName)){
			action = new JoinsuccessAction();
		} else if("join".equals(actionName)){
			action = new JoinAction();
		} else if("loginform".equals(actionName)){
			action = new LoginformAction();
		} else if("login".equals(actionName)){
			action = new LoginAction();
		} else if("logout".equals(actionName)){
			action = new LogoutAction();
		} else if("updateform".equals(actionName)){
			action = new UpdateformAction();
		} else {
			action = new MainAction();
		}
		return action;
	}
}
