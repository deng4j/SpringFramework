package com.example.springexplore.FactoryBeanConfig.beans;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * FactoryBean用来创建AOP的代理对象，FactoryBean不遵循Spring的生命周期
 */
@Component
public class UserFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        // 假设User的实例化过程比较复杂，在此处进行User的实例化
        System.out.println("\033[32m" + "UserFactoryBean" + "\033[0m");
        return new User("张三");
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}