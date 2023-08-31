package com.dengzhihomng.pojo;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Liberal {
    /**
     * int也可以注入到String[]。
     */
    private String[] arr;
    private List<Book> bookList;
    private Set<String> set;
    private Map<String,Book> bookMap;
    private Properties prop;


    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public Map<String, Book> getBookMap() {
        return bookMap;
    }

    public void setBookMap(Map<String, Book> bookMap) {
        this.bookMap = bookMap;
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }
}
