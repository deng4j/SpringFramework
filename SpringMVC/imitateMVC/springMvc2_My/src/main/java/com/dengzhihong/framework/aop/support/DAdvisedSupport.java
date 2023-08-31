package com.dengzhihong.framework.aop.support;

import com.dengzhihong.framework.aop.aspect.DAfterReturningAdviceInterceptor;
import com.dengzhihong.framework.aop.aspect.DAfterThrowingAdviceInterceptor;
import com.dengzhihong.framework.aop.aspect.DMethodBeforeAdviceInterceptor;
import com.dengzhihong.framework.aop.config.DAopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DAdvisedSupport {
    private Class<?> targetClass;
    private Object target;
    private DAopConfig config;
    private Pattern pointCutClassPattern;
    private Map<Method,List<Object>> methodCache;

    public DAdvisedSupport(DAopConfig config) {
        this.config=config;
    }

    public Class<?> getTargetClass(){
        return this.targetClass;
    }


    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method,  Class<?> targetClass) throws NoSuchMethodException {
        //拿到拦截器链
        List<Object> cached = methodCache.get(method);
        if (cached==null){
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            //method有可能被代理过了
            cached= methodCache.get(m);
            this.methodCache.put(m,cached);
        }
        return cached;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        try {
            parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析配置文件
     */
    private void parse() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //public .* com\.dengzhihong\.demo\.service\..*Service..*\(.*\)
        String pointCut = config.getPointCut()
                .replaceAll("\\.","\\\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\\\(")
                .replaceAll("\\)","\\\\)");
        String pointCutForClassRegex = pointCut.substring(0,pointCut.lastIndexOf("\\(")-4)+"..*";
        pointCut=pointCutForClassRegex+"\\(.*\\)";
        pointCutClassPattern= Pattern.compile("class "+pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ")+1));


        methodCache=new HashMap<>();

        Pattern pattern = Pattern.compile(pointCut);

        Class<?> aspectClazz = Class.forName(this.config.getAspectClass());
        Map<String,Method>  aspectMethodMap=new HashMap<>();
        for (Method method : aspectClazz.getMethods()) {
            aspectMethodMap.put(method.getName(), method);
        }

        for (Method method : this.targetClass.getMethods()) {
            String methodName = method.toString();
            if (methodName.contains("throws")){
                methodName=methodName.substring(0,methodName.lastIndexOf("throws")).trim();

            }
            Matcher matcher = pattern.matcher(methodName);
            if (matcher.matches()){
                //执行器链
                List<Object> advise=new LinkedList<>();
                //把每个方法包装成MethodIterceptor
                //before
                if (!(null==config.getAspectBefore()||"".equals(config.getAspectBefore()))){
                    advise.add(new DMethodBeforeAdviceInterceptor(aspectMethodMap.get(config.getAspectBefore()),aspectClazz.newInstance()));
                }
                //after
                if (!(null==config.getAspectAfter()||"".equals(config.getAspectAfter()))){
                    advise.add(new DAfterReturningAdviceInterceptor(aspectMethodMap.get(config.getAspectAfter()),aspectClazz.newInstance()));
                }
                //afterThrow
                if (!(null==config.getAspectAfterThrow()||"".equals(config.getAspectAfterThrow()))){
                    DAfterThrowingAdviceInterceptor dAfterThrowingAdviceInterceptor = new DAfterThrowingAdviceInterceptor(aspectMethodMap.get(config.getAspectAfterThrow()), aspectClazz.newInstance());
                    dAfterThrowingAdviceInterceptor.setThrowName(config.getAspectAfterThrowingName());
                    advise.add(dAfterThrowingAdviceInterceptor);
                }
                methodCache.put(method,advise);
            }
        }

    }

    public void setTarget(Object target) {
        this.target=target;
    }
    public Object getTarget(){
        return this.target;
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}


