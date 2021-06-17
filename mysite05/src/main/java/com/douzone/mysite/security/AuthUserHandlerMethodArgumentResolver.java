package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(
			MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		if(supportsParameter(parameter) == false) {
			return WebArgumentResolver.UNRESOLVED; //return null 과 같음
		}
		
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser 없을 경우
		if(authUser == null) {
			return false;
		}
		
		// parameter type이 UserVo가 아닌 경우
		if(parameter.getParameterType().equals(UserVo.class) == false) {
			return false;
		}
		
		return true;
	}

}
