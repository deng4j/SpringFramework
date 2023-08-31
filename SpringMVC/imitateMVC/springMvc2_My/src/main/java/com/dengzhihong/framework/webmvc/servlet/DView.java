package com.dengzhihong.framework.webmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DView {

   private final String DEFAULT_CONTENT_TYPE="text/html;charset=utf-8";
   private File viewFile;

   public DView(File viewFile) {
      this.viewFile=viewFile;
   }

   public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception{
      StringBuffer stringBuffer = new StringBuffer();

      //"r":以只读方式打开。
      RandomAccessFile ra=new RandomAccessFile(this.viewFile,"r");
      String line=null;
      while (null!=( line=ra.readLine())){
         line=new String(line.getBytes("ISO-8859-1"),"utf-8");
         Pattern pattern = Pattern.compile("￥\\{[^\\}]+\\}", Pattern.CASE_INSENSITIVE);
         Matcher matcher = pattern.matcher(line);
         //读到Y{}开始替换
         while (matcher.find()){
            String paramName=matcher.group();
            paramName=paramName.replaceAll("￥\\{|\\}","");
            Object paramValue = model.get(paramName);
            if (null==paramValue){
               continue;
            }
            line=matcher.replaceFirst(makeStringForRegExp(paramValue.toString()) );
            matcher=pattern.matcher(line);
         }
         stringBuffer.append(line);
      }

      response.setCharacterEncoding("utf-8");
      response.getWriter().write(stringBuffer.toString());

   }

   //处理特殊字符
   public static String makeStringForRegExp(String str) {
      return str.replace("\\", "\\\\").replace("*", "\\*")
              .replace("+", "\\+").replace("|", "\\|")
              .replace("{", "\\{").replace("}", "\\}")
              .replace("(", "\\(").replace(")", "\\)")
              .replace("^", "\\^").replace("$", "\\$")
              .replace("[", "\\[").replace("]", "\\]")
              .replace("?", "\\?").replace(",", "\\,")
              .replace(".", "\\.").replace("&", "\\&");
   }
}
