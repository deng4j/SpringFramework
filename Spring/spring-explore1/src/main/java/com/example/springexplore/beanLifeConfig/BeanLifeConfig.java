package com.example.springexplore.beanLifeConfig;

import com.example.springexplore.LifecycleConfig.beans.TestLifecycle;
import com.example.springexplore.beanLifeConfig.beans.BeanLife;
import com.example.springexplore.beanLifeConfig.beans.TestBeanLife;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanLifeConfig {

    @Bean("beanLife")
    public BeanLife createdBeanLife() {
        return new BeanLife("张三");
    }

    @Bean("testBeanLife")
    public TestBeanLife createdTestBeanLife() {
        return new TestBeanLife("testLifecycle");
    }
}