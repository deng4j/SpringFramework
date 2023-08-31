package com.dengzhihong.AOP.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan({"com.dengzhihong.AOP"})
//开启注解aop功能
@EnableAspectJAutoProxy

public class SpringConfig {
}
