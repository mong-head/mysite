package com.douzone.mysite.dto;

public class JsonResult {
	private String result; /* "success" or "fail" */
	private Object data;  /* if success, Data Set (else null) */
	private String message; /* if result==fail, message set (else null) */
	
	//밖에서 만들지 못하게 함
	private JsonResult() {
		
	}
	private JsonResult(Object data) {
		result = "success";
		this.data = data;
		this.message = null;
	}
	
	private JsonResult(String message) {
		result = "fail";
		this.data = null;
		this.message = message;
	}
	
	public String getResult() {
		return result;
	}
	public Object getData() {
		return data;
	}
	public String getMessage() {
		return message;
	}

	public static JsonResult success(Object data) {
		return new JsonResult(data);
	}
	
	public static JsonResult fail(String message) {
		return new JsonResult(message);
	}
}
