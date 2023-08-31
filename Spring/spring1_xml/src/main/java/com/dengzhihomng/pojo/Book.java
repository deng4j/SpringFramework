package com.dengzhihomng.pojo;

public class Book {
    private String name;
    private String auther;
    private  int age;

    public Book() {
    }

    public Book(String name, String auther, int age) {
        this.name = name;
        this.auther = auther;
        this.age = age;
    }


    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", auther='" + auther + '\'' +
                ", age=" + age +
                '}'+"\r\n";
    }
}
