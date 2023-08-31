package com.dengzhihong.framework.beans.support;

import com.dengzhihong.framework.beans.config.DBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 读取配置文件的类
 */
public class DBeanDefinitionReader {
    private Properties contextConfig=new Properties();
    private final String SCAN_PACKAGE="scanPackage";

    private List<String> registerBeanClasses=new ArrayList<>();

    public DBeanDefinitionReader(String... location) {
        //通过定位Properties文件，加载内容
        InputStream fis=this.getClass().getClassLoader().getResourceAsStream(location[0].replace("classpath:",""));
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
        //扫描包
        doScanner(contextConfig.getProperty(SCAN_PACKAGE));
    }

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
                registerBeanClasses.add(className);
            }
        }
    }

    public Properties getContextConfig() {
        return contextConfig;
    }

    /**
     * 将配置文件中所有扫描到的信息，转化为BeanDefinition对象，便于IOC操作
     */
    public List<DBeanDefinition> loadBeanDefinitions(){
        List<DBeanDefinition> result=new ArrayList<>();
        try {
            for (String className : registerBeanClasses) {
                Class<?> beanClass = Class.forName(className);
                //如果是一个接口，是不能实例化的
                //用它实现类来实例化
                if(beanClass.isInterface()) { continue; }

                //beanName有三种情况:
                //1、默认是类名首字母小写
                //2、自定义名字
                //3、接口注入
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));
                //result.add(doCreateBeanDefinition(beanClass.getName(),beanClass.getName()));

                Class<?> [] interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                    //如果是多个实现类，只能覆盖
                    result.add(doCreateBeanDefinition(i.getName(),beanClass.getName()));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private DBeanDefinition doCreateBeanDefinition(String factoryBeanName,String beanClassName){
        DBeanDefinition beanDefinition = new DBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
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
}
