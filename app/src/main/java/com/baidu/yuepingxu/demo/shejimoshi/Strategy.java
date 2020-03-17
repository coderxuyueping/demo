package com.baidu.yuepingxu.demo.shejimoshi;

/**
 * @author xuyueping
 * @date 2020-03-16
 * @describe 策略模式 定义不同的策略传进去，不需要改变就可以替换
 */
public class Strategy {
    public static void main(String[] args) {
        Call call = new Call();
        call.call();

        call.setCall(new PigCall());
        call.call();
    }
}

interface ICall{
    void call();
}

class NormalCall implements ICall{

    @Override
    public void call() {
        System.out.println("旺旺");
    }
}

class PigCall implements ICall{

    @Override
    public void call() {
        System.out.println("猪叫");
    }
}

abstract class AbsCall implements ICall{
    ICall iCall;
    public AbsCall(ICall iCall){
        this.iCall = iCall;
    }
}

class Call extends AbsCall{

    public Call(){
        super(new NormalCall());
    }

    public Call(ICall iCall){
        super(iCall);
    }

    @Override
    public void call() {
        iCall.call();
    }

    public void setCall(ICall iCall){
        this.iCall = iCall;
    }
}
