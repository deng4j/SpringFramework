package com.dengzhihong.framework.aop.aspect;

import com.dengzhihong.framework.aop.intercept.DMethodInterceptor;
import com.dengzhihong.framework.aop.intercept.DMethodInvocation;

import java.lang.reflect.Method;

public class DAfterThrowingAdviceInterceptor extends DAbstractAspectAdvice implements DMethodInterceptor {

    private String throwingName;

    public DAfterThrowingAdviceInterceptor(Method aspectMethod, Object target) {
        super(aspectMethod, target);
    }

    @Override
    public Object invoke(DMethodInvocation dm) throws Throwable {

        try {
            return dm.proceed();
        } catch (Throwable e) {
           invokeAdviceMethod(dm,null,e.getCause());
           throw e;
        }
    }

    public void setThrowName(String throwName){
        this.throwingName=throwName;
    }
}
