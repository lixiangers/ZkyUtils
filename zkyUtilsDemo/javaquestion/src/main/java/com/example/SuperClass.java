package com.example;

public class SuperClass {
    static {
        System.out.println("父类的静态代码块");
    }

    {
        System.out.println("父类代码块");
    }

    public SuperClass() {
        System.out.println("父类的构造函数");
    }
}
