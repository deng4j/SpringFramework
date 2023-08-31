package com.dengzhihong.bankTransaction;

import com.dengzhihong.bankTransaction.config.Spring_Config;
import com.dengzhihong.bankTransaction.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class app {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(Spring_Config.class);
        UserService userService = atx.getBean(UserService.class);
        userService.transferCps("张部国内账号",100);

        System.out.println(userService);
        System.out.println(userService.getClass());
    }
}
