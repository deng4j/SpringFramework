package com.dengzhihomng.factory;

import com.dengzhihomng.pojo.Book;

public class BookFactory {
    public static Book getBookFactory(){
        //do something
        return new Book();
    }
}
