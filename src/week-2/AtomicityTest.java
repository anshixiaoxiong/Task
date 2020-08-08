import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
——————————————————————————————————————————————————————————————————————————
原子性：一个或多个操作，要么全部执行且在执行过程中不被任何因素打断，要么全部不执行。
JMM来直接保证的原子性变量操作包括read、load、assign、use、store和write，
java中基本数据类型的读取和赋值是具备原子性的，例外就是long和double的非原子性协定，
不过商业JMM基本都做了保证原的子操作，所以基本不会发生。
而在具体开发场景JMM提供了Atomic 类型、lock、unlock和synchronized来保证原子性。
需要特别注意：volatile的单个操作具有原子性，组合操作却不具有原子性！
——————————————————————————————————————————————————————————————————————————
 */

/*
 **************************不进行原子性处理，会出现并发错误********************
 */
public class AtomicityTest {

    static Integer num = 0;

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Thread> list = new ArrayList<>(2000);

        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    num++;
                }
            });
            thread.start();
            list.add(thread);
        }

        for (Thread thread : list) {
            thread.join();
        }
        System.out.println(num);//结果不等于2000，并发出现问题
    }

}

/*
 ****************************** 1、使用Atomic解决原子性 **************************
 */
class Atomicity_Test_1 {

    static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {


        ArrayList<Thread> list = new ArrayList<>(2000);

        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    num.incrementAndGet();
                }
            });
            thread.start();
            list.add(thread);

        }

        for (Thread thread : list) {
            thread.join();
        }

        System.out.println(num);
    }

}

/*
 ****************************** 2、使用synchronized解决原子性问题 ********************
 */
class Atomicity_Test_2 {

    static Integer num = 0;

    public static void main(String[] args) throws InterruptedException {


        ArrayList<Thread> list = new ArrayList<>(2000);

        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (Atomicity_Test_2.class) {
                        num++;
                    }
                }
            });
            thread.start();
            list.add(thread);

        }

        for (Thread thread : list) {
            thread.join();
        }

        System.out.println(num);
    }

}

/*
 ******************************* 3、使用lock解决原子性 ******************************
 */
class Atomicity_Test_3 {

    static Integer num = 0;
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {


        ArrayList<Thread> list = new ArrayList<>(2000);

        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        num++;
                    } finally {
                        lock.unlock();
                    }
                }
            });
            thread.start();
            list.add(thread);

        }

        for (Thread thread : list) {
            thread.join();
        }

        System.out.println(num);
    }

}

/*
 **************************** 4、volatile关键字无法解决组合操作原子性问题 *******************
 */
class Atomicity_Test_4 {

    volatile static Integer num = 0;

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Thread> list = new ArrayList<>(2000);

        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    num++;
                }
            });
            thread.start();
            list.add(thread);
        }

        for (Thread thread : list) {
            thread.join();
        }
        System.out.println(num);//结果并不等于2000，说明volatile不能解决组合操作原子性问题
    }
}