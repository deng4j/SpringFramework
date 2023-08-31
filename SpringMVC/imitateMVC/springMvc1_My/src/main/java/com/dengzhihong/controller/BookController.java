package com.dengzhihong.controller;

import com.dengzhihong.Anno.DAutowired;
import com.dengzhihong.Anno.DController;
import com.dengzhihong.Anno.DRequestMapping;
import com.dengzhihong.Anno.DRequestParam;
import com.dengzhihong.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@DController
@DRequestMapping("book")
public class BookController {

    @DAutowired
    private BookService bookService;

    @DRequestMapping("/name")
    public void getName(HttpServletRequest req, HttpServletResponse resp,
                        @DRequestParam("name")String name,@DRequestParam("count") Integer count){
        String result= bookService.getName(name);
        try {
            resp.getWriter().write(result+":"+count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
