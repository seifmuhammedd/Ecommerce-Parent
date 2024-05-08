package com.ecommerce.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAuthAspect {
    private static final Logger log = LoggerFactory.getLogger(UserAuthAspect.class);
    @Pointcut("execution(* com.ecommerce.Controller.UserController.register(..))")
    public void registerPointcut() {}

    @Pointcut("execution(* com.ecommerce.Controller.UserController.login(..))")
    public void loginPointcut() {}

    @Before("registerPointcut() || loginPointcut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before executing {}", joinPoint.getSignature().getName());
    }

    @Around("registerPointcut() || loginPointcut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Attempting to start around advice for {}", proceedingJoinPoint.getSignature().getName());
        try {
            Object result = proceedingJoinPoint.proceed();
            log.info("Successfully ended around advice for {}", proceedingJoinPoint.getSignature().getName());
            return result;
        } catch (Exception e) {
            log.error("Error in around advice for {}: {}", proceedingJoinPoint.getSignature().getName(), e.getMessage());
            throw e;
        }
    }

    @AfterReturning(pointcut = "registerPointcut() || loginPointcut()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        log.info("After Returning from {}: result = {}", joinPoint.getSignature().getName(), result);
    }


}
