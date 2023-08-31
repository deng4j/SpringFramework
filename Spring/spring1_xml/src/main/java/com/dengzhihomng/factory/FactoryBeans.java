package com.dengzhihomng.factory;

import com.dengzhihomng.dao.BookDao;
import com.dengzhihomng.dao.BookDaoImpl.BookDaoImpl;
import org.springframework.beans.factory.FactoryBean;

public class FactoryBeans implements FactoryBean<BookDao> {

    public BookDao getObject() throws Exception {
        return new BookDaoImpl();
    }

    public Class<?> getObjectType() {
        return BookDao.class;
    }

    /**
     * 是否为单例
     * @return
     */
    public boolean isSingleton() {
        return true;
    }
}
