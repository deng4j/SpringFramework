package com.example.springexplore.LifecycleConfig.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.SmartLifecycle;

/**
 * Lifecycle接口只是用于显式启动/停止通知的简单约定，并不意味着在上下文刷新时自动启动。
 * SmartLifecycle，以便对特定 bean 的自动启动(包括启动阶段)进行细粒度的控制。
 * 另外，请注意，不能保证在销毁之前会发出停止通知：在常规关闭时，所有Lifecycle bean 都会在传播一般销毁回调之前首先收到停止通知；
 * 但是，在上下文生命周期中进行热刷新或中止刷新尝试时，仅将调用 destroy 方法。
 *
 * SmartLifecycle 不仅仅能在初始化后执行一个逻辑，还能再关闭前执行一个逻辑，
 * 比如你一个服务在启动时向服务注册发现中心发一个信号告诉它服务上线了，下线前通知它你下线了。
 *
 * LifecycleProcessor本身也是Lifecycle接口的扩展。
 */
@AllArgsConstructor
@Data
public class MyLifecycle implements SmartLifecycle {

    private String name;

    private boolean running;

    /**
     * 启动回调：
     * 该方法中启动任务或者其他异步服务，比如开启MQ接收消息当上下文被刷新（所有对象已被实例化和初始化之后）时，
     * 将调用该方法，默认生命周期 处理器将检查每个SmartLifecycle对象的isAutoStartup()方法返回的布尔值。如果为“true”，
     * 则该方法会被调用，而不是等待显式调用自己的start()方法，同时当上下文被刷新（所有对象已被实例化和初始化之后）时，
     * 将调用该方法，默认生命周期处理器将检查每个SmartLifecycle对象的isAutoStartup()方法返回的布尔值，
     * 如果为true，则该方法会被调用，而不是等待显式调用自己的start()方法，
     * boolean isRunning() 的状态信号也决定此方法是否执行只有当该方法返回false start()方法才会被执行
     */
    @Override
    public void start() {
        System.out.println("\033[32m" + "SmartLifecycle：：start："+this.getClass().getName() + "\033[0m");
        this.running=true;
    }

    /**
     * 关闭回调
     */
    @Override
    public void stop() {
        System.out.println("\033[32m" + "SmartLifecycle：：stop()：" +this.getClass().getName()+ "\033[0m");
        this.running=false;
    }

    /**
     * 1. 只有该方法返回false时，start方法才会被执行。<br/>
     * 2. 只有该方法返回true时，stop(Runnable callback)或stop()方法才会被执行。
     */
    @Override
    public boolean isRunning() {
        return running;
    }

    /**
     * 根据该方法的返回值决定是否执行start方法。<br/>
     * 返回true时start方法会被自动执行，返回false则不会。
     */
    @Override
    public boolean isAutoStartup() {
        return true;
    }

    /**
     *  生命周期结束时调用的方法。只有当 boolean isRunning() 方法返回true 该方法才会被执行，
     *  该方法是属于Lifecyle接口的，被SmartLifeCycle作为了一个钩子。 实际执行的是SmartLifeCycle中的这个方法
     */
    @Override
    public void stop(Runnable callback) {
        System.out.println("\033[32m" + "SmartLifecycle：：stop(Runnable callback)：" +this.getClass().getName()+ "\033[0m");
        SmartLifecycle.super.stop(callback);
    }

    /**
     * 相位最低的对象首先启动，而停止时相反
     */
    @Override
    public int getPhase() {
        return SmartLifecycle.super.getPhase();
    }
}
