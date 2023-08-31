package com.dengzhihong.config;


import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class Mybatis_Config {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sft=new SqlSessionFactoryBean();
        //设置pojo别名
        sft.setTypeAliasesPackage("com.dengzhihong.pojo");

        //设置数据源
        sft.setDataSource(dataSource);

        return sft;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        //设置自动代理接口包
        msc.setBasePackage("com.dengzhihong.dengzhihong");
        return msc;
    }
}
