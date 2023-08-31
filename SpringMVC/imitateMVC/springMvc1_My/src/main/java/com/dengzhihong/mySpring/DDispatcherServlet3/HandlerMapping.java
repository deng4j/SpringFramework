package com.dengzhihong.mySpring.DDispatcherServlet3;

import com.dengzhihong.Anno.DRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private String url;
    private Method method;
    private Object controller;
    private Class<?>[] paramTypes;
    /**
     * 保存参数的名称和索引
     */
    private Map<String,Integer> paramIndexMap;


    public HandlerMapping(String url, Method method, Object controller) {
        this.url = url;
        this.method = method;
        this.controller = controller;

        //初始化参数类型
        this.paramTypes= method.getParameterTypes();

        paramIndexMap=new HashMap<>();
        putParamIndexMap(method);
    }

    public Map<String, Integer> getParamIndexMap() {
        return paramIndexMap;
    }

    private void putParamIndexMap(Method method) {

        //拿到方法上的参数注解，一个字段可以有多个注解
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation:annotations[i]){
                //获取参数上的注解
                DRequestParam requestParam = (DRequestParam) annotation;
                if (requestParam instanceof DRequestParam){
                    String paramName = requestParam.value();
                    if (!"".equals(paramName.trim())){
                        paramIndexMap.put(paramName,i);
                    }
                }

            }
        }

        //提取方法中的request和response参数
        Class<?> [] paramsTypes = method.getParameterTypes();
        for (int i = 0; i < paramsTypes.length ; i ++) {
            Class<?> type = paramsTypes[i];
            if(type == HttpServletRequest.class ||
                    type == HttpServletResponse.class){
                paramIndexMap.put(type.getName(),i);
            }
        }

    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }
}
