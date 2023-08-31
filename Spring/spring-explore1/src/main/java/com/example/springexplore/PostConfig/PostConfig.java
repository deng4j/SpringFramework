package com.example.springexplore.PostConfig;

import com.example.springexplore.PostConfig.beans.MyBeanPostProcessor;
import com.example.springexplore.PostConfig.beans.TestPostBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostConfig {

    @Bean("myBeanPostProcessor")
    public MyBeanPostProcessor createdAwareBean() {
        return new MyBeanPostProcessor("xxxxxxxx");
    }

    @Bean("testPostBean")
    public TestPostBean createdTestPostBean() {
        return new TestPostBean("testPostBean");
    }
}