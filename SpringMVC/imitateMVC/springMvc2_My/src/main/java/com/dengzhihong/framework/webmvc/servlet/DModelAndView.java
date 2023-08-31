package com.dengzhihong.framework.webmvc.servlet;

import java.util.Map;

public class DModelAndView {

    private String viewName;
    private Map<String,?> model;

    public DModelAndView() {
    }

    public DModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public DModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public DModelAndView(Map<String, ?> model) {
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }
}
