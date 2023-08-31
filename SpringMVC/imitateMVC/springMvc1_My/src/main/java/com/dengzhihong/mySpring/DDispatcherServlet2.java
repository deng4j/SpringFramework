package com.dengzhihong.mySpring;

import com.dengzhihong.Anno.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DDispatcherServlet2 extends HttpServlet {

    /*保存Properties中的内容*/
    private Properties contextConfig=new Properties();
    /*保存扫描到的所有类名*/
    private List<String> classNames=new ArrayList<>();
   /* 准备IOC容器*/
    private Map<String,Object> ioc=new ConcurrentHashMap<>();
    /*url映射容器*/
    private Map<String,Method> handlerMapping=new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6.调用，运行阶段
        try {
            doDispatcher(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500"+Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //6.调用，运行阶段
        try {
            doDispatcher(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500"+Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String url = req.getRequestURI();
        //处理成相对路径
        String contextPath = req.getContextPath();
        url=url.replaceAll(contextPath,"").replaceAll("/+","/");

        if (!handlerMapping.containsKey(url)){
            resp.getWriter().write("404");
        }
        Method method = handlerMapping.get(url);
        //通过反射拿到method所在class
        String beanName =toLowerFirstCase( method.getDeclaringClass().getSimpleName());

        Map<String, String[]> params  = req.getParameterMap();
        //获取方法的形参列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] paramValues= new Object[parameterTypes.length];
        //一维是参数索引，二维是参数注解
        Annotation[][] annotations = method.getParameterAnnotations();

        for (int i = 0; i < parameterTypes.length; i++) {

            Class<?> parameterType = parameterTypes[i];

            if (parameterType==HttpServletRequest.class){
                paramValues[i]=req;
                continue;
            }else if (parameterType==HttpServletResponse.class){
                paramValues[i]=resp;
                continue;
            }else{

                for (Annotation annotation:annotations[i]){
                    //获取参数上的注解
                    DRequestParam requestParam = (DRequestParam) annotation;
                    if (requestParam instanceof DRequestParam){
                        String[] mapValue = params.get(requestParam.value());
                        if (mapValue!=null){
                                //字符串转化
                                Object value = convert(parameterType,Arrays.toString(mapValue) );
                                paramValues[i]=value;
                        }
                    }
                }

            }

        }
        method.invoke(ioc.get(beanName),paramValues);
    }

    /**
     * url都是字符串
     */
    private Object convert(Class<?> type,String value){
        value = value.replaceAll("\\[|\\]","")
                .replaceAll("\\s",",");

        if (Integer.class==type){
            return Integer.valueOf(value);
        }
        return value;
    }

    /**
     * 初始化阶段
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
            doLoadConfig(config.getInitParameter("contextConfigLocation"));

        // 2.扫描相关类
            doScanner(contextConfig.getProperty("scanPackage"));
        //3.初始化扫描到的类，并放入ioc中
            DoInstance();
        //4.完成依赖注入
            doAutoWired();
        //5.初始化HandlerMapping
            initHandlerMapping();

        System.out.println("D Spring frame is init");
    }

    /**
     * 初始化url和Method的一对一关系
     */
    private void initHandlerMapping() {
        if (ioc.isEmpty()){
            return;
        }
        Set<Map.Entry<String, Object>> entries = ioc.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(DController.class)){
                continue;
            }
            //保存写在类上的url
            String baseUrl="/";
            if (clazz.isAnnotationPresent(DController.class)){
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
                String url=(baseUrl+"/"+methodMapping.value()).replaceAll("/+","/");
                //放入映射容器
                handlerMapping.put(url,method);
                System.out.println("url："+url+":"+method.toString());
            }
        }

    }

    private void doAutoWired() {
        if (ioc.isEmpty()){
            return;
        }

        Set<Map.Entry<String, Object>> entries = ioc.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            //获得一个对象的所有方法
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(DAutowired.class)){
                    DAutowired autowired = field.getAnnotation(DAutowired.class);
                    String beanName = autowired.value().trim();
                    //如果注解默认值为空，则根据类型注入
                    if ("".equals(beanName)){
                        beanName=field.getType().getName();
                    }
                    //赋值
                    try {
                        field.setAccessible(true);
                        field.set(entry.getValue(),ioc.get(beanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }

    private void DoInstance() {
        //初始化,为DI做准备
        if (classNames.isEmpty()){return;}

        for (String className : classNames) {
            try {

                Class<?> clazz = Class.forName(className);

                //加了注解的类需要初始化,这里只举例几个注解
                if (clazz.isAnnotationPresent(DController.class)){
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    //类名首字母小写
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName,instance);
                }else if (clazz.isAnnotationPresent(DService.class)){
                    DService service = clazz.getAnnotation(DService.class);
                    //1.自定义beanName
                    // 获得该注解的默认值
                    String beanName = service.value();

                    //2.默认类名首字母小写
                    if ("".equals(beanName.trim())){
                         beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    //3.根据类型自动注入,扫描该类的所有接口
                    for (Class<?> i:clazz.getInterfaces()){
                        //防止使用相同key名
                        if (ioc.containsKey(i.getName())){
                            throw  new Exception(i.getName()+"该类实例已存在，请更换名字");
                        }
                        ioc.put(i.getName(),instance);
                    }
                }else {
                    continue;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //大小写字母ASCII码相差32
        if (chars[0]>=65&&chars[0]<=90){
            //说明是一个大写字母
            chars[0] +=32;
        }
        return String.valueOf(chars);
    }

    //扫描相关类
    private void doScanner(String scanPackage) {
        //转化成文件路径:classpath，注意双亲委派机制
        URL url = this.getClass().getClassLoader().getResource(scanPackage.replaceAll("\\.","/"));
        File classpath = new File(url.getFile());
        File[] files = classpath.listFiles();
        for (File file : files) {
            if (file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else {
                if (!file.getName().endsWith(".class")){continue;}
                //得到完整的类名
                String className=(scanPackage+"."+file.getName().replace(".class",""));
                //保存到容器中
                classNames.add(className);
            }
        }
    }

    /**
     * 加载配置文件
     * @param contextConfigLocation
     */
    private void doLoadConfig(String contextConfigLocation) {
        InputStream fis=null;
        //直接从类路径下找到Spring主配置文件所在路径
        //将properties文件放到Properties对象中
        fis=this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
