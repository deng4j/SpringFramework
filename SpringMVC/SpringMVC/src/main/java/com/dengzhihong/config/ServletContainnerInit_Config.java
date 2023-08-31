package com.dengzhihong.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * web容器配置，AbstractDispatcherServletInitializer下有一个AbstractAnnotationConfigDispatcherServletInitializer子类，
 *
 * 启动服务器初始化过程：
 * 1. 服务器启动，执行ServletContainersInitConfig类，初始化web容器
 * 2. 执行createServletApplicationContext方法，创建了WebApplicationContext对象
 * 3. 加载SpringMvcConfig配置类
 * 4. 执行@ComponentScan加载对应的bean
 * 5. 加载UserController，每个@RequestMapping的名称对应一个具体的方法
 * 6. 执行getServletMappings方法，定义所有的请求都通过SpringMVC
 */
public class ServletContainnerInit_Config extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{Spring_config.class};
    }
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMVC_Config.class};
    }
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 解决post乱码
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("utf-8");
        return new Filter[]{filter};
    }


    /* *//**
     * 加载springmvc配置类，生产springmvc容器
     *//*
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        //初始化WebApplication对象
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        //加载指定配置类
        ctx.register(SpringMVC_Config.class);

        return ctx;
    }

    *//**
     * 设置由springmvc控制器处理的请求映射路径
     *//*
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    *//**
     * 加载spring配置类
     *//*
    @Override
    protected WebApplicationContext createRootApplicationContext() {
        //初始化Spring容器对象
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        //加载指定配置类
        ctx.register(Spring_config.class);
        return ctx;
    }*/


}
