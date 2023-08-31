package com.dengzhihong.demo.service.impl;


import com.dengzhihong.demo.service.BookService;
import com.dengzhihong.framework.annotation.DService;

@DService
public class BookServiceImpl implements BookService {

    @Override
    public String getName(String name) {
        return name;
    }

    @Override
    public void e() throws Exception {
        throw new Exception();
    }


}
