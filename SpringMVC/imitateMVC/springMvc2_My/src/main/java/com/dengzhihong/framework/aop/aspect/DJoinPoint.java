package com.dengzhihong.framework.aop.aspect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public interface DJoinPoint {
    Object getThis();
    Object[] getArguments();
    Method getMethod();

    void setUserAttribute(String key, Object value);
    Object getUserAttribute(String key);
}
