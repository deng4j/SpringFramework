package com.dengzhihong.controller;

import com.dengzhihong.pojo.Classs;
import com.dengzhihong.pojo.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
//设置请求映射前缀
@RequestMapping("user")
public class RequestMapping_Test {

    //设置映射路径url,RequestParam接收表单或路径数据
    @RequestMapping("/hello")
    //设置响应体
    @ResponseBody
    public String hello(@RequestParam("name") String name, int age) {
        System.out.println(name + ":" + age);
        return "hello";
    }

    /**
     * 传递实体参数user
     */
    @RequestMapping("/user")
    @ResponseBody
    public String user(User user) {
        System.out.println(user);
        return "user";
    }

    /**
     * 嵌套POJO参数：嵌套属性按照层次结构设定名称即可完成参数传递
     */
    @RequestMapping("/pojoContain")
    @ResponseBody
    public String classs(Classs classs) {
        System.out.println(classs);
        return "classs";
    }

    /**
     * 数组参数
     */
    @RequestMapping("/array")
    @ResponseBody
    public String array(String[] likes) {
        System.out.println(Arrays.toString(likes));
        return "array";
    }

    /**
     * 集合参数：同名请求参数可以使用@RequestParam注解映射到对应名称的集合对象中作为数据
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestParam List<String> likes) {
        System.out.println(likes);
        return "list";
    }


    /**
     * 集合参数json格式：
     * 使用@RequestBody注解将外部传递的json数组数据映射到形参的集合对象中作为数据。
     * 作用：将请求中请求体所包含的数据传递给请求参数，此注解一个处理器方法只能使用一次
     */
    @RequestMapping("/listJson")
    @ResponseBody
    public String listJson(@RequestBody List<String> likes) {
        System.out.println(likes);
        return "listJson";
    }

    /**
     * json传递实体参数user：json数据与形参对象属性名相同，定义POJO类型形参即可接收参数
     */
    @RequestMapping("/userJson")
    @ResponseBody
    public String userJson(@RequestBody User user) {
        System.out.println(user);
        return "userJson";
    }

    /**
     * json实体集合参数：json数组数据与集合泛型属性名相同，定义List类型形参即可接收参数
     */
    @RequestMapping("/listUserJson")
    @ResponseBody
    public String listUserJson(@RequestBody List<User> users) {
        System.out.println(users);
        return "usersJson";
    }


    /**
     * 时间类型参数：使用@DateTimeFormat注解设置日期类型数据格式，默认格式yyyy/MM/dd。
     * 内部依赖Converter接口
     */
    @RequestMapping("/date")
    @ResponseBody
    public String date(Date date, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1, @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date date2) {
        System.out.println(date);
        System.out.println(date1);
        System.out.println(date2);
        return "date";
    }
}
