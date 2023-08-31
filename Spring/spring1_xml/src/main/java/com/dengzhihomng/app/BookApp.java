package com.dengzhihomng.app;

import com.dengzhihomng.pojo.Book;
import com.dengzhihomng.service.BookService;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * spring是使用反射出的构造方法创建对象的。
 *
 * 三种获取ApplicationContext的方法：
 *
 * 1.ApplicationContext 应用上下文容器取（从类路径中加载） ：
 *  new ClassPathXmlApplicationContext("bean1.xml", "bean2.xml");
 *  new ClassPathXmlApplicationContext("applicationContext.xml");
 *  但要注意bean的生命周期要为singleton，也就是说，不管没有getBean()，
 *  使用上下文容器获取bean，就会实例化该bean。
 *
 * 2.ApplicationContext （从文件系统中加载）:
 *  new FileSystemXmlApplicationContext("D:\\applicationContext.xml");
 *  文件路径为绝对路径，且注意使用转义符，直接使用“C:\sjt\idea\code\spring\spring-interface\src”，
 *  会报错，需要将“\”转义。
 *
 *  3.XmlWebApplicationContext （从web系统中加载）:
 *  在tomcat启动时就会加载，此处不做说明，在web应用中说明。
 *
 *  使用bean工厂：
 *  new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
 *  spring-config.xml文件中配置的bean不会被实例化，即光实例化容器，并不会实例化bean
 *  而是在执行以下代码时才会被实例化，即使用bean的时候。
 *
 */
public class BookApp {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookService bookService = (BookService) ctx.getBean("service");
        bookService.save();
        //注册关闭钩子函数，在虚拟机退出之前回调此函数，暴力关闭容器ctx.close()
        ctx.registerShutdownHook();


        Book book = ctx.getBean("book",Book.class);
        //按类型获取，但容器bean要唯一
        Book book2 = ctx.getBean(Book.class);
        String name = book.getName();

        System.out.println("-----"+(book==book2));
        System.out.println("-----"+name);
    }
}
