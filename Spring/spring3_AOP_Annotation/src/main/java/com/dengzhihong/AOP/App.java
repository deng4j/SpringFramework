package com.dengzhihong.AOP;


import com.dengzhihong.AOP.config.SpringConfig;
import com.dengzhihong.AOP.dao.BookDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDaoImpl = atx.getBean(BookDao.class);

        System.out.println(bookDaoImpl);
        System.out.println(bookDaoImpl.getClass());

        bookDaoImpl.delete("二");
        System.out.println("--------------------------------");

        int i = bookDaoImpl.select(10);
        System.out.println(i);
    }
}
