package com.baidu.yuepingxu.demo.shejimoshi;

/**
 * @author xuyueping
 * @date 2020-03-16
 * @describe 使得子类不改变算法的步骤就可以执行特定的
 */
public class Template {
    public static void main(String[] args) {
        A a = new B();
        a.operation();
    }
}

abstract class A {

    public void operation() {
        System.out.println("step1");
        System.out.println("step2");
        System.out.println("step3");
        template();
    }

    abstract void template();
}

class B extends A {

    @Override
    void template() {
        System.out.println("执行template");
    }
}
