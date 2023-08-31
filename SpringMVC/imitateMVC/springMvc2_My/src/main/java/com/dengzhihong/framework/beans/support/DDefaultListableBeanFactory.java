package com.dengzhihong.framework.beans.support;

import com.dengzhihong.framework.beans.config.DBeanDefinition;
import com.dengzhihong.framework.context.support.DAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DDefaultListableBeanFactory extends DAbstractApplicationContext {


     //存储注册信息DBeanDefinition,伪ioc容器
    public final Map<String, DBeanDefinition> beanDefinitonMap=new ConcurrentHashMap<>();
}
