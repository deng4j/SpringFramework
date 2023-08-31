package com.dengzhihomng.factory;

import com.dengzhihomng.pojo.Book;

public class BookInstanceFactory {
    public  Book getBookFactory(){
        //do something
        return new Book();
    }
}
