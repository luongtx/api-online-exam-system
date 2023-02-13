package com.luongtx.oes.config;

import com.luongtx.oes.aspect.ExceptionAspect;
import com.luongtx.oes.aspect.LoggingAspect;
import com.luongtx.oes.aspect.PerformanceAspect;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AspectJConfig {

    @Value("${aspectj.enabled}")
    boolean enableAspectJ;

    @Bean
    AspectJProxyFactory aspectJProxyFactory() {
        return new AspectJProxyFactory();
    }

    @Bean
    LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    PerformanceAspect performanceAspect() {
        return new PerformanceAspect();
    }

    @Bean
    ExceptionAspect exceptionAspect() {
        return new ExceptionAspect();
    }

    <T> T proxyObject(T object) {
        if (!enableAspectJ) {
            return object;
        }
        AspectJProxyFactory factory = aspectJProxyFactory();
        factory.setTarget(object);
        factory.addAspect(loggingAspect());
        factory.addAspect(exceptionAspect());
        factory.addAspect(performanceAspect());
        return factory.getProxy();
    }

}
