package com.douzone.mysite.web.board;

import com.douzone.web.Action;
import com.douzone.web.ActionFactory;

public class BoardActionFactory implements ActionFactory{

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("view".equals(actionName)) {
			action = new ViewAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if("write".equals(actionName)) {
			action = new WriteAction();
		} else if("newInsert".equals(actionName)) {
			action = new NewInsertAction();
		} else if("commentInsert".equals(actionName)) {
			action = new CommentInsertAction();
		} else {
			action = new ListAction();
		}
		return action;
	}
}
