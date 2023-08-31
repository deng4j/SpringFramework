package com.example.springexplore.copeConfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 自定义BeanFactoryPostProcessor并使用ConfigurableListableBeanFactory注册我们的自定义范围
 */
public class TenantBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        /**
         * 为了使Spring容器知道您的新作用域，您需要通过ConfigurableBeanFactory实例上的registerScope方法对其进行注册。
         */
        factory.registerScope("tenant", new TenantScope());
    }
}
