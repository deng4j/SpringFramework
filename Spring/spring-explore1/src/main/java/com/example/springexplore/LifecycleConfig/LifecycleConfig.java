package com.example.springexplore.LifecycleConfig;

import com.example.springexplore.LifecycleConfig.beans.MyLifecycle;
import com.example.springexplore.LifecycleConfig.beans.TestLifecycle;
import com.example.springexplore.beanLifeConfig.beans.BeanLife;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifecycleConfig {

    @Bean("myLifecycle")
    public MyLifecycle createdMyLifecycle() {
        return new MyLifecycle("myLifecycle",true);
    }

    @Bean("testLifecycle")
    public TestLifecycle createdTestLifecycle() {
        return new TestLifecycle("testLifecycle");
    }
}