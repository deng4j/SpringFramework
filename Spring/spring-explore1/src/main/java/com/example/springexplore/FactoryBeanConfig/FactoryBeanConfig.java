package com.example.springexplore.FactoryBeanConfig;

import com.example.springexplore.FactoryBeanConfig.beans.UserFactoryBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {UserFactoryBean.class})
public class FactoryBeanConfig {}
