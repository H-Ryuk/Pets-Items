package com.hassan.pets.AOP;


import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LoggingAspect {


    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);


    // return type, package-name.class-name.method-name(args)

//    @Before("execution(* com.hassan.pets.service.UserService.*(..))")
//    public void logMethodCall(JoinPoint jp){
//        LOGGER.info("Method called " + jp.getSignature().getName());
//    }
//
//
//    @After("execution(* com.hassan.pets.service.UserService.*(..))")
//    public void logMethodExecution(JoinPoint jp){
//        LOGGER.info("Method Execution " + jp.getSignature().getName());
//    }
//
//
//    @AfterReturning("execution(* com.hassan.pets.service.UserService.*(..))")
//    public void logMethodExecutionSuccessfully(JoinPoint jp){
//        LOGGER.info("Method Execution Successfully " + jp.getSignature().getName());
//    }
//
//
//    @AfterThrowing("execution(* com.hassan.pets.service.UserService.*(..))")
//    public void logMethodExecutionFailed(JoinPoint jp){
//        LOGGER.info("Method Execution Failed " + jp.getSignature().getName());
//    }

}
