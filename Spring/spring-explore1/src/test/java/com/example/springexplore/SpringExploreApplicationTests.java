package com.example.springexplore;

import com.example.springexplore.ApplicationEvent.beans.EmailPublisher;
import com.example.springexplore.ApplicationEvent.EventConfig;
import com.example.springexplore.ApplicationEvent.beans.BookingCreatedEvent;
import com.example.springexplore.AwareConfig.AwareConfig;
import com.example.springexplore.AwareConfig.beans.ApplicationContextAwareBean;
import com.example.springexplore.FactoryBeanConfig.FactoryBeanConfig;
import com.example.springexplore.FactoryBeanConfig.beans.User;
import com.example.springexplore.FactoryBeanConfig.beans.UserFactoryBean;
import com.example.springexplore.LifecycleConfig.LifecycleConfig;
import com.example.springexplore.LifecycleConfig.beans.MyLifecycle;
import com.example.springexplore.PostConfig.PostConfig;
import com.example.springexplore.PostConfig.beans.MyBeanPostProcessor;
import com.example.springexplore.beanLifeConfig.BeanLifeConfig;
import com.example.springexplore.beanLifeConfig.beans.BeanLife;
import com.example.springexplore.copeConfig.beans.TenantBean;
import com.example.springexplore.copeConfig.TenantScopeConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

class SpringExploreApplicationTests {

    @Test
    public void testTenantScope(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TenantScopeConfig.class);
        try{

            TenantBean foo =  ctx.getBean("foo", TenantBean.class);
            foo.sayHello();

            TenantBean bar = ctx.getBean("bar", TenantBean.class);
            bar.sayHello();

            Map<String, TenantBean> foos = ctx.getBeansOfType(TenantBean.class);


            BeanDefinition fooDefinition = ctx.getBeanDefinition("foo");
            BeanDefinition barDefinition = ctx.getBeanDefinition("bar");
        }
        finally {
            ctx.close();
        }
    }

    @Test
    public void testBeanLife(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BeanLifeConfig.class);
        try{
            BeanLife beanLife =  ctx.getBean("beanLife", BeanLife.class);
        }
        finally {
            ctx.close();
        }
    }


    @Test
    public void testMyLifecycle(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(LifecycleConfig.class);
        try{
            MyLifecycle myLifecycle =  ctx.getBean("myLifecycle", MyLifecycle.class);
            System.out.println(myLifecycle);
        }
        finally {
            ctx.close();
        }
    }

    @Test
    public void testApplicationContextAwareBean(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AwareConfig.class);
        try{
            ApplicationContextAwareBean awareBean =  ctx.getBean("applicationContextAwareBean", ApplicationContextAwareBean.class);
            System.out.println(awareBean);
        }
        finally {
            ctx.close();
        }
    }


    @Test
    public void testBeanPostProcessorBean(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(PostConfig.class);
        try{
            MyBeanPostProcessor awareBean =  ctx.getBean("myBeanPostProcessor", MyBeanPostProcessor.class);
            System.out.println(awareBean);
        }
        finally {
            ctx.close();
        }
    }


    @Test
    public void testBookingEventsListener(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(EventConfig.class);
        try{
            //触发事件1
            BookingCreatedEvent bookingggg = new BookingCreatedEvent(this, "bookingggg");
            ctx.publishEvent(bookingggg);

            //触发事件2
            EmailPublisher bean =  ctx.getBean("emailPublisher", EmailPublisher.class);
            bean.sendMessage("叮叮当，叮叮当，铃儿响叮当！！！");
        }
        finally {
            ctx.close();
        }
    }


    @Test
    public void testFactoryBean() throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(FactoryBeanConfig.class);
        try{
            //获取FactoryBean
            UserFactoryBean bean =  ctx.getBean("&userFactoryBean", UserFactoryBean.class);
            System.out.println(bean);

            //获取user
            User user =  ctx.getBean("userFactoryBean", User.class);
            System.out.println(user);
        }
        finally {
            ctx.close();
        }
    }
}
