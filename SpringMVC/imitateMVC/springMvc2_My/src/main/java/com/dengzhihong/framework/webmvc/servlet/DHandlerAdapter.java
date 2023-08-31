package com.dengzhihong.framework.webmvc.servlet;

import com.dengzhihong.framework.annotation.DRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DHandlerAdapter {

    public boolean support(Object handler){
        return (handler instanceof DHandlerMapping);
    }

    public DModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws InvocationTargetException, IllegalAccessException {
        DHandlerMapping handlerMapping=(DHandlerMapping) handler;

        //把方法的形参列表和request的参数列表所在顺序一一对应
        HashMap<String, Integer> paramIndexMapping = new HashMap<>(16);

        Annotation[][] annotations = handlerMapping.getMethod().getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation:annotations[i]){
                //获取参数上的注解
                DRequestParam requestParam = (DRequestParam) annotation;
                if (requestParam instanceof DRequestParam){
                    String paramName = requestParam.value();
                    if (!"".equals(paramName.trim())){
                        paramIndexMapping.put(paramName,i);
                    }
                }
            }
        }

        //提取方法中的request和response参数
        Class<?> [] paramsTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i = 0; i < paramsTypes.length ; i ++) {
            Class<?> type = paramsTypes[i];
            if(type == HttpServletRequest.class ||
                    type == HttpServletResponse.class){
                paramIndexMapping.put(type.getName(),i);
            }
        }


        //获得形参列表
        Map<String, String[]> params = request.getParameterMap();
        //获得实参列表
        Object[] paramValues=new Object[paramsTypes.length];

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "")
                    .replaceAll("\\s", ",");

            if (!paramIndexMapping.containsKey(param.getKey())){
                continue;
            }
            Integer index =paramIndexMapping.get(param.getKey());
            paramValues[index] =convert(paramsTypes[index],value);
        }

        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }

        if(paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = response;
        }
        Method method = handlerMapping.getMethod();
        method.setAccessible(true);
        Object returnValue = method.invoke(handlerMapping.getController(),paramValues);

        if (returnValue==null || returnValue instanceof Void){
            return null;
        }
        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == DModelAndView.class;

        if (isModelAndView){
            return (DModelAndView) returnValue;
        }

        return null;
    }

    private Object convert(Class<?> type,String value){
        value = value.replaceAll("\\[|\\]","")
                .replaceAll("\\s",",");

        if (Integer.class==type){
            return Integer.valueOf(value);
        }
        return value;
    }
}
