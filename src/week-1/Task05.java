import java.util.concurrent.atomic.AtomicLong;

/*
volatile解决的是多线程共享变量可见性问题，类似synchronized,但不具有synchronized互斥性，
所以对volatile变量操作并非原子性,线程不安全，适用一写多读场景，但多些场景存在线程安全问题。
 */
public class Task05 {
    private static volatile long count = 0L;
    private static final int NUMBER = 10000;
    private static final AtomicLong atomicLong = new AtomicLong(0);


    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < NUMBER; i++) {
                atomicLong.incrementAndGet();
                synchronized (Task05.class) {
                    count--;
                }
            }
        });
        thread.start();

//        Thread subThread = new Thread(new SubThread());
//        subThread.start();

        for (int i = 0; i < NUMBER; i++) {
            atomicLong.decrementAndGet();
            synchronized (Task05.class) {
                count++;
            }
        }
        while (thread.isAlive()) {
            //等待减减线程结束
        }
        System.out.println("count最终结果：" + count);
        System.out.println("atomicLong最终结果：" + atomicLong.get());
    }

    static class SubThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < NUMBER; i++) {
                synchronized (SubThread.class) {
                    count--;
                }

            }
        }
    }
}

