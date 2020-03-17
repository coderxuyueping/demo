package com.baidu.yuepingxu.demo.shejimoshi;

/**
 * @author xuyueping
 * @date 2020-03-16
 * @describe 适配器模式，把一个接口或者对象转为另一个解决兼容问题， 比如220v高压适配后变成5v
 * 对象适配器和类适配器
 */
public class Adapter {
    public static void main(String[] args) {
        MyInterface myInterface = new MyAdapter(new A());
    }

    interface MyInterface{
        int getData();
    }

    static class A {
        public int output() {
            return 220;
        }
    }

    // 对象适配器模式
    static class MyAdapter implements MyInterface{
        A a;

        public MyAdapter(A a){
            this.a = a;
        }


        @Override
        public int getData() {
            //... doBefore
            int data = a.output();
            //... doAfter
            return 5;
        }
    }

    static class MyAdapter1 extends A implements MyInterface{
        @Override
        public int getData() {
            int data = output();
            return 5;
        }
    }
}
