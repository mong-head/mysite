package com.douzone.mysite.web.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.web.main.MainAction;
import com.douzone.web.Action;
import com.douzone.web.ActionFactory;

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
		} else if("update".equals(actionName)){
			action = new UpdateAction();
		} else {
			action = new MainAction();
		}
		return action;
	}
}
