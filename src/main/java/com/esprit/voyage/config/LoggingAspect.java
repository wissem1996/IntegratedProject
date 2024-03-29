package com.esprit.voyage.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Before("execution(* com.esprit.voyage.service.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        logger.info("In method " + name);
    }

    @After("execution(* com.esprit.voyage.service.*.*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        logger.info("Out of " + name);
    }
}
