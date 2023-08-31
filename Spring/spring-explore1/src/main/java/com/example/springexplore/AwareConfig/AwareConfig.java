package com.example.springexplore.AwareConfig;

import com.example.springexplore.AwareConfig.beans.ApplicationContextAwareBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwareConfig {

    @Bean("applicationContextAwareBean")
    public ApplicationContextAwareBean createdAwareBean() {
        return new ApplicationContextAwareBean("applicationContextAwareBean");
    }
}