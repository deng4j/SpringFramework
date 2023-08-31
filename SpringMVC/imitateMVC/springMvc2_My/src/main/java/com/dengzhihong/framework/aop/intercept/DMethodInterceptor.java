package com.dengzhihong.framework.aop.intercept;

public interface DMethodInterceptor {
    Object invoke( DMethodInvocation invocation) throws Throwable;
}
