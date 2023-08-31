package com.dengzhihong.framework.aop;

public interface DAopProxy {

    Object getProxy();

    Object getProxy( ClassLoader classLoader);
}
