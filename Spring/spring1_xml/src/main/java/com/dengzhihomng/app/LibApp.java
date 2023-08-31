package com.dengzhihomng.app;

import com.dengzhihomng.pojo.Book;
import com.dengzhihomng.pojo.Liberal;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class LibApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Liberal liber = (Liberal) ctx.getBean("liber");
        String[] arr = liber.getArr();
        System.out.println("arr:"+arr);

        List<Book> bookList = liber.getBookList();
        System.out.println("List:"+bookList);

        Set<String> set = liber.getSet();
        System.out.println("Set:"+set);

        Map<String, Book> bookMap = liber.getBookMap();
        System.out.println("Map:"+bookMap);

        Properties prop = liber.getProp();
        System.out.println("prop:"+prop);
    }
}
