package com.dengzhihong.framework.webmvc.servlet;

import java.io.File;
import java.util.Locale;

public class DViewResolver {

    private final String DEFAULT_TEMPLATE_SUFFIX=".html";

    private File templateRootDir;

    public DViewResolver(String templateRoot) {
        String file = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        this.templateRootDir = new File(file);
    }

    public DView resolveViewName(String viewName, Locale locale){
        if (null==viewName||"".equals(viewName)){
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFIX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));
        return new DView(templateFile);
    }


}
