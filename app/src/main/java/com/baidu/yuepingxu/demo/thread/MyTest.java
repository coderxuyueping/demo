package com.baidu.yuepingxu.demo.thread;

/**
 * @author xuyueping
 * @date 2020-03-26
 * @describe 两个线程打印奇偶数，从小到大
 */
public class MyTest {
    public static void main(String[] args) {
//        Data data = new Data();
//        new Thread(new OddRunable(data)).start();
//        new Thread(new EvenRunable(data)).start();
        Link link = new Link(5,new Link(4, new Link(3, null)));
        int num = getIntFromLink(link);
    }

    // 5-4-3      543
    public static int getIntFromLink(Link node){
        if(node == null || node.next == null){
            return 0;
        }
        node.value = node.value*10;
        int link = getIntFromLink(node.next);
        return link;
    }

}


class Link {
    int value;
    Link next;
    public Link(int value, Link next){
        this.value = value;
                this.next = next;
    }
}

//奇数
class OddRunable implements Runnable {
    Data data;

    OddRunable(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        synchronized (data) {
            while (data.value <= 100) {
                if (data.isOdd) {
                    System.out.println("" + data.value);
                    data.value++;
                    data.isOdd = !data.isOdd;
                    data.notify();
                }
                try {
                    data.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            data.notify();

        }
    }
}

// 偶数
class EvenRunable implements Runnable {
    Data data;

    EvenRunable(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        synchronized (data) {
            while (data.value <= 100) {
                if (!data.isOdd) {
                    System.out.println("" + data.value);
                    data.value++;
                    data.isOdd = !data.isOdd;
                    data.notify();
                }
                try {
                    data.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            data.notify();
        }
    }
}

class Data {
    int value = 1;
    boolean isOdd = true;
}
