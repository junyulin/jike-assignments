package homework;

import java.util.function.Supplier;

/**
 * 第四周作业
 * 使用函数式编程，将方法通过函数式接口传到自定义的线程类中，计算后再赋值给线程类中的自定义属性
 *
 * @author LinJn
 * @since 2021/5/30 23:14
 */
public class Homework5 {

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        // 使用函数式编程
        Supplier<Integer> supplier = Homework5::sum;
        MyThread<Integer> thread = new MyThread<>(supplier);
        thread.start();
        thread.join();
        System.out.println("异步计算结果为："+ thread.result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }

    private static class MyThread<T> extends Thread {

        T result;

        final Supplier<T> supplier;

        public MyThread(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public void run() {
            this.result = supplier.get();
        }

    }

}
