package com.douzone.mysite.exception;

public class GuestbookRepositoryException extends RuntimeException {

	private static final long serialVersionUID = 4275020530575443912L;

	public GuestbookRepositoryException() {
		super("GuestbookRepositoryException Occurs");
	}
	public GuestbookRepositoryException(String error) {
		super(error);
	}
}
