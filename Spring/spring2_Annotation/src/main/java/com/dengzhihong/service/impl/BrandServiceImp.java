package com.dengzhihong.service.impl;

import com.dengzhihong.dao.BrandDao;
import com.dengzhihong.pojo.Brand;
import com.dengzhihong.pojo.PageBean;
import com.dengzhihong.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("brandServiceImp")
//默认将类中的所有函数纳入事务管理.
@Transactional

public class BrandServiceImp implements BrandService {

    @Autowired
    private BrandDao brandDao;


    @Override
    public int add(Brand brand) {
        int id= brandDao.add(brand);
        return id;
    }

    /**
     * 捕获异常后但是不抛出去是无法执行回滚事务的
     *  建议：用本方式写,使上层方法能够检测到数据插入失败，因此必须抛出异常，然后在调用它的方法中捕获异常并提示用户数据提交失败
     *  @throws Exception
     * 运行时异常不作捕获则则能够正常执行回滚事务
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    @Override
    public void deleteByIds(String[] idarrs) {

        brandDao.deleteByIds(idarrs);
    }

    @Override
    public void deleteOne(String id) {
        brandDao.deleteOne(id);
    }

    @Override
    public int update(Brand brand) {
        int id= brandDao.update(brand);
        return id;
    }



    @Override
    public PageBean<Brand> selectByPageCondition(int currentPage, int size, Brand brand) {
        PageBean<Brand> pageBean= brandDao.selectByPageCondition(currentPage,size,brand);
        return pageBean;
    }
}
