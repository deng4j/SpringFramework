package com.example.springexplore.copeConfig;

import com.example.springexplore.copeConfig.beans.TenantBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TenantScopeConfig {

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new TenantBeanFactoryPostProcessor();
    }

    @Scope(scopeName ="tenant")
    @Bean
    public TenantBean foo() {
        return new TenantBean("foo");
    }

    @Scope(scopeName ="tenant")
    @Bean
    public TenantBean bar() {
        return new TenantBean("bar");
    }
}