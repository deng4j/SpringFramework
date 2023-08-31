package com.dengzhihong.framework.aop;

import com.dengzhihong.framework.aop.intercept.DMethodInvocation;
import com.dengzhihong.framework.aop.support.DAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class DJdkDynamicAopProxy implements DAopProxy, InvocationHandler {

    private DAdvisedSupport advised;

    public DJdkDynamicAopProxy(DAdvisedSupport config){
        this.advised=config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatches= this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());
        DMethodInvocation invocation=new DMethodInvocation(proxy,this.advised.getTarget(),method,args,this.advised.getTargetClass(),interceptorsAndDynamicMethodMatches);

        return invocation.proceed();
    }
}
