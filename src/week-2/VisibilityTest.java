import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
——————————————————————————————————————————————————————————————————————————————————
可见性：一个线程对共享变量的修改，另一个线程能够立刻看到修改
JMM是通过在变量修改后将新值同步回主内存，在变量读取前从主内存刷新变量值的方式来实现可见性的，
方式有volatile、synchronized、lock、unlock,Atomic类型，volatile和锁具有相同的的内存语义
——————————————————————————————————————————————————————————————————————————————————
 */

/*
****************************不进行可见性处理****************************************
此示例不进行可见性处理，线程会陷入while死循环，
说明读到的值始终是线程栈中的值，并没有从主存刷新isRunning变量的值
 */
public class VisibilityTest {
    static Boolean isRunning = false;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入run方法");
                while (!isRunning) {
//                    不进行可见性处理，主线程更新的isRUnning不可见，线程死循环
                }
                System.out.println("线程执行结束");
            }
        });
        thread.start();
        Thread.sleep(500);
        isRunning = true;
    }
}

/*
 *************************** 1、volatile方式解决可见性问题***************************
 */
class Visibility_Test_1 {
    volatile static Boolean isRunning = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入run方法");
                while (!isRunning) {
                }
                System.out.println("线程执行结束");
            }
        }).start();
        Thread.sleep(500);
        isRunning = true;
    }
}

/*
 *************************** 2、使用synchronized解决可见性问题***********************
 */
class Visibility_Test_2 {
    static Boolean isRunning = false;

    public static synchronized Boolean getRunning() {
        return isRunning;
    }

    public static synchronized void setRunning() {
        isRunning = true;
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入run方法");
                while (!getRunning()) {

                }
                System.out.println("线程执行结束");
            }
        }).start();

        Thread.sleep(500);
        setRunning();
    }
}

/*
 **************************** 3、使用lock解决可见性问题******************************
 */
class Visibility_Test_3 {
    static Lock lock = new ReentrantLock();
    static Boolean isRunning = false;

    public static Boolean getRunning() {
        lock.lock();
        try {
            return isRunning;
        } finally {
            lock.unlock();
        }
    }

    public static void setRunning() {
        lock.lock();
        try {
            isRunning = true;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入run方法");
                while (!getRunning()) {

                }
                System.out.println("线程执行结束");
            }
        }).start();

        Thread.sleep(500);
        setRunning();
    }
}

/*
 ***************************** 4、使用Atomic类解决可见性问题*************************
 */
class Visibility_Test_4 {
    static AtomicBoolean isRunning = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程进入run方法");
                while (!isRunning.get()) {
                }
                System.out.println("线程执行完成");
            }
        }).start();

        Thread.sleep(500);
        isRunning.set(true);
    }
}