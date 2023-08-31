package com.dengzhihong.web.servlet;

import com.alibaba.fastjson.JSON;
import com.dengzhihong.pojo.Brand;
import com.dengzhihong.pojo.PageBean;
import com.dengzhihong.service.BrandService;
import com.dengzhihong.service.impl.BrandServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/brand/*")
@Controller
public class BrandServlet extends BaseServlet{

    @Autowired
    @Qualifier("brandServiceImp")
    private BrandService brandService;


    public void test(HttpServletRequest req, HttpServletResponse resp){
        System.out.println(brandService);
    }


    public void BrandAddServlet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String str = reader.readLine();

        //设置头信息
        resp.setContentType("text/html;charset=utf-8");

        Brand brand = null;
        try {
            brand = JSON.parseObject(str, Brand.class);
        } catch (Exception e) {
            System.out.println("BrandAddServlet:json解析错误");
            resp.getWriter().write("fail");
            return;
        }
        System.out.println(brand);
        int id=-1;
        if (brand!=null&&!brand.getBrandName().equals("")){
            id=brandService.add(brand);
        }
        System.out.println(id);
        if (id!=-1){
            resp.getWriter().write("success");
        }else {
            resp.getWriter().write("fail");
        }
    }
    public void BrandDelByIdsServlet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String ids = reader.readLine();
        String[] strings = JSON.parseObject(ids, String[].class);
        System.out.println(strings.length);
        //设置头信息
        resp.setContentType("text/html;charset=utf-8");
        if (strings.length<1){
            resp.getWriter().write("fail");
            return;
        }
        brandService.deleteByIds(strings);
    }
    public void BrandDelOne(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String id = reader.readLine();
        System.out.println(id);
        //brandService.deleteOne(id);
    }
    public void BrandUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String str = reader.readLine();

        //设置头信息
        resp.setContentType("text/html;charset=utf-8");

        Brand brand=null;
        try {
            brand = JSON.parseObject(str, Brand.class);
        } catch (Exception e) {
            System.out.println("BrandUpdate:json解析错误");
            resp.getWriter().write("fail");
            return;
        }
        System.out.println(brand);
        int id= brandService.update(brand);
        if (id!=-1){
            resp.getWriter().write("success");
        }else {
            resp.getWriter().write("fail");
        }
    }


    /**
     * 分页条件查询
     * @param req
     * @param resp
     * @throws IOException
     */
    public  void  selectByPageCondition(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String _currentPage = req.getParameter("currentPage");
        String _size = req.getParameter("size");

        int currentPage = Integer.parseInt(_currentPage);
        int size = Integer.parseInt(_size);

        BufferedReader reader = req.getReader();
        String json = reader.readLine();

        Brand brand = JSON.parseObject(json, Brand.class);
        System.out.println(brandService);
        PageBean<Brand> pageBean= brandService.selectByPageCondition(currentPage,size,brand);
        //设置头信息
        resp.setContentType("text/json;charset=utf-8");
        String str = JSON.toJSONString(pageBean);
        resp.getWriter().write(str);
    }
}
