package com.dengzhihong.framework.beans;

/**
 * 包装类：BeanWrapper是对Bean的包装
 */
public class DBeanWrapper {

    //对象实例
    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public DBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWarppedInstance(){
        return this.wrappedInstance;
    }

   public Class<?> getWrappedClass(){
       return this.wrappedInstance.getClass();
    }
}
