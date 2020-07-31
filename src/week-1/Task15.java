import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task15 {
    private static final Lock lock = new ReentrantLock();
    /*
    验证ReentrantLock重入性，模拟加锁失败导致最终又释放锁导致异常
    结论：ReentrantLock可重入，加锁lock.lock()要写在try代码块外，
         若果写在try内加锁一旦失败，仍会执行finally中的lock.unlock()
         会抛出异常。
     */
    public static void main(String[] args) {
        lockMethod();
//        lockMethodB();
    }

    public static void lockMethod() {
//        lock.lock();
        int a = 10 / 0;//模拟加锁失败
        System.out.println("lockMethod获得了锁");
        try {
//            lock.lock();
            System.out.println("lockMethod锁代码块执行了");
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("lockMethod释放锁");
        }
    }

    public static void lockMethodA() {
        lock.lock();
        System.out.println("lockMethodA获得了锁");
        try {
            System.out.println("lockMethodA锁代码块执行了");
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("lockMethodA释放锁");
        }
    }

    public static void lockMethodB() {
        lock.lock();
        System.out.println("lockMethodB获得了锁");
        try {
            lockMethodA();
            System.out.println("lockMethodB锁代码块执行了");
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("lockMethodB释放锁");
        }
    }
}
