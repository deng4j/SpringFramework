package com.dengzhihong.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
//扫描controller包和SpringMVCSupport
@ComponentScan({"com.dengzhihong.controller","com.dengzhihong.config"})
//开启json数据转换
@EnableWebMvc
public class SpringMVC_Config {
}
