package com.xadaptersimple;


/**
 * by y on 2016/11/15
 */

public class MainBean {

    private String name;

    public MainBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int age;

}
