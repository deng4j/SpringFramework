package com.dengzhihong.framework.beans.config;

import lombok.Data;

@Data
public class DBeanDefinition {

    //全类名
    private String beanClassName;
    private boolean lazyInit=false;
    //首字母小写简单类名
    private String factoryBeanName;

}
