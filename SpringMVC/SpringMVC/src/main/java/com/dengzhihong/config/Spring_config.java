package com.dengzhihong.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration
//设置除Controller包外的扫描,的两种方式
//@ComponentScan({"com.dengzhihong.dao","com.dengzhihong.pojo","com.dengzhihong.service"})

@ComponentScan(value = "com.dengzhihong",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Controller.class))
public class Spring_config {
}
