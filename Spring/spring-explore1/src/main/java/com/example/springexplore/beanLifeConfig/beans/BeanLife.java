package com.example.springexplore.beanLifeConfig.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Data
@AllArgsConstructor
public class BeanLife implements InitializingBean , DisposableBean {

    private String name;

    /**
     * 允许 Bean 在容器上设置了所有必需的属性后执行初始化工作。
     * 也可以@Bean的initMethod
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("\033[32m" + "afterPropertiesSet："+this.getClass().getName() + "\033[0m");
    }

    /**
     * 当包含 bean 的容器被销毁时，bean 可以获取回调。
     * 可以使用@Bean的destroyMethod
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("\033[32m" + "destroy："+this.getClass().getName() + "\033[0m");
    }



    /**
     * 该注解与afterPropertiesSet效果一样
     */
    @PostConstruct
    public void postConstruct(){
        System.out.println("\033[32m" + "postConstruct："+this.getClass().getName() + "\033[0m");
    }

    /**
     * 该注解与destroy效果一样
     */
    @PreDestroy
    public void preDestroy(){
        System.out.println("\033[32m" + "preDestroy："+this.getClass().getName() + "\033[0m");
    }
}
