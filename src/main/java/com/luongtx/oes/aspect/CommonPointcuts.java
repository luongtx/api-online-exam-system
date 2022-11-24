package com.luongtx.oes.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcuts {

	@Pointcut("execution(* com.luongtx.oes.web.*.*(..))")
	public void webLayerExecution() {}

    @Pointcut("execution(* com.luongtx.oes.service..*(..))")
    public void serviceLayerExecution() {}

}
