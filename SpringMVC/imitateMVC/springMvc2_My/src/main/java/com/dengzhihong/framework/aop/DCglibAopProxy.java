package com.dengzhihong.framework.aop;

import com.dengzhihong.framework.aop.support.DAdvisedSupport;

public class DCglibAopProxy implements DAopProxy{
    public DCglibAopProxy(DAdvisedSupport config) {

    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
