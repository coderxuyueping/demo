package com.baidu.yuepingxu.demo.shejimoshi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuyueping
 * @date 2020-03-16
 * @describe 观察者模式
 */
public class ObserverTest {
    public static void main(String[] args) {
        Obserble obserble = new Obserble();
        obserble.test();
    }
}

// 容器
class Suject{
    List<IObserver> list = new ArrayList<>();

    public void add(IObserver observer){
        if(!list.contains(observer)){
            list.add(observer);
        }
    }

    public void remover(IObserver observer){
        if(list.contains(observer)){
            list.remove(observer);
        }
    }

    public void notifyAllObserver(){
        for(IObserver observer : list){
            observer.update();
        }
    }
}

// 被观察者
interface IObserver{
    void update();
}

class Observer implements IObserver{

    @Override
    public void update() {
        System.out.println("update");
    }
}

// 观察者
class Obserble{
    public void test(){
        Suject suject = new Suject();
        suject.add(new Observer());
        suject.notifyAllObserver();
    }
}
