package com.dengzhihong.framework.context;

/**
 * 通过解耦的方式获得IOC容器的顶层设计，后面通过一个监听器扫描所有的类，
 * 只要实现了此接口，将自动调用setApplicationContext()方法，将IOC容器
 * 注入到目标类中。
 */
public interface DApplicationContextAware {
    void setApplicationContext(DApplicationContext applicationContext);
}
