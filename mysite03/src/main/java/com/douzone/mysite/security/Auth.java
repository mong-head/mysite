package com.douzone.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE}) //controller의 전체class 와 handler개별 모두 annotation가능
//@Retention(RetentionPolicy.CLASS) //retention : 종속기관 , class : run-time때는 X
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	//public String value() default "";
	public String role() default "USER"; //의미를 명확히 하기 위해 value대신 role사용
	public boolean test() default false;
	
}
