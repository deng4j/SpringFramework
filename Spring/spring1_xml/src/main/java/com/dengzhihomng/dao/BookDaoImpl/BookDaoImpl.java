package com.dengzhihomng.dao.BookDaoImpl;

import com.dengzhihomng.dao.BookDao;

public class BookDaoImpl implements BookDao {
    public void save() {
        System.out.println("book save");
    }

    /**
     * 设置生命周期
     */
    public void init(){
        System.out.println("bookDao init");
    }
    public void destroy(){
        System.out.println("bookDao destroy");
    }
}
