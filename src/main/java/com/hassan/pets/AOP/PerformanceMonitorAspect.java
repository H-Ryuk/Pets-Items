package com.hassan.pets.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitorAspect.class);


    // return type, package-name.class-name.method-name(args)

//    @Around("execution(* com.hassan.pets.Service.*.*(..)) && args(userId)")
//    public Object monitorTime(ProceedingJoinPoint jp, Long userId) throws Throwable {
//
//        Long start = System.currentTimeMillis();
//        Object obj = jp.proceed();
//        Long end = System.currentTimeMillis();
//
//        LOGGER.info("the user id is: " + userId);
//        LOGGER.info("Time taken by : " + jp.getSignature().getName() + " : " + (end - start) + "ms");
//
//        return obj;
//    }


}
