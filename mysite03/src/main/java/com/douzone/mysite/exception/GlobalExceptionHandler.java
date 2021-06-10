package com.douzone.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.douzone.mysite.dto.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Log LOGGER = LogFactory.getLog( GlobalExceptionHandler.class );
	
	@ExceptionHandler(Exception.class)  //모든 exception
	public String handlerException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws Exception {
		//1. logging
		StringWriter errors = new StringWriter(); 
		e.printStackTrace(new PrintWriter(errors)); //error stack 내용 자세히 출력(어디서 error발생한 것인지)
		LOGGER.error(errors.toString());
		
		//2. 요청 구분
		// if request is the JSON request, Accept of request header : application/json
		// if reques is the HTML request, Accept of request header : text/html
		String accept = request.getHeader("accept");
		
		
		if(accept.matches(".*application/json.*")) {
			//3. json response
			response.setStatus(HttpServletResponse.SC_OK);
			
			JsonResult result = JsonResult.fail(errors.toString());
			String jsonString = new ObjectMapper().writeValueAsString(result);
			
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("UTF-8"));
			os.close();
		
		} else {
			//3. apologize page ( 정상 종료)
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request,response);
		}
		
		//3. 정상 종료
		request.setAttribute("exception",errors.toString());
		return "error/exception";
	}
}
