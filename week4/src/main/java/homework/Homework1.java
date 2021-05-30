package homework;

/**
 * 第四周作业
 * 使用一个数组或者一个类去线程里接住这个返回值
 *
 * @author LinJn
 * @since 2021/5/30 23:14
 */
public class Homework1 {

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        // 使用一个数组到线程中接住返回值，使用 list 和 自定义对象 去接住是同样的道理，这里不重复演示
        int[] ret = new int[1];
        Runnable runnable = () -> ret[0] = sum();
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
        System.out.println("异步计算结果为：" + ret[0]);

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
