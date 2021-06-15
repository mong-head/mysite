package com.douzone.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExecutionTimeAspect {
	
	//public 생략, 모든 return, repository,service,controller내의 모든 method에 대해
	@Around("execution(* *..*.*.repository.*.*(..)) || execution(* *..*.*.service.*.*(..)) || execution(* *..*.*.controller.*.*(..))") 
	public Object aroubdAdvice(ProceedingJoinPoint pjp) throws Throwable {
		//before
		StopWatch sw = new StopWatch();
		sw.start();
		
		//code 실행
		Object result = pjp.proceed();
		
		//after
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
		
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String task = className + "." + methodName;
		
		System.out.printf("[Execution Time][%s] %smillisecond \n",task,totalTime);
		
		return result;
	}
}
