import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 ———————————————————————————————————————————————————————————————————————————————
 * CAS是Compare And Set的一个简称，如下理解：
 * 1，已知当前内存里面的值current和预期要修改成的值new传入
 * 2，内存中AtomicInteger对象地址对应的真实值(因为有可能别修改)real与current对比，
 *    相等表示real未被修改过，是“安全”的，将new赋给real结束然后返回；
 *    不相等说明real已经被修改，结束并重新执行直到修改成功
 *
 * CAS相比Synchronized，避免了锁的使用，总体性能比Synchronized高很多.
 * compareAndSet典型使用为计数，如i++,++i,这里以i++为例：
 ————————————————————————————————————————————————————————————————————————————————
 */
public class CASTest {

}

class Counter {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final Counter counter = new Counter();
        List<Thread> threads = new ArrayList<>(100);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        counter.count();
                        counter.safeCount();
                    }
                }
            });
            threads.add(t);
        }
        for (Thread thread : threads) {
            thread.start();
        }
// 等待所有线程执行完成
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(counter.i);
        System.out.println(counter.atomicInteger.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 使用CAS实现线程安全 i++
     */
    private void safeCount() {
        for (; ; ) {
            int i = atomicInteger.get();
            boolean b = atomicInteger.compareAndSet(i, ++i);
            if (b) {
                break;
            }
        }
    }

    /**
     * 非线程安全 i++
     */
    private void count() {
        i++;
    }
}

