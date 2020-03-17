package com.baidu.yuepingxu.demo.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuyueping
 * @date 2020-03-10
 * @describe
 * synchronized 锁住的是对象，对象在内存中是有一个对象头的，当一个线程第一次进入一个无锁状态的对象时，会把自己的threadid记录在对象，
 * 此时是偏向锁，当另外一个线程竞争时转为轻量锁，当竞争的线程超过cpu核数为重量锁
 * 在字节码中  mintorenter       mintorexit
 *
 *
 * new出thread后进入新生状态，调用start后进入就绪状态，得到cpu调度后进入运行状态，在运行过程中可以进入阻赛状态，执行完进入死亡
 *
 * yield方法会让线程进入就绪状态
 * thread.Join把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行的线程。
 * 比如在线程B中调用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。
 *
 *
 * volatile只有可见性和有序性（禁止指令重排),不具备原子性
 * synchronized三个都具备
 * atomic保证可见和原子性，他使用了volatile和acs算法
 * CAS也可以保证原子性，该算法是先从主存中读出值为内存值V，然后计算后得到更新值B，此时再从内存中读一次值A，如果V=A就更新值到主存，否则什么都不干
 */
public class Test {
    private static char[] c1 = "ABCDEFG".toCharArray();
    private static char[] c2 = "1234567".toCharArray();
    static final Object o = new Object();
    static Thread t2 = null;

    public static void main(String[] args){
        // 两个线程打印A1B2C3D  线程通信
        demon();
    }

    private static void method1(){
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o){
                    for(char c : c1){
                        System.out.println(c);
                        latch.countDown(); // 减1，当传给CountDownLatch的值为0的时候才会继续执行
                        o.notify(); // 叫醒线程
                        try {
                            o.wait(); // 让出锁，sleep不让出
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    o.notify(); // 这个不执行t1会一直wait，程序一直执行
                }
            }
        },"t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await(); // 为了保证t1先执行，t2一上来就wait
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o){
                    for(char c : c2){
                        System.out.println(c);
                        o.notify();
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    o.notify();
                }
            }
        },"t2").start();
    }

    private static void method2(){
        // 使用LockSupport
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(char c : c1){
                    System.out.print(c);
                    LockSupport.unpark(t2); // 叫醒t2
                    LockSupport.park(); // 阻塞
                }
            }
        },"t1");

        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(char c : c2){
                    LockSupport.park(); // 阻塞
                    System.out.print(c);
                    LockSupport.unpark(t1); // 叫醒t1
                }
            }
        },"t2");
        t1.start();
        t2.start();
    }

    private static void method3(){
        // 和synchronized的区别是，他可以叫醒指定线程
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition1 = lock.newCondition();
        final Condition condition2 = lock.newCondition();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    lock.lock(); // 类似monitorenter
                    for(char c : c1){
                        System.out.print(c);
                        condition2.signal();
                        condition1.await();
                    }
                    condition2.signal();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock(); // monitorexit
                }

            }
        },"t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    lock.lock();
                    for (char c : c2){
                        System.out.print(c);
                        condition1.signal();
                        condition2.await();
                    }
                    condition1.signal();
                }catch (Exception e){

                }finally {
                    lock.unlock();
                }
            }
        },"t2").start();
    }

    private static void demon(){
        // 守护线程 虚拟机不需要等待守护线程执行就可以停止
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("我是守护线程");
                }
            }
        });
        t.setDaemon(true);
        t.start();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++){
                    System.out.println("我是用户线程");
                }
            }
        });
        t1.start();
    }

}
