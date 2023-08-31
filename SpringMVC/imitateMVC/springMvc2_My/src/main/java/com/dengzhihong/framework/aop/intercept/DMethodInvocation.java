package com.dengzhihong.framework.aop.intercept;

import com.dengzhihong.framework.aop.aspect.DJoinPoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DMethodInvocation implements DJoinPoint {

    private  Object[] arguments;
    private  Method method;
    private  Class<?> targetClass;
    private  Object target;
    private  Object proxy;
    private  List<Object> interceptorsAndDynamicMethodMatchers;

    //定义一个索引，从-1开始记录当前拦截器执行的位置。
    private int currentInterceptorIndex=-1;

    private Map<String,Object> userAttributes;

    public DMethodInvocation(Object proxy, Object target,
                             Method method, Object[] arguments,
                             Class<?> targetClass,
                             List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    /**
     * 执行拦截器链的方法
     */
    public Object proceed() throws Throwable {
        //如果执行完了，则执行joinPoint
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(this.target,this.arguments);
        } else {
            Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
            if (interceptorOrInterceptionAdvice instanceof DMethodInterceptor) {
                DMethodInterceptor dm = (DMethodInterceptor)interceptorOrInterceptionAdvice;
                return dm.invoke(this);
            } else {
                //动态匹配失败，忽略当前Interceptor，调用下一个
                return proceed();
            }
        }
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public void setUserAttribute(String key, Object value) {
        if (value != null) {
            if (this.userAttributes == null) {
                this.userAttributes = new HashMap<String,Object>();
            }
            this.userAttributes.put(key, value);
        }
        else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }
    }

    @Override
    public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);
    }

}
