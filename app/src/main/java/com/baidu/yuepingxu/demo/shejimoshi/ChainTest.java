package com.baidu.yuepingxu.demo.shejimoshi;

/**
 * @author xuyueping
 * @date 2020-03-16
 * @describe 责任链模式
 */
public class ChainTest {
    public static void main(String[] args) {
        Test test = new Test();
        test.test(new Handler1(new Handler2(null)));
    }
}

class Test{
    void test(Handler handler){
        handler.process();
    }
}

interface Handler {
    boolean process();
}

abstract class AbsHandler implements Handler {
    Handler next;

    public AbsHandler(Handler next) {
        this.next = next;
    }
}

class Handler1 extends AbsHandler {

    public Handler1(Handler next) {
        super(next);
    }

    @Override
    public boolean process() {
        System.out.println("处理第一个拦截");
        if (next != null) {
            // 交给下一个拦截链处理
            if (next.process()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}

class Handler2 extends AbsHandler {

    public Handler2(Handler next) {
        super(next);
    }

    @Override
    public boolean process() {
        System.out.println("处理第二个拦截");
        if (next != null) {
            // 交给下一个拦截链处理
            if (next.process()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
