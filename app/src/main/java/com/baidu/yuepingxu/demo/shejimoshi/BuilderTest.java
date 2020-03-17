package com.baidu.yuepingxu.demo.shejimoshi;

/**
 * @author xuyueping
 * @date 2020-03-16
 * @describe 建造者模式
 */
public class BuilderTest {
    public static void main(String[] args) {
        Product product = new Product.Builder()
                .setValue1(1)
                .setValue2(2)
                .setValue3(3)
                .setValue4(4)
                .build();
        product.print();
    }

    static class Product {
        int value1;
        int value2;
        int value3;
        int value4;

        public void print() {
            System.out.print("" + value1 + value2 + value3 + value4);
        }


        static class Builder {
            int value1;
            int value2;
            int value3;
            int value4;

            Builder setValue1(int value1) {
                this.value1 = value1;
                return this;
            }

            Builder setValue2(int value2) {
                this.value2 = value2;
                return this;
            }

            Builder setValue3(int value3) {
                this.value3 = value3;
                return this;
            }

            Builder setValue4(int value4) {
                this.value4 = value4;
                return this;
            }

            Product build() {
                Product product = new Product();
                product.value1 = this.value1;
                product.value2 = this.value2;
                product.value3 = this.value3;
                product.value4 = this.value4;
                return product;
            }
        }

    }
}
