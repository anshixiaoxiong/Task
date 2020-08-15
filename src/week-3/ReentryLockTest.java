import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ————————————————————————————————————————————————————————————————————————————————————————————————
 * ReentrantLock是可重入的独占锁。比起synchronized功能更加丰富，支持公平锁实现，支持中断响应以及限时等待等等。
 * 可以配合一个或多个Condition条件方便的实现等待通知机制。
 * ————————————————————————————————————————————————————————————————————————————————————————————————
 */
public class ReentryLockTest {

}

/************************** 1、ReentrantLock是独占锁且可重入的*************************************
 ReentrantLock和synchronized都是可重入的。
 synchronized因为可重入因此可以放在被递归执行的方法上,且不用担心线程最后能否正确释放锁；
 而ReentrantLock在重入时要却确保重复获取锁的次数必须和重复释放锁的次数一样，否则可能导致其他线程无法获得该锁。

 */
class ReentrantLock_Test_1 {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        for (int i = 1; i <= 3; i++) {
            lock.lock();
        }

        for (int i = 1; i <= 3; i++) {
            try {

            } finally {
                lock.unlock();
            }
        }
    }
}

/********************************* 2、ReentrantLock可以实现公平锁**********************************
 公平锁是指当锁可用时,在锁上等待时间最长的线程将获得锁的使用权。
 */
class ReentrantLock_Test_2 {

    //    static Lock lock = new ReentrantLock(true);
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5; i++) {
            new Thread(new ThreadDemo(i)).start();
        }

    }

    static class ThreadDemo implements Runnable {
        Integer id;

        public ThreadDemo(Integer id) {
            this.id = id;
        }

        @Override

        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 2; i++) {
                lock.lock();
                System.out.println("获得锁的线程：" + id);
                lock.unlock();
            }
        }
    }
}

/********************************* 3、ReentrantLock可响应中断 *********************************************
 当使用synchronized实现锁时,阻塞在锁上的线程除非获得锁否则将一直等待下去，也就是说这种无限等待获取锁的行为无法被中断。
 而ReentrantLock给我们提供了一个可以响应中断的获取锁的方法lockInterruptibly()。该方法可以用来解决死锁问题。
 */
class ReentrantLock_Test_3 {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new ThreadDemo(lock1, lock2));//该线程先获取锁1,再获取锁2
        Thread thread2 = new Thread(new ThreadDemo(lock2, lock1));//该线程先获取锁2,再获取锁1
        thread1.setName("线程1 ");
        thread2.setName("线程2 ");
        thread1.start();
        thread2.start();
        thread1.interrupt();//是第一个线程中断
    }

    static class ThreadDemo implements Runnable {
        Lock firstLock;
        Lock secondLock;

        public ThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            try {
                firstLock.lockInterruptibly();
                TimeUnit.MILLISECONDS.sleep(10);//更好的触发死锁
                secondLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName() + "正常结束!");
            }
        }
    }
}

/********************************* 4、获取锁时限时等待示例1 **************************************************
 ReentrantLock还给我们提供了获取锁限时等待的方法tryLock(),可以选择传入时间参数,表示等待指定的时间,无参则表示立即
 返回锁申请的结果:true表示获取锁成功,false表示获取锁失败。我们可以使用该方法配合失败重试机制来更好的解决死锁问题。
 */
class ReentrantLock_Test_4 {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new ThreadDemo(lock1, lock2));//该线程先获取锁1,再获取锁2
        Thread thread2 = new Thread(new ThreadDemo(lock2, lock1));//该线程先获取锁2,再获取锁1
        thread1.setName("线程1 ");
        thread2.setName("线程2 ");
        thread1.start();
        thread2.start();
    }

    static class ThreadDemo implements Runnable {
        Lock firstLock;
        Lock secondLock;

        public ThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            try {
//                失败重试
                while (!firstLock.tryLock()) {
                    TimeUnit.MILLISECONDS.sleep(10);
                }
                while (!secondLock.tryLock()) {
                    firstLock.unlock();
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (((ReentrantLock) firstLock).isHeldByCurrentThread()) {
                    firstLock.unlock();
                }
                if (((ReentrantLock) secondLock).isHeldByCurrentThread()) {
                    secondLock.unlock();
                }
                System.out.println(Thread.currentThread().getName() + "正常结束!");
            }
        }
    }
}
/********************************* 4、获取锁时限时等待示例2 **************************************************
 ReentrantLock还给我们提供了获取锁限时等待的方法tryLock(),可以选择传入时间参数,表示等待指定的时间,无参则表示立即
 返回锁申请的结果:true表示获取锁成功,false表示获取锁失败。我们可以使用该方法配合失败重试机制来更好的解决死锁问题。
 */
class Time_Lock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + "  get lock succed");
                Thread.sleep(2000);
            } else {
                System.out.println(Thread.currentThread().getName() + "  get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "   unlock ");
            }
        }
    }

    public static void main(String[] args) {
        Time_Lock timeLock = new Time_Lock();
        Thread t1 = new Thread(timeLock);
        Thread t2 = new Thread(timeLock);
        t1.setName("thread-t1");
        t2.setName("thread-t2");
        t1.start();
        t2.start();
    }
}