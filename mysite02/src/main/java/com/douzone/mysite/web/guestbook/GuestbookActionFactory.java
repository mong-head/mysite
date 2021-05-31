package com.douzone.mysite.web.guestbook;

import com.douzone.mysite.web.main.MainAction;
import com.douzone.mysite.web.user.JoinAction;
import com.douzone.mysite.web.user.JoinformAction;
import com.douzone.mysite.web.user.JoinsuccessAction;
import com.douzone.web.Action;
import com.douzone.web.ActionFactory;

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
