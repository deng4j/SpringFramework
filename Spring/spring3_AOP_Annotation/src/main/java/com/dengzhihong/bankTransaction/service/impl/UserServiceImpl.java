package com.dengzhihong.bankTransaction.service.impl;

import com.dengzhihong.bankTransaction.mapper.UserMapper;
import com.dengzhihong.bankTransaction.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService , ApplicationContextAware {
    private  ApplicationContext applicationContext;


    @Autowired
    private UserMapper userMapper;

    /**
     * 获取当前对象
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /**
     * userMapper两个方法默认是加入到transfer方法事务中的。
     * 具体参考，事务配置和事务传播行为csdn
     *
     */
    @Override
    public void transfer(String outName,String inName,double moeny) {
        try {
            userMapper.outMoney(outName,moeny);
            userMapper.inMoeny(inName,moeny);
        } finally {

        }
    }

    /**
     * 同类调用aop不生效
     */
    @Override
    public void transferCps(String outName, double moeny) {


        UserService userService = applicationContext.getBean(UserService.class);
        System.out.println(this);
        System.out.println(this.getClass());
        System.out.println(userService.getClass());

        try {
            //开始转钱
            userService.transfer(outName,"A",moeny);
            Thread.sleep(300);
            userService.transfer("A","B",moeny);
            Thread.sleep(300);
            userService.transfer("B","C",moeny);
            Thread.sleep(300);
            userService.transfer("C","D",moeny);
            Thread.sleep(300);
            userService.transfer("D","张部海外账号",moeny);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
