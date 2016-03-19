package com.example;

public class TestStaticCodeBlockAndConstructor {
    public static void main(String[] args) {
        System.out.println("--------应用开始启动-----------");
        NormalClass normalClass = new NormalClass();
        System.out.println("--------第一次使用-----------");
        //第一次是使用StaticCodeBlockClass,要进行类的准备阶段，进行类变量分配内存。然后进行初始化阶段(执行类的静态代码块)
        StaticCodeBlockClass staticCodeBlockClass1 = new StaticCodeBlockClass();

        System.out.println("a="+StaticCodeBlockClass.a);
        System.out.println("b="+StaticCodeBlockClass.b);

        System.out.println("--------第二次使用-----------");
        //第二次执行就不需要进行类的准备阶段和类初始化阶段 直接进行实例的初始化。
        StaticCodeBlockClass staticCodeBlockClass2 = new StaticCodeBlockClass();
    }
}
