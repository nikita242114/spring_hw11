package ru.gb.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggableAspect {
    // Для обработки методов с указанной аннотацией
    @Pointcut("@annotation(ru.gb.aspects.Timer)")
    public void methodsAnnotatedWith() {
    }

    // Для обработки классов с указанной аннотацией
    @Pointcut("within(@ru.gb.aspects.Timer *)")
    public void classAnnotatedWith() {
    }

    @Around("methodsAnnotatedWith() || classAnnotatedWith()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint)throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object result = null;
        Long timeElapsed = 0L;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex){
            log.error("Exception caught: {}", ex.getMessage());
        } finally {
            Long endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            log.info("{} - {} #({} mseconds)", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), timeElapsed / 1000.0);
        }
        return result;
    }


}