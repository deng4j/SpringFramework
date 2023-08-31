package com.dengzhihong.framework.webmvc.servlet;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

@Data
public class DHandlerMapping {
    private Method method;
    private Object controller;
    private Pattern pattern;

    public DHandlerMapping( Pattern pattern, Object controller,Method method) {
        this.method = method;
        this.controller = controller;
        this.pattern = pattern;
    }

    public DHandlerMapping() {
    }
}
