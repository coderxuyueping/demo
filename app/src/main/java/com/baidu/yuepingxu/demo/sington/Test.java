package com.baidu.yuepingxu.demo.sington;

/**
 * @author xuyueping
 * @date 2020-03-16
 * @describe
 */
public class Test {

    // 饿汉模式 利用类的加载机制保证线程安全，jvm在多线程下只有一个线程会拿到实例，但是会造成空间的浪费，不是懒加载
    public static class T1{
        public static T1 instance = new T1();

        private T1(){

        }

        public static T1 getInstance(){
            return instance;
        }
    }

    // 饱汉模式，懒加载，但是因为不是原子操作所以不是线程安全
    public static class T2{
        public static T2 instance = null;

        private T2(){

        }

        public static T2 getInstance(){
            if(instance == null){
                instance = new T2();
            }
            return instance;
        }
    }

    // 饱汉模式线程安全版 直接加synchronized，但是这样锁的粒度太大了，效率低
    public static class T3{
        public static T3 instance = null;

        private T3(){

        }

        public synchronized static T3 getInstance(){
            if(instance == null){
                instance = new T3();
            }
            return instance;
        }
    }

    // 饱汉模式线程改进版
    public static class T4{
        public static volatile T4 instance = null;

        private T4(){

        }

        public static T4 getInstance(){
            if(instance == null){
                synchronized (T4.class){
                    // 如果不再加一个判断，会因为对象的创建是
                    // 1.分配内存   2.初始化   3.赋值
                    // 由于jvm  cpu会优化指令重排，所以可能3和2换位置（不影响结果） 在外层空判断出问题，直接返回一个没有初始化的instance
                    // 可以加volatile禁止指令重排，这个不能保证原子性
                    if(instance == null){
                        instance = new T4();
                    }
                }

            }
            return instance;
        }
    }

    // 静态内部类   利用类的加载记机制，并且是懒加载
    public static class T5{
        private T5(){

        }

        public static T5 getInstance(){
            return Holder.instance;
        }

        public static class Holder{
            private static final T5 instance = new T5();
        }
    }


}
