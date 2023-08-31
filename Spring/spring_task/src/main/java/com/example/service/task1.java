package com.example.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 秒分时日月周
 *
 * *:匹配该域任意值
 * ?:忽略该域，只能用在周和日两个域。
 * -:表示范围，如分域5-20，表示5分到20分，每分钟触发一次。
 * /:表示起始时间开始触发，每隔固定间隔触发一次。
 * ,:表示列出枚举。
 */
@Service
public class task1 {

    @Scheduled(cron = "0/10 * * * * ?")
    public void start(){

        System.out.println("执行一次,每10s:");
    }

    @Scheduled(cron = "0/1 * * * * ?")
    public void s1(){
        System.out.println("执行一次,每1s");
    }
}
