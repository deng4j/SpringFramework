package com.dengzhihong.bankTransaction.advice;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {

    public MyAdvice() {
        System.out.println("aop开启");
    }

    @Pointcut("execution(void com..UserService.transfer(..))")
    private void  log(){}


    @Around("log()")
    public void inMoney(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("------------------------------");
        Object[] args = pjp.getArgs();
        pjp.proceed();
        System.out.println(args[0]+"向"+args[1]+"转账："+args[2]);
    }

}
