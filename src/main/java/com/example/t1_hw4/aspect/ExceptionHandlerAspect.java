package com.example.t1_hw4.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Обработчик ошибок методов пакета `com.example.t1_hw4.security`
 */
@Component
@Aspect
@Slf4j
public class ExceptionHandlerAspect {

    @AfterThrowing(pointcut = "within(com.example.t1_hw4.*)", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        log.info("Произошла ошибка при вызове метода: {}", joinPoint.getSignature().toShortString());
        log.info("Ошибка: {}", exception.getMessage());
    }
}
