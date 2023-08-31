package com.example.springexplore.PostConfig.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;

/**
 * BeanPostProcessor该接口我们也叫后置处理器，也叫AOP的桥梁，作用是在所有Bean对象在实例化和依赖注入完毕后，
 * 在显示调用初始化方法的前后添加我们自己的逻辑。注意是Bean实例化完毕后及依赖注入完成后触发的。
 *
 * BeanPostProcessor接口定义了回调方法，您可以实施这些回调方法以提供自己的(或覆盖容器的默认值)实例化逻辑，依赖关系解析逻辑等。
 * 如果您想在 Spring 容器完成实例化，配置和初始化 bean 之后实现一些自定义逻辑，则可以插入一个或多个BeanPostProcessor实现。
 *
 * 多个BeanPostProcessor实现，可以通过设置Ordered接口属性来控制这些BeanPostProcessor的执行顺序。
 *
 * 如果bean在BeanPostProcessor实现之前初始化，该bean就无法被代理
 */
@Data
@AllArgsConstructor
public class MyBeanPostProcessor implements BeanPostProcessor, Ordered {

    private String name;

    /**
     * 实例化、依赖注入完毕，在调用显示的初始化之前完成一些定制的初始化任务
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("\033[32m" + "BeanPostProcessor：：postProcessBeforeInitialization：" +beanName+ "\033[0m");
        return bean;
    }

    /**
     * 实例化、依赖注入、初始化完毕时执行
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("\033[32m" + "BeanPostProcessor：：postProcessAfterInitialization：" +beanName+ "\033[0m");
        this.name="候莫";
        return bean;
    }

    /**
     * @Order、Ordered不影响类的加载顺序而是影响Bean加载如IOC容器之后执行的顺序
     */
    @Override
    public int getOrder() {
        return 999;
    }
}
