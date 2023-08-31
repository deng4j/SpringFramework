package com.dengzhihong.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

//声明是配置注解
@Configuration
//扫描包下的注解
@ComponentScan({"com.dengzhihong"})
//声明properties数据源
@PropertySource({"classpath:jdbc.properties"})
@Import({Jdbc_Config.class, Mybatis_Config.class}) //从其他配置中获取bean
public class Spring_Config {
}
