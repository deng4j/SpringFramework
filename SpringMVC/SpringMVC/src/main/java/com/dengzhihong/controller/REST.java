package com.dengzhihong.controller;

import com.dengzhihong.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*@Controller
//代表所有方法都有@ResponseBody
@ResponseBody*/

//代表上面两个
@RestController
@RequestMapping("/users")

public class REST {

    /**
     * 相对于@RequestMapping(method = RequestMethod.GET)
     */
    @GetMapping
    public String get() {
        System.out.println("get");
        return "get";
    }


    /**
     * 相对于@RequestMapping(method = RequestMethod.POST)
     */
    @PostMapping
    public String post(@RequestBody User user) {
        System.out.println(user);
        return "post";
    }

    /**
     * 万物皆可map
     *
     * @param map
     * @return
     */
    @PostMapping
    public ResponseEntity putAnswers(@RequestBody Map map) {
        List<Map<String, String>> answers = (List<Map<String, String>>) map.get("answers");
        return ResponseEntity.ok(null);
    }

    /**
     * 路径参数
     * 相对于：@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        System.out.println("delete:" + id);
        return "delete";
    }

    /**
     * 相对于：@RequestMapping(method = RequestMethod.PUT)
     */
    @PutMapping("/{id}")
    public String put(@RequestBody User user, @PathVariable int id) {
        System.out.println(id);
        System.out.println("put:" + user);
        return "put";
    }

}
