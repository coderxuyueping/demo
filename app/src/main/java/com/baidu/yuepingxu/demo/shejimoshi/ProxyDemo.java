package com.baidu.yuepingxu.demo.shejimoshi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xuyueping
 * @date 2020/6/15
 * @describe
 */
public class ProxyDemo {
    public static void main(String[] args) {
        proxy();
    }

    private static void staticProxy() {
        // 静态代理
        // 具体实现类
        final IEat eat = new Eat();
        // 代理类
        IEat proxy = new IEat() {
            @Override
            public void eat() {
                eat.eat();
            }
        };

        proxy.eat();
    }

    // 动态代理
    private static void proxy(){
        final IEat eat = new Eat();
        IEat eatProxy = (IEat) Proxy.newProxyInstance(eat.getClass().getClassLoader(), eat.getClass().getInterfaces()
                , new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(eat, args);
                    }
                });

        eatProxy.eat();
    }
}

interface IEat {
    void eat();
}

class Eat implements IEat {

    @Override
    public void eat() {
        System.out.println("具体执行的类，被代理类");
    }
}
