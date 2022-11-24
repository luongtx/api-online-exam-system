package com.luongtx.oes.aspect;

import com.luongtx.oes.exception.ApplicationUserException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class ExceptionAspect {

    @Around("com.luongtx.oes.aspect.CommonPointcuts.serviceLayerExecution()")
    public void handle(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        } catch (ApplicationUserException e) {
            throw e;
        } catch (Exception e) {
            log.info("Exception at {} with message: {}", joinPoint, e.getMessage());
        } catch (Throwable e) {
            log.info("Exception at ExceptionAspect.handle() with message: {}", e.getMessage());
        }
    }
}
