package com.douzone.mysite.mvc.guestbook;

import com.douzone.mvc.Action;
import com.douzone.mvc.ActionFactory;
import com.douzone.mysite.mvc.main.MainAction;
import com.douzone.mysite.mvc.user.JoinAction;
import com.douzone.mysite.mvc.user.JoinformAction;
import com.douzone.mysite.mvc.user.JoinsuccessAction;

public class GuestbookActionFactory implements ActionFactory{

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("deleteform".equals(actionName)) {
			action = new DeleteformAction();
		} else if("delete".equals(actionName)){
			action = new DeleteAction();
		} else if("insert".equals(actionName)){
			action = new InsertAction();
		} else {
			action = new ListAction();
		}
		return action;
	}

}
