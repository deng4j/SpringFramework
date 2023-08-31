package com.dengzhihong.bankTransaction.config;


import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//声明是配置注解
@Configuration
//扫描包下的注解
@ComponentScan({"com.dengzhihong.bankTransaction"})
//声明properties数据源
@PropertySource({"classpath:jdbc.properties"})

//事务注解驱动
@EnableTransactionManagement
//开启AOP注解
@EnableAspectJAutoProxy
@Import({Jdbc_Config.class,Mybatis_Config.class})
public class Spring_Config {
}
