package com.dengzhihong.framework.beans.config;

public class DBeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean,String beanName){
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean,String beanName){
        return bean;
    }
}
