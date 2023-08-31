package com.dengzhihong.service;

import com.dengzhihong.pojo.Brand;
import com.dengzhihong.pojo.PageBean;

import java.util.List;

public interface BrandService {

    int add(Brand brand);

    void deleteByIds(String[] idarrs);

    void deleteOne(String id);

    int update(Brand brand);


    PageBean<Brand> selectByPageCondition(int currentPage, int size, Brand brand);
}
