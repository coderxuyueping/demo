package com.baidu.yuepingxu.demo.shejimoshi;

/**
 * @author xuyueping
 * @date 2020-03-16
 * @describe 装饰器模式，对原代码开闭原则下，改进
 */
public class Decorator {
    public static void main(String[] args) {
        IDecorator componet = new Decorator1(new Componet());
        componet.decorator();
    }

    interface IDecorator{
        void decorator();
    }

    static class Componet implements IDecorator{

        @Override
        public void decorator() {
            System.out.println("拍照");
        }
    }

    static class Decorator1 implements IDecorator{
        IDecorator iDecorator;
        public Decorator1(IDecorator iDecorator){
            this.iDecorator = iDecorator;
        }

        @Override
        public void decorator() {
            System.out.println("美颜");
            iDecorator.decorator();
        }
    }

}
