package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Log LOGGER = LogFactory.getLog( GlobalExceptionHandler.class );
	
	@ExceptionHandler(Exception.class)  //모든 exception
	public String handlerException(Model model, Exception e) {
		//1. logging
		StringWriter errors = new StringWriter(); 
		e.printStackTrace(new PrintWriter(errors)); //error stack 내용 자세히 출력(어디서 error발생한 것인지)
		LOGGER.error(errors.toString());
		
		//2. apologize page
		//3. 정상 종료
		model.addAttribute("exception",errors.toString());
		return "error/exception";
	}
}
