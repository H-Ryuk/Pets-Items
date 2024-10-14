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
public class TargetAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetAspect.class);


//    @Around("execution(* com.hassan.pets.service.ItemService.*(..))")
//    public Object handleTargetNotFound(ProceedingJoinPoint jp) throws Throwable {
//        try {
//            Object result = jp.proceed();
//            if (result == null) {
//                Long targetId = (Long) jp.getArgs()[0];  // Assuming the ID is the first argument
//                LOGGER.error("Target not found with id: " + targetId);
//                throw new TargetNotFoundException("test", targetId);
//            }
//            return result; // Return the result if not null
//        } catch (TargetNotFoundException ex) {
//            // Log the exception if necessary
//            LOGGER.error("Caught TargetNotFoundException: " + ex.getMessage());
//            throw ex; // Rethrow the exception to propagate it
//        }
//    }



}
