package com.example;

/**
 * 静态代码块的测试
 */
public class StaticCodeBlockClass extends SuperClass {
    public static int b = 1;

    static {
        a = 6;
        b = 2;
        System.out.println("子类静态代码块");
    }

    public static int a = 9;

    {
        System.out.println("子类代码块");
    }

    public StaticCodeBlockClass() {
        System.out.println("子类构造函数");
    }
}
