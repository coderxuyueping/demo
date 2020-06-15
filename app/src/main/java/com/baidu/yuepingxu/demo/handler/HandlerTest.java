package com.baidu.yuepingxu.demo.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @author xuyueping
 * @date 2020-03-17
 * @describe
 * 为什么looper.loop不会阻塞主线程，因为查看main源码可以发现，android是由事件驱动的，如果队列里没有消息主线程进入休眠，不会占用cpu资源，
 * 如果有消息到来唤醒，loop不会阻塞，唯一导致anr的就是dispatchMessage里的消息处理过长导致anr
 * 当loop方法执行完了，整个应用的生命周期也结束
 */
public class HandlerTest {
    public static void main(String[] args) {
        // 通过这个创建一个looper对象，把looper对象放到一个threadLocal中
        // threadLocal的作用是在每一个线程创建一个副本
        // threadLocal 里面维护了一个ThreadLocalMap，ThreadLocalMap里面有一个Entry key是threadLocal，value是object，也就是Looper对象
        // 虽然key是弱引用，但是因为value不是，所以还是会有内存泄露，记得不用了remove
        // 在第一次set的时候，ThreadLocalMap需要create  thread.threadLocals = new ThreadLocalMap(this, firstValue);
        // 每一个thread里有一个ThreadLocalMap，在之后的set中取出当前thread的map进行set，key为threadLocal，value为looper
        // new Looper的时候会创建一个MessageQueue
        Looper.prepare();


        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        // 会调用myLooper获取threadLocal里的looper对象，也就是每一个线程都有属于自己的looper
        // 开启不停止的循环 Message msg = queue.next();取出消息队列里的下一个message
        // 找到message后通过msg.target.dispatchMessage(msg);发送消息到handler  每一个message里有一个target，它就是handler对象
        // 主线程在main函数里开启里looper，死循环为什么不会阻塞？因为看源码有一句，如果停止里消息机制就是抛异常
        // dispatchMessage在handler中有三种处理方式：第一种通过message的callback处理，就是一个Runnable
        // 第二种通过handler设置的一个callback接口处理
        // 第三种就是通过handler里的一个空方法handleMessage来处理
        // 在主线程创建handler和开启loop，在子线程发送消息的时候，因为dispatchMessage是在looper线程执行的，所以是在主线程处理消息
        Looper.loop();

        // MessageQueue里面有一个message对列，其实就是一个链表结构，按照时间先后插入
        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
    }
}
