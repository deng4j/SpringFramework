package com.dengzhihong.framework.aop.aspect;

import com.dengzhihong.framework.aop.intercept.DMethodInterceptor;
import com.dengzhihong.framework.aop.intercept.DMethodInvocation;

import java.lang.reflect.Method;

public class DMethodBeforeAdviceInterceptor extends DAbstractAspectAdvice implements DMethodInterceptor {
    private DJoinPoint joinPoint;

    public DMethodBeforeAdviceInterceptor(Method aspectMethod, Object target) {
        super(aspectMethod, target);
    }

    private void before(Method method,Object[] args,Object target) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint,null,null);
    }

    @Override
    public Object invoke(DMethodInvocation dm) throws Throwable {
        this.joinPoint=dm;
        before(dm.getMethod(),dm.getArguments(),dm.getThis());
        return dm.proceed();
    }


}
