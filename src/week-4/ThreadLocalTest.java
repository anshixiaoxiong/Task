import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
/*
ThreadLocal 变量，线程局部变量，同一个 ThreadLocal 所包含的对象，在不同的 Thread 中有不同的副本,
且其它 Thread 不可访问，那就不存在多线程间共享的问题。
 */

//程序日志打印的简单模拟
//实际执行记录日志的类，每个线程都会拥有一个该类的实例，各实例互不影响，有该线程独享。
class TSLog {

    private PrintWriter writer = null;

    public TSLog(String filename) {
        try {
            writer = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void println(String s) {
//        writer.println("==== Start of log ===");
        writer.println(s);
    }

    public void close() {
        writer.println("==== End of log ====");
        writer.close();
    }

}

class Log {

    private static final ThreadLocal<TSLog> tsLogCollection = new ThreadLocal<>();

    public static void println(String s) {
        getTSLog().println(s);
    }

    public static void close() {
        getTSLog().close();
    }

    private static TSLog getTSLog() {
        TSLog tsLog = tsLogCollection.get();
        if (tsLog == null) {
            tsLog = new TSLog(Thread.currentThread().getName() + "-log.txt");
            tsLogCollection.set(tsLog);
        }
//        System.out.println(tsLog);//打印日志看到三个线程只产生三个实例。
        return tsLog;

    }

    //注意，在使用完时及时清除，避免内存泄漏
    public static void remove() {
        tsLogCollection.remove();
    }

}


class ClientThread extends Thread {

    public ClientThread(String name) {
        super(name);
    }

    public void run() {
        System.out.println(getName() + " BEGIN");
        for (int i = 0; i < 10; i++) {
            Log.println("线程-" + Thread.currentThread().getName() + "-日志信息：i = " + i);
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
            }
        }
        Log.close();
        Log.remove();//使用完及时关闭，避免内存泄漏
        System.out.println(getName() + " END");
    }

}

public class ThreadLocalTest {
    /*
    执行：
    A、B、C三个线程调用Log类的同一个方法，但实际上每个线程都拥有独自的TSLog实例。
     */
    public static void main(String[] args) {
        new ClientThread("A").start();
        new ClientThread("B").start();
        new ClientThread("C").start();
    }

}
