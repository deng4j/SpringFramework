package com.dengzhihong.AOP.dao.impl;

import com.dengzhihong.AOP.anno.Log;
import com.dengzhihong.AOP.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {

    @Override
    @Log(title ="查询")
    public int select(int id) { System.out.println("select"); return id; }

    @Override
    public void delete(String id) {
        System.out.println("delete");
    }

    public void show(){ System.out.println("show"); }
}
