package com.dengzhihong.dao;

import com.dengzhihong.mapper.BrandMapper;
import com.dengzhihong.pojo.Brand;
import com.dengzhihong.pojo.PageBean;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrandDao {

    @Autowired
   private PageBean<Brand> pageBean;
    @Autowired
    private BrandMapper mapper;

    public int add(Brand brand) {

        int id = -1;
        try {
            id = mapper.add(brand);
        } catch (Exception e) {
            System.out.println("字段解析错误");
        }
        return id;
    }

    public void deleteByIds(String[] idarrs) {

        mapper.deleteByIds(idarrs);
    }

    public void deleteOne(String id) {
        mapper.deleteOne(id);
    }

    public int update(Brand brand) {
        int id = mapper.updateById(brand);
        return id;
    }




    public PageBean<Brand> selectByPageCondition(int currentPage, int size, Brand brand) {

        //设置模糊查询
        brand.setBrandName("%"+brand.getBrandName()+"%");
        brand.setCompanyName("%"+brand.getCompanyName()+"%");

        currentPage=(currentPage-1)*size;
        int count = mapper.selectTotalCountCondition(brand);


        List<Brand> brands = mapper.selectByPageCondition(currentPage, size,brand);


        pageBean.setRows(brands);
        pageBean.setTotalCount(count);

        return pageBean;
    }
}
