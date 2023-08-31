package com.dengzhihong.pojo;

public class Classs {

    private String name;
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Classs{" +
                "name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}
