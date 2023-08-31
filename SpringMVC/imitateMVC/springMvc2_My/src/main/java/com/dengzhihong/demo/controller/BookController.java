package com.dengzhihong.demo.controller;


import com.dengzhihong.demo.service.BookService;
import com.dengzhihong.framework.annotation.DAutowired;
import com.dengzhihong.framework.annotation.DController;
import com.dengzhihong.framework.annotation.DRequestMapping;
import com.dengzhihong.framework.annotation.DRequestParam;
import com.dengzhihong.framework.webmvc.servlet.DModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

@DController
@DRequestMapping("book")
public class BookController {

    @DAutowired
    private BookService bookService;

    @DRequestMapping("/name")
    public DModelAndView getName(HttpServletRequest req, HttpServletResponse resp,
                                 @DRequestParam("name")String name, @DRequestParam("count") Integer count){
        String result= bookService.getName(name);
        result=result+"::"+count;
        return out(resp,result);
    }


    @DRequestMapping("/e")
    public DModelAndView exception(HttpServletRequest request,HttpServletResponse response){
        try {
            bookService.e();
            return out(response,"");
        } catch (Exception e) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("detail",e.getCause().getMessage());
            model.put("stackTrance", Arrays.toString( e.getStackTrace()).replaceAll("\\[|\\]",""));
            return new DModelAndView("500",model);
        }

    }



    private DModelAndView out(HttpServletResponse resp,String str){
        try {
            resp.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
