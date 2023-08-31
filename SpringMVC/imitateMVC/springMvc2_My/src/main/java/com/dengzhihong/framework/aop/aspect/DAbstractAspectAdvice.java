package com.dengzhihong.framework.aop.aspect;

import java.lang.reflect.Method;

public abstract class DAbstractAspectAdvice implements DAdvice{
    private Method aspectMethod;
    private Object target;


    public DAbstractAspectAdvice(Method aspectMethod, Object target) {
        this.aspectMethod = aspectMethod;
        this.target = target;
    }


    protected Object invokeAdviceMethod(DJoinPoint joinPoint, Object returnValue, Throwable tx) throws Throwable{
        Class<?>[] parameterTypes = this.aspectMethod.getParameterTypes();
        if (null==parameterTypes||parameterTypes.length==0){
            return this.aspectMethod.invoke(target);
        }else {
            Object[] args = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i]==DJoinPoint.class){
                    args[i]=joinPoint;
                }else if (parameterTypes[i]==Throwable.class){
                    args[i]=tx;
                }else if (parameterTypes[i]==Object.class){
                    args[i]=returnValue;
                }
            }
            return this.aspectMethod.invoke(target,args);
        }
    }
}
