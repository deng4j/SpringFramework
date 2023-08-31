package com.dengzhihong.framework.context;

import com.dengzhihong.framework.annotation.DAutowired;
import com.dengzhihong.framework.annotation.DController;
import com.dengzhihong.framework.annotation.DService;
import com.dengzhihong.framework.aop.DAopProxy;
import com.dengzhihong.framework.aop.DCglibAopProxy;
import com.dengzhihong.framework.aop.DJdkDynamicAopProxy;
import com.dengzhihong.framework.aop.config.DAopConfig;
import com.dengzhihong.framework.aop.support.DAdvisedSupport;
import com.dengzhihong.framework.beans.DBeanWrapper;
import com.dengzhihong.framework.beans.config.DBeanPostProcessor;
import com.dengzhihong.framework.core.DBeanFactory;
import com.dengzhihong.framework.beans.config.DBeanDefinition;
import com.dengzhihong.framework.beans.support.DBeanDefinitionReader;
import com.dengzhihong.framework.beans.support.DDefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DApplicationContext extends DDefaultListableBeanFactory implements DBeanFactory {
    private String[] configLocation;
    private  DBeanDefinitionReader reader;
    //保存单例的ioc容器缓存
    private Map<String,Object> singletonObjects=new ConcurrentHashMap<>();
    //通用ioc容器
    private Map<String,DBeanWrapper> factoryBeanInstanceCache=new ConcurrentHashMap<>();

    public DApplicationContext(String... configLocation){
        this.configLocation=configLocation;

        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void refresh() throws Exception {
        //1.定位配置文件
        reader = new DBeanDefinitionReader(this.configLocation);
        //2.加载配置文件，扫描相关类，封装成BeanDefinition
        List<DBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        //3.注册，把配置信息放在容器中（伪IOC容器）
        doRegisterBeanDefinition(beanDefinitions);
        //4.把不是延迟加载的类提前初始化
        doAutoWired();
    }

    //初始化非延迟加载的bean
    @Override
    public Object getBean(String beanName) {
        DBeanDefinition dBeanDefinition = this.beanDefinitonMap.get(beanName);

        //1.初始化
        Object instance= instantiateBean(beanName,dBeanDefinition);

        //将对象封装成beanWrapper对象
        DBeanWrapper beanWrapper = new DBeanWrapper(instance);

        //创建一个代理策略，jdk或cglib代理。



        DBeanPostProcessor postProcessor = new DBeanPostProcessor();
        //前通知
        postProcessor.postProcessBeforeInitialization(instance,beanName);

        //2.将beanWrapper保存到ioc容器中
        this.factoryBeanInstanceCache.put(beanName,beanWrapper);

        //注入前通知
        postProcessor.postProcessAfterInitialization(instance,beanName);
        //3.注入
        populateBean(beanName,new DBeanDefinition(),beanWrapper);
        return this.factoryBeanInstanceCache.get(beanName).getWarppedInstance();
    }

    //注入
    private void populateBean(String beanName, DBeanDefinition dBeanDefinition, DBeanWrapper dBeanWrapper) {
        Object instance = dBeanWrapper.getWarppedInstance();

        Class<?> wrappedClazz= dBeanWrapper.getWrappedClass();
        if(!(wrappedClazz.isAnnotationPresent(DController.class) || wrappedClazz.isAnnotationPresent(DService.class))){
            return;
        }

        //获得所有Fields
        Field[] fields = wrappedClazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DAutowired.class)){
                continue;
            }

            DAutowired autowired = field.getAnnotation(DAutowired.class);
            String value = autowired.value().trim();
            if ("".equals(value)){
                value=field.getType().getName();
            }
            field.setAccessible(true);
            if (this.factoryBeanInstanceCache.get(value)==null){
                return;
            }
            try {
                Object warppedInstance = this.factoryBeanInstanceCache.get(value).getWarppedInstance();
                field.set(instance,warppedInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        }

    }

    private Object instantiateBean(String beanName, DBeanDefinition dBeanDefinition) {

        //1.拿到对象全类名
        String beanClassName = dBeanDefinition.getBeanClassName();
        Object instance=null;
        try {
            if (this.singletonObjects.containsKey(beanClassName)){
                instance=this.singletonObjects.get(beanClassName);
            }else {
                //2.反射实例化
                Class<?> beanClazz = Class.forName(beanClassName);
                instance = beanClazz.newInstance();

                DAdvisedSupport config = instantionAopConfig(dBeanDefinition);
                config.setTargetClass(beanClazz);
                config.setTarget(instance);

                //符合PointCut的规则的话，闯将代理对象
                if(config.pointCutMatch()) {
                    instance = createProxy(config).getProxy();
                }


                //根据类型
                this.singletonObjects.put(beanClassName,instance);
                //根据beanName注入
                this.singletonObjects.put(beanName,instance);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    private DAopProxy createProxy(DAdvisedSupport config) {
        Class<?> targetClass = config.getTargetClass();
        if (targetClass.getInterfaces().length>0){
           return new DJdkDynamicAopProxy(config);
        }
        return new DCglibAopProxy(config);
    }

    private DAdvisedSupport instantionAopConfig(DBeanDefinition dBeanDefinition) {
        DAopConfig config = new DAopConfig();
        //拿到切点
        String pointCut = this.reader.getContextConfig().getProperty("pointCut");
        String aspectClass = this.reader.getContextConfig().getProperty("aspectClass");
        String aspectBefore = this.reader.getContextConfig().getProperty("aspectBefore");
        String aspectAfter = this.reader.getContextConfig().getProperty("aspectAfter");
        String aspectAfterThrow = this.reader.getContextConfig().getProperty("aspectAfterThrow");
        String aspectAfterThrowingName = this.reader.getContextConfig().getProperty("aspectAfterThrowingName");
        config.setPointCut(pointCut);
        config.setAspectClass(aspectClass);
        config.setAspectBefore(aspectBefore);
        config.setAspectAfter(aspectAfter);
        config.setAspectAfterThrow(aspectAfterThrow);
        config.setAspectAfterThrowingName(aspectAfterThrowingName);
        return new DAdvisedSupport(config);
    }


    /**
     * 只处理非延迟加载
     */
    private void doAutoWired() {
        Set<Map.Entry<String, DBeanDefinition>> entries = super.beanDefinitonMap.entrySet();
        for (Map.Entry<String, DBeanDefinition> entry : entries) {
            String beanName = entry.getKey();
            if (!entry.getValue().isLazyInit()){
                //注意依赖注入时的初始化的先后顺序，应该先初始化再注入
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinition(List<DBeanDefinition> beanDefinitions) throws Exception {

        for (DBeanDefinition beanDefinition : beanDefinitions) {
            if (super.beanDefinitonMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The"+beanDefinition.getFactoryBeanName()+"is exist");
            }
            super.beanDefinitonMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }

    }

    //获取所有的beanName
    public String[] getBeanDefinitionNames(){
        return beanDefinitonMap.keySet().toArray(new String[this.beanDefinitonMap.size()]);
    }

    public int getBeanDefinitionCount(){
        return this.beanDefinitonMap.size();
    }

    public Properties getConfig(){
        return this.reader.getContextConfig();
    }

}
