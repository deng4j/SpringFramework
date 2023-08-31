package com.dengzhihong.AOP.advice;

import com.dengzhihong.AOP.anno.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
//设置切面类
@Aspect
/**
 * 切面类：
 * 1. Spring容器启动
 * 2. 读取所有切面配置中的切入点
 * 3. 初始化bean，判定bean对应的类中的方法是否匹配到任意切入点
 *    - 匹配失败，创建原始对象
 *    - 匹配成功，创建原始对象（目标对象）的代理对象
 * 4. 获取bean执行方法
 *    - 获取的bean是原始对象时，调用方法并执行，完成操作
 *    - 获取的bean是代理对象时，根据代理对象的运行模式运行原始方法与增强的内容，完成操作
 *
 * 5.Spring Aop自己扩充的一种一共是10种类型的表达式，分别如下：
 * execution：一般用于指定方法的执行，用的最多。
 * within：指定某些类型的全部方法执行，也可用来指定一个包。
 * this：Spring Aop是基于代理的，生成的bean也是一个代理对象，this就是这个代理对象，当这个对象可以转换为指定的类型时，对应的切入点就是它了，Spring Aop将生效。
 * target：当被代理的对象可以转换为指定的类型时，对应的切入点就是它了，Spring Aop将生效。
 * args：当执行的方法的参数是指定类型时生效。
 * @target：当代理的目标对象上拥有指定的注解时生效。
 * @args：当执行的方法参数类型上拥有指定的注解时生效。
 * @within：与@target类似，看官方文档和网上的说法都是@within只需要目标对象的类或者父类上有指定的注解，则@within会生效，而@target则是必须是目标对象的类上有指定的注解。而根据笔者的测试这两者都是只要目标类或父类上有指定的注解即可。
 * @annotation：当执行的方法上拥有指定的注解时生效。
 * bean：当调用的方法是指定的bean的方法时生效。
 */
public class BookAdvice {

    /**
     * 设置切入点:
     * 1.描述切入点通**==常描述接口==**，而不描述实现类
     * 2.访问控制修饰符针对接口开发均采用public描述（**==可省略访问控制修饰符描述==**）
     * 3.**==包名==**书写**==尽量不使用..匹配==**，效率过低，常用\*做单个包描述匹配，或精准匹配
     * 4.返回值类型对于增删改类使用精准类型加速匹配，对于查询类使用\*通配快速描述
     * 5.**==接口名/类名==**书写名称与模块相关的**==采用\*匹配==**，
     * 例如UserService书写成\*Service，绑定业务层接口名。
     * 6.通常**==不使用异常==**作为**==匹配==**规则
     *
     * 切入点表达式：
     * 1、 *：单个独立的任意符号，可以独立出现，也可以作为前缀或者后缀的匹配符出现
     * execution（public * com.itheima.*.UserService.find*(*))
     * 2、 .. ：多个连续的任意符号，可以独立出现，常用于简化包名与参数的书写
     * execution（public User com..UserService.findById(..))
     * 3、+：专用于匹配子类类型
     * execution(* *..*Service+.*(..))
     */
    @Pointcut("execution(void com.dengzhihong.AOP.dao.BookDao.delete(..))")
    public void pt() {}

    /**
     * 设置切入点的前置通知
     */
    @Before("pt()")
    public void before() {
        System.out.println("我是前置通知");
    }

    /**
     * 设置切入点的后置通知
     */
    @After("pt()")
    public void after() {
        System.out.println("我是后置通知");
    }

    /**
     * 设置切入点的环绕通知
     */
    @Around("pt()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("前环绕");

        pjp.proceed();

        System.out.println("后环绕");
    }

    /**
     * 带返回值的方法
     */

    @Pointcut("execution(int com.dengzhihong.AOP.dao.BookDao.select(..))")
    public void pt2() {}

    @Around("pt2()")
    public int aroundSelect(ProceedingJoinPoint pjp) throws Throwable {
        //获取一次执行的签名
        Signature signature = pjp.getSignature();
        //通过签名获取接口全类名
        Class declaringType = signature.getDeclaringType();
        //通过签名获取方法名
        String name = signature.getName();
        System.out.println("接口全类名" + declaringType + " " + "方法名:" + name);

        //获取连接点参数
        Object[] args = pjp.getArgs();

        System.out.println("aroundSelect前环绕");

        Integer proceed = (Integer)pjp.proceed(args);

        System.out.println("aroundSelect后环绕");
        return proceed + 100;
    }

    /**
     * 返回后通知,和自定义接收返回值，JoinPoint参数在前，自定义参数在后
     */

    @AfterReturning(value = "pt2()", returning = "ret")
    public void afterReturning(JoinPoint jp, Object ret) {
        String s = Arrays.toString(jp.getArgs());
        System.out.println("afterReturning advice ..." + ret + " " + s);
    }

    /**
     * 抛异常后通知
     */

    @AfterThrowing(value = "pt2()", throwing = "t")
    public void afterThrowing(Throwable t) {
        System.out.println("afterThrowing advice ..." + t);
    }


    //-----------------注解切点-------------------
    @Before(value = "@annotation(controllerLog)")
    public void doBefore(JoinPoint joinPoint, Log controllerLog) {
        System.out.println(controllerLog.title());
    }

    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        System.out.println(controllerLog.title());
    }
}
