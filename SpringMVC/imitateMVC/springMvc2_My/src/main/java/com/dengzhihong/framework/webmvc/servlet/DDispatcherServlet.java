package com.dengzhihong.framework.webmvc.servlet;

import com.dengzhihong.framework.annotation.DController;
import com.dengzhihong.framework.annotation.DRequestMapping;
import com.dengzhihong.framework.beans.support.DBeanDefinitionReader;
import com.dengzhihong.framework.context.DApplicationContext;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DDispatcherServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION="contextConfigLocation";
    private DApplicationContext context;
    private List<DHandlerMapping> handlerMappings=new ArrayList<>();
    private Map<DHandlerMapping, DHandlerAdapter> handlerAdapterMap=new ConcurrentHashMap<>();
    private List<DViewResolver> viewResolvers=new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception,Details:\r\n" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "").replaceAll(",\\s", "\r\n"));
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.初始化ApplicationContext
        context=new DApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        //2.初始化SpringMVC九大组件
        initStrategies(context);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1.从request中拿到url，去匹配handlerMapping
        DHandlerMapping handler = getHandler(req);
        if (handler==null){
            DModelAndView modelAndView = new DModelAndView("404");
            processdispatchResult(req,resp,modelAndView);
            return;
        }
        //2.准备调用前的参数
        DHandlerAdapter handlerAdapter=getHandlerAdapter(handler);
        //3.真正的调用方法,返回ModelAndView
        DModelAndView mv = handlerAdapter.handle(req, resp, handler);
        //4.输出
        processdispatchResult(req,resp,mv);
    }

    private void processdispatchResult(HttpServletRequest req, HttpServletResponse resp, DModelAndView mv) throws Exception {
        //把mv变成一个html，OutputStream，json，freeMark，或jsp。
        if (null==mv){
            return;
        }
        //渲染
        if (this.viewResolvers.isEmpty()){
            return;
        }
        for (DViewResolver viewResolver : this.viewResolvers) {
            DView view = viewResolver.resolveViewName(mv.getViewName(), null);
            view.render(mv.getModel(),req,resp);
            return;
        }
    }

    private DHandlerAdapter getHandlerAdapter(DHandlerMapping handler) {
        if (this.handlerMappings.isEmpty()){
            return null;
        }
        DHandlerAdapter handlerAdapter = this.handlerAdapterMap.get(handler);
        if (handlerAdapter.support(handler)){
            return handlerAdapter;
        }
        return null;
    }


    private DHandlerMapping getHandler(HttpServletRequest req) {
        if (handlerMappings.isEmpty()){
            return null;
        }
        String url = req.getRequestURI();
        //处理成相对路径
        String contextPath = req.getContextPath();
        url=url.replaceAll(contextPath,"").replaceAll("/+","/");

        for (DHandlerMapping handler : handlerMappings) {
            //匹配url
            Matcher matcher = handler.getPattern().matcher(url);
            if (!matcher.matches()){
                continue;
            }
            return handler;
        }
        return null;
    }

    /**
     * 初始化策略,九大组件
     * @param context
     */
    protected void initStrategies(DApplicationContext context){
        //多文件上传组件
        iniMultipartResolver(context);
        //初始化本地语言
        initLocaleResolver(context);
        //初始化模板处理器
        initThemeResolver(context);
        //handlerMapping
        initHandlerMappings(context);
        //初始化参数适配器
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);
        //初始化视图转换器
        initViewResolver(context);
        //
        initFlashMapManager(context);
    }

    private void initFlashMapManager(DApplicationContext context) {

    }

    private void initViewResolver(DApplicationContext context) {
        //拿到模板的目录
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String file = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir=new File(file);
        File[] listFiles = templateRootDir.listFiles();
        for (File template : listFiles) {
            this.viewResolvers.add(new DViewResolver(templateRoot));
        }
    }

    private void initRequestToViewNameTranslator(DApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(DApplicationContext context) {

    }

    private void initHandlerAdapters(DApplicationContext context) {
            //将一个request请求变成一个handler，参数都是字符串，自动装配到handler形参
        for (DHandlerMapping handlerMapping : this.handlerMappings) {
            this.handlerAdapterMap.put(handlerMapping,new DHandlerAdapter());
        }
    }

    private void initHandlerMappings(DApplicationContext context) {
        //获得所有beanName
        String[] beanNames = context.getBeanDefinitionNames();
        //
        for (String beanName : beanNames) {
            //获得所有bean
            Object controller = context.getBean(beanName);
            Class<?> clazz = controller.getClass();
            //判断controller
            if (!clazz.isAnnotationPresent(DController.class)){
                continue;
            }

            //保存写在类上的url
            String baseUrl="/";
            if (clazz.isAnnotationPresent(DRequestMapping.class)){
                DRequestMapping requestMapping = clazz.getAnnotation(DRequestMapping.class);
                baseUrl += requestMapping.value();
            }
            //获取所有public的方法
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(DRequestMapping.class)){
                    continue;
                }
                DRequestMapping methodMapping = method.getAnnotation(DRequestMapping.class);
                //如果出现多个斜杆，替换成一个
                String regex=(baseUrl+"/"+methodMapping.value().replaceAll("\\*",".*")).replaceAll("/+","/");
                Pattern pattern=Pattern.compile(regex);
                //放入映射容器
                this.handlerMappings.add(new DHandlerMapping(pattern,controller,method));
                log.info("Mapped"+regex+","+method);
            }

        }
    }

    private void initThemeResolver(DApplicationContext context) {

    }

    private void initLocaleResolver(DApplicationContext context) {

    }

    private void iniMultipartResolver(DApplicationContext context) {

    }

}
