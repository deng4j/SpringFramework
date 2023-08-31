package com.dengzhihomng.service.impl;

import com.dengzhihomng.dao.BookDao;
import com.dengzhihomng.service.BookService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


public class BookServiceImpl implements BookService,InitializingBean,DisposableBean {

    private BookDao bookDao;

    public BookServiceImpl() {
        System.out.println("1、实例化 :加载sping-config.xml文件时，bean就会被实例化到内存（前提是scope=singleton）");
    }

    /**
     * 提供依赖注入setter方法
     * @param bookDao
     */
    public void setBookDao(BookDao bookDao) {

        this.bookDao = bookDao;
        System.out.println("2、设置属性值 :调用set方法设置属性，前提是有对应的set方法");
    }

    public void save() {
        bookDao.save();
    }

    /**
     * 生命周期控制
     * @throws Exception
     */
    public void destroy() throws Exception {
        System.out.println("BookServiceImpl destroy");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("BookServiceImpl afterPropertiesSet");
    }
}
