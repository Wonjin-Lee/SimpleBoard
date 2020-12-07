package com.spacewhale.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.spacewhale.board..controller.*Controller.*(..)) " +
            "or execution(* com.spacewhale.board..service.*Impl.*(..)) " +
            "or execution(* com.spacewhale.board..dao.*Mapper.*(..))")
    public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
        String type = "";
        String name = joinPoint.getSignature().getDeclaringTypeName();

        if (name.indexOf("Controller") > -1) {
            type = "Controller \t: ";
        } else if (name.indexOf("Service") > -1) {
            type = "ServiceImpl \t: ";
        } else if (name.indexOf("Mapper") > -1){
            type = "Mapper \t\t: ";
        }

        log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
        return joinPoint.proceed();
    }
}
