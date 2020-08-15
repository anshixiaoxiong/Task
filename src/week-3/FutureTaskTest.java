import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("haha");

            }
        });
//        匿名内部类
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("嘿嘿");
            }
        };
        new Thread(runnable).start();

    }
}

class MyCallable implements Callable<String> {
    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(new MyCallable());
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
//   futureTask.get()方法的调用会阻塞主线程
            System.out.println("返回值：" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("main线程执行结束");

    }

    @Override
    public String call() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("mycallable is running....." + i);
        }
        return "Mycallable线程执行完成了";
    }
}
