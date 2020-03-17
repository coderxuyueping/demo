package com.baidu.yuepingxu.demo.thread;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuyueping
 * @date 2020-03-17
 * @describe
 *
 * 1、线程池的优势
 * （1）、降低系统资源消耗，通过重用已存在的线程，降低线程创建和销毁造成的消耗；
 * （2）、提高系统响应速度，当有任务到达时，通过复用已存在的线程，无需等待新线程的创建便能立即执行；
 * （3）方便线程并发数的管控。因为线程若是无限制的创建，可能会导致内存占用过多而产生OOM，并且会造成cpu过度切换
 *     （cpu切换线程是有时间成本的（需要保持当前执行线程的现场，并恢复要执行线程的现场））。
 * （4）提供更强大的功能，延时定时线程池。
 *
 *
 *
 * 2、线程池流程
 *  提交任务--->核心线程是否满了？没有满创建新线程执行任务-->核心线程池满了判断阻塞队列是否满了-->没有满添加到队列，满了判断最大线程池是否满了
 *  -->没有满创建线程执行，否则执行拒绝策略
 *
 *
 *  3、线程池为什么要使用阻塞队列而不使用非阻塞队列？
 * 阻塞队列可以保证任务队列中没有任务时阻塞获取任务的线程，使得线程进入wait状态，释放cpu资源。
 * 当队列中有任务时才唤醒对应线程从队列中取出消息进行执行，使得在线程不至于一直占用cpu资源。
 * （线程执行完任务后通过循环再次从任务队列中取出任务进行执行，代码片段如下
 * while (task != null || (task = getTask()) != null) {}）。
 *
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        // 工作队列
        LinkedBlockingQueue blockingQueue = new LinkedBlockingQueue(100);
        // 线程创建工厂
        ThreadFactory threadFactory = new ThreadFactory() {
            // 原子类cas
            AtomicInteger atomicInteger = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("name" + atomicInteger.getAndIncrement());
                return null;
            }
        };

        // 第一个参数是核心线程数，一般是cpu个数+1
        // 第二个是最大线程池大小
        // 第三个是空闲线程存活时间，第四个是时间单位
        // 第五个是阻塞队列
        // 第六个是线程创建工厂
        // 第七个是拒绝策略，在最大线程池满了并且阻塞队列也满了再添加任务进来会执行拒绝策略
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 100, 100,
                TimeUnit.SECONDS, blockingQueue, threadFactory, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
    }
}
