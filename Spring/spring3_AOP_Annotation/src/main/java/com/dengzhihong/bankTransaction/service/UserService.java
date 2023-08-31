package com.dengzhihong.bankTransaction.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface UserService {
     /**
      * 实现方法必须是public修饰符，否则注解不会生效。因为事务是基于动态代理实现。
      */
     @Transactional(rollbackFor = IOException.class)
     void transfer(String outName,String inName,double moeny);

     @Transactional(rollbackFor = IOException.class)
     void transferCps(String outName ,double moeny);
}
