package com.hassan.pets.AOP;

import com.hassan.pets.Exception.TargetNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class TargetNotFoundAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetNotFoundAspect.class);



    @Around("execution(* com.hassan.pets.service.*.*(..))")
    public Object handleTargetNotFound(ProceedingJoinPoint jp) throws Throwable {
        Object result = jp.proceed();

        Long targetId = (Long) jp.getArgs()[0];

        if (result == null){
           LOGGER.error("No Target found with id : " + targetId);
           throw new TargetNotFoundException(targetId);
        }
        return result;
    }





}
