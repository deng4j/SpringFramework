package com.dengzhihong.framework.core;

/**
 * 单例工厂的顶层设计
 */
public interface DBeanFactory {

    /**
     *根据beanName从Ioc容器中获取实例Bean
     */
    Object getBean(String beanName);
}
