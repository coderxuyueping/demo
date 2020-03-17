package com.baidu.yuepingxu.demo.shejimoshi;

/**
 * @author xuyueping
 * @date 2020-03-16
 * @describe 抽象工厂模式
 *
 *
 * 单一职责原则：一个类只做和自己相关的业务
 * 迪米特原则（最少知识原则）：一个类和其他的类耦合越小越好
 * 开闭原则：对扩展开放，对修改关闭
 * 里氏替换原则：所有子类出现的地方都可以用父类替换
 * 接口隔离原则：不要所有的功能都放到一个接口，比如clickLister要区分是点击还是长按，不要放到一个接口
 * 依赖倒置原则：高层模块不要依赖底层模块，高层和底层应该通过抽象来依赖，具体实现依赖抽象，抽象不依赖具体实现
 */
public class AbstractFactory {
    public static void main(String[] args) {
        IDataUtils dataUtils = new OricelDataUtils(); // new SqlDataUtils() 要替换只需改这一行，代码改动少，符合开闭原则
        IConnect connect = dataUtils.getConnect();
        ICommand command = dataUtils.getCommand();
        connect.connect();
        command.command();
    }


    interface IConnect{
        void connect();
    }

    interface ICommand{
        void command();
    }

    interface IDataUtils{
        ICommand getCommand();
        IConnect getConnect();
    }

    static class SqlConnect implements IConnect{

        @Override
        public void connect() {
            System.out.println("SqlConnect");
        }
    }

    static class SqlCommand implements ICommand{

        @Override
        public void command() {
            System.out.println("SqlCommand");
        }
    }

    static class SqlDataUtils implements IDataUtils{

        @Override
        public ICommand getCommand() {
            return new SqlCommand();
        }

        @Override
        public IConnect getConnect() {
            return new SqlConnect();
        }
    }




    static class OricelConnect implements IConnect{

        @Override
        public void connect() {
            System.out.println("OricelConnect");
        }
    }

    static class OricelCommand implements ICommand{

        @Override
        public void command() {
            System.out.println("OricelCommand");
        }
    }

    static class OricelDataUtils implements IDataUtils{

        @Override
        public ICommand getCommand() {
            return new OricelCommand();
        }

        @Override
        public IConnect getConnect() {
            return new OricelConnect();
        }
    }
}
