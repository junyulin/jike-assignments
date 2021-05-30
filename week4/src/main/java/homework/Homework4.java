package homework;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 第四周作业
 * 使用线程池的 invokeAny 方法
 *
 * @author LinJn
 * @since 2021/5/30 23:14
 */
public class Homework4 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start=System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        // 使用线程池的 invokeAny 方法
        Callable<Integer> callable = Homework4::sum;
        Integer result = executorService.invokeAny(Collections.singleton(callable));
        executorService.shutdown();
        System.out.println("异步计算结果为："+ result);
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

}
