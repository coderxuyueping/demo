package com.baidu.yuepingxu.demo.shejimoshi;

/**
 * @author xuyueping
 * @date 2020-03-16
 * @describe 工厂模式 把相同稳定的抽成接口，类似于客户想要买车，交给工厂去生产，工厂有一个平台可以生产A和B两种车
 */
public class FactoryTest {
    public static void main(String[] args) {
        Product product = CarFactory.buyCar(2);
        product.productCar();
    }

    interface Product {
        void productCar();
    }

    public static class CarA implements Product {

        @Override
        public void productCar() {
            System.out.print("生产出A车");
        }
    }

    public static class CarB implements Product {

        @Override
        public void productCar() {
            System.out.print("生产出B车");
        }
    }

    public static class CarFactory {
        public static Product buyCar(int type) {
            if (type == 1) {
                return new CarA();
            } else if (type == 2) {
                return new CarB();
            } else {
                return null;
            }
        }
    }

}
