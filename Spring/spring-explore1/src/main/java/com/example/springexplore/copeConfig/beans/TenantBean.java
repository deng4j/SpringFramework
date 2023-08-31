package com.example.springexplore.copeConfig.beans;


public class TenantBean {

    private final String name;

    public TenantBean(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println("\033[32m" + String.format("Hello from %s of type %s",
            this.name,
            this.getClass().getName()) + "\033[0m");
    }
}
