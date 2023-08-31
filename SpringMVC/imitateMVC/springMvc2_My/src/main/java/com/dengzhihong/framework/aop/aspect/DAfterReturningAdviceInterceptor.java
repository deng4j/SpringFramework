package com.dengzhihong.framework.aop.aspect;

import com.dengzhihong.framework.aop.intercept.DMethodInterceptor;
import com.dengzhihong.framework.aop.intercept.DMethodInvocation;

import java.lang.reflect.Method;

public class DAfterReturningAdviceInterceptor extends DAbstractAspectAdvice implements DMethodInterceptor {

    private DJoinPoint joinPoint;

    public DAfterReturningAdviceInterceptor(Method aspectMethod, Object target) {
        super(aspectMethod, target);
    }

    @Override
    public Object invoke(DMethodInvocation dm) throws Throwable {
        Object retVal = dm.proceed();
        this.joinPoint=dm;
        this.afterReturning(retVal,dm.getMethod(),dm.getArguments(),dm.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint,retVal,null);
    }
}
