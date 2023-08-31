package com.example.springexplore.copeConfig;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义范围
 */
public class TenantScope implements Scope {

    private Map<String, Object> scopedObjects = Collections.synchronizedMap(new HashMap<>());

    private Map<String, Runnable> destructionCallbacks = Collections.synchronizedMap(new HashMap<>());

    /**
     * 返回对象
     */
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if(!scopedObjects.containsKey(name)) {
            scopedObjects.put(name, objectFactory.getObject());
        }
        System.out.println("\033[32m" + "return："+name + "\033[0m");
        return scopedObjects.get(name);
    }

    /**
     * 该方法从作用域中删除命名对象，并删除其注册的销毁回调，并返回删除的对象
     */
    @Override
    public Object remove(String name) {
        System.out.println("\033[32m" + "remove："+name + "\033[0m");
        destructionCallbacks.remove(name);
        return scopedObjects.remove(name);
    }

    /**
     * 提供一个回调，当命名对象被销毁或作用域本身被应用程序销毁时，将执行该回调
     */
    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        System.out.println("\033[32m" + "callback："+name + "\033[0m");
        destructionCallbacks.put(name, callback);
    }

    /**
     *  如果您的范围支持多个上下文对象，则将每个对象与一个键值相关联，并返回与提供的键参数相对应的对象。 否则，约定将返回null
     */
    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    /**
     * 如果您的范围支持对话ID的概念，则可以在此处将其返回。 否则，约定将返回null
     */
    @Override
    public String getConversationId() {
        return"tenant";
    }
}
