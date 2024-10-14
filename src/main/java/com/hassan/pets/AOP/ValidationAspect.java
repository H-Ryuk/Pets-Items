package com.hassan.pets.AOP;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
@Aspect
public class ValidationAspect {


    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);


    // return type, package-name.class-name.method-name(args)
//    @Around("execution(* com.hassan.pets.service.UserService.*(..)) && args(userId)")
//    public  Object validateAndUpdate(ProceedingJoinPoint jp, Long userId) throws Throwable {
//        if (userId < 0){
//            LOGGER.warn("userId is negative");
//            userId = -userId;
//            LOGGER.info("the updated userId is : " + userId);
//        }
//        return jp.proceed(new Object[]{userId});
//    }




}
