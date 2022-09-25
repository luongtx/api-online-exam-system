package com.luongtx.oes.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class PerformanceAspect {
	@Around(CommonJoinPoint.WEB_LAYER_EXECUTION)
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object retVal = joinPoint.proceed();
		long elapseTime = System.currentTimeMillis() - startTime;
		log.info("Time taken by {} : {} ms", joinPoint, elapseTime);
		return retVal;
	}

}
