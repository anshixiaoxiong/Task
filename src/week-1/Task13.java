/*
    验证synchronized的可重入性，
    验证类锁、对象锁的区别
    类锁，因为所有不同实例对象是同一个类的实例，所以不同线程能拿到的肯定是同一把锁
    对象锁，因为一个类可产生多个对象实例，所以必须保证是同一个实例对象作为索
    结论：不同线程拿到的必须是同一把锁才能实现同步
 */
public class Task13 {
    static long count = 0L;
    static final int NUMBER = 10000;

    public static void main(String[] args) {

        SynLock.synMethod_B();//静态同步方法可重入
        SynLock synLock = new SynLock();
        synLock.synMethod_B1();//非静态同步方法可重入
        synLock.synMethod_B2();//同步代码块可重入

        Thread thread1 = new Thread(() -> {
            SynLock synLock1 = new SynLock();  //生成不同实例对象作为对象锁
            for (int i = 0; i < NUMBER; i++) {

//                synchronized (synLock1)      //不同线程用同一类的不同实例对象作为自己对象锁，不能实现同步
//                synchronized (synLock)       //不同线程用同一类的相同实例对象作为自己对象锁，能实现同步
                synchronized (SynLock.class)   //各个线程用类锁，能实现同步
                {
                    count++;
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            SynLock synLock2 = new SynLock();  //生成不同实例对象作为对象锁
            for (int i = 0; i < NUMBER; i++) {
//                synchronized (synLock2)
//                synchronized (synLock)
                synchronized (SynLock.class)
                {
                    count--;
                }
            }
        });
        thread2.start();
        while (thread1.isAlive() || thread2.isAlive()) {
            //  等待两个线程都结束...
        }
        System.out.println("count最终结果：" + count);

    }
}

class SynLock {

    public static synchronized void synMethod_A() {
        System.out.println("静态同步方法--A--执行了..");
    }

    public static synchronized void synMethod_B() {
        synMethod_A();
        System.out.println("静态同步方法--B--执行了..");
    }

    public synchronized void synMethod_A1() {
        System.out.println("非静态同步方法--A1--执行了..");
    }

    public synchronized void synMethod_B1() {
        synMethod_A1();
        System.out.println("非静态同步方法--B1--执行了..");
    }

    public void synMethod_A2() {
        synchronized (this) {
            System.out.println("同步代码块--A2--执行了..");
        }
    }

    public void synMethod_B2() {
        synchronized (this) {
            synMethod_A2();
            System.out.println("同步代码块--B2--执行了..");
        }
    }


}
