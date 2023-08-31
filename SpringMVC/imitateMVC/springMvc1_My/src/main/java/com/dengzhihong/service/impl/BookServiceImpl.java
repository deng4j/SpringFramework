package com.dengzhihong.service.impl;

import com.dengzhihong.Anno.DService;
import com.dengzhihong.service.BookService;

@DService
public class BookServiceImpl implements BookService {

    @Override
    public String getName(String name) {
        return name;
    }
}
