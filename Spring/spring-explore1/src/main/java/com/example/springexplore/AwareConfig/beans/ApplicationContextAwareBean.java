package com.example.springexplore.AwareConfig.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Data
@AllArgsConstructor
public class ApplicationContextAwareBean implements ApplicationContextAware , BeanNameAware {

    private String name;

    /**
     * 获得对该ApplicationContext的引用。
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("\033[32m" + "applicationContext：" +applicationContext.getId()+ "\033[0m");
    }

    /**
     * 获得bean的名称
     */
    @Override
    public void setBeanName(String s) {
        System.out.println("\033[32m" + "setBeanName：" +s+ "\033[0m");
    }
}
