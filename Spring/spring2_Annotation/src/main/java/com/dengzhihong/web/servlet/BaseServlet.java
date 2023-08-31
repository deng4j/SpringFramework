package com.dengzhihong.web.servlet;

import com.dengzhihong.config.Spring_Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    /**
     * 使用反射根据请求的最后一段路径进行方法分发
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径
        String uri = req.getRequestURI();
        //获取方法名
        int index = uri.lastIndexOf('/');
        String methodName = uri.substring(index + 1);

        //执行方法，this(谁调用本方法就是谁)
        Class<? extends BaseServlet> aClass = this.getClass();
        try {
            AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(Spring_Config.class);
            Method method = aClass.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(atx.getBean(BrandServlet.class), req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
