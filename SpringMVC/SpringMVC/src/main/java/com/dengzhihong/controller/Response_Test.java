package com.dengzhihong.controller;

import com.dengzhihong.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Response_Test {

    /**
     * 页面跳转
     */
    @RequestMapping("/page")
    public String index() throws IOException {
        return "page.jsp";
    }

    /**
     * 响应POJO对象
     * 返回值为实体类对象，设置返回值为实体类类型，即可实现返回对应对象的json数据，
     * 需要依赖@ResponseBody注解和@EnableWebMvc注解。
     */
    @RequestMapping("/toJsonPOJO")
    @ResponseBody
    public User toJsonPOJO(){
        System.out.println("返回json对象数据");
        User user = new User();
        user.setName("itcast");
        user.setAge(15);
        return user;
    }

    /**
     * 响应POJO集合对象：
     * //返回值为集合对象，设置返回值为集合类型，即可实现返回对应集合的json数组数据。
     */
    @RequestMapping("/toJsonList")
    @ResponseBody
    public List<User> toJsonList(){
        System.out.println("返回json集合数据");
        User user1 = new User();
        user1.setName("传智播客");
        user1.setAge(15);

        User user2 = new User();
        user2.setName("黑马程序员");
        user2.setAge(12);

        List<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);

        return userList;
    }
}
