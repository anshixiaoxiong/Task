import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
—————————————————————————————————————————————————————————————————————————————————————————————————
有序性：程序执行的顺序按照代码的先后顺序执行（理想的内存一致性模型，JMM具体实现并不是严格的一致性）。
Java语言提供了volatile、synchronized和lock、unlock来保证线程之间操作的有序性，
volatile关键字本身就包含了禁止指令重排序的语义，而synchronized则是由“一个变量在同一个时刻
只允许一条线程对其进行lock操作”这条规则获得的，这条规则决定了持有同一个锁的两个同
步块只能串行地进入。
——————————————————————————————————————————————————————————————————————————————————————————————————
 */
public class OrderingTest {
}

/*
 ******************************** 1、使用volatile 的禁止指令重排序示例1 *****************************
 */
class Ordering_Test_1 {

    private static volatile Ordering_Test_1 instance = null;
    // 如果不使用volatile，就不能保证得到的实例是已经正确初始化完成的
//    private static  Ordering_Test_1 instance = null;

    //private  避免类在外部被实例化
    private Ordering_Test_1() {
    }

    //    双重校验锁
    public static Ordering_Test_1 getInstance() {
        if (instance == null) {
            synchronized (Ordering_Test_1.class) {
                if (instance == null) {
                    // 在此处volatile的禁止指令重排序作用保证了实例的初始化完成先于将对象地址写到instance字段。
                    instance = new Ordering_Test_1();
                }
            }
        }
        return instance;
    }
}

/*
 ******************************** 2、使用volatile禁止指令重排序示例2 ***********************************
 */
class Ordering_Test_2 {

    private HashMap configOptions;
    private char[] configText;

    //此变量必须定义为volatile
    volatile boolean initialized = false;

    //假设以下代码在线程A中执行
//模拟读取配置信息，当读取完成后将initialized设置为true以通知其他线程配置可用
    public void init() {
        configOptions = new HashMap<>();
        configText = readConfigFile("fileName");
        processConfigOptions(configText, configOptions);
        // 在此处volatile保证了“initialized = true”不会被重排序到读取配置信息之前。
        initialized = true;


        //假设以下代码在线程B中执行
//等待initialized为true，代表线程A已经把配置信息初始化完成
        while (!initialized) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public char[] readConfigFile(String fileName) {
        char[] configText = null;

        //doSometing...

        return configText;
    }

    public void processConfigOptions(char[] configText, HashMap configOptions) {

        //doSometing...

    }

    //使用线程A中初始化好的配置信息
    public void doSomethingWithConfig() {

        //doSometing...

    }
}

/*
 ******************************** 3、使用synchronized实现线程间的有序性 ***************************
 */
class Ordering_Test_3 {

    private HashMap configOptions;
    private char[] configText;

    static boolean initialized = false;

    public static synchronized boolean isInitialized() {
        return initialized;
    }
    //假设以下代码在线程A中执行
//模拟读取配置信息，当读取完成后将initialized设置为true以通知其他线程配置可用

    public synchronized void init() {
        configOptions = new HashMap<>();
        configText = readConfigFile("fileName");
        processConfigOptions(configText, configOptions);
        initialized = true;
    }

    //假设以下代码在线程B中执行
//等待initialized为true，代表线程A已经把配置信息初始化完成

    public void startTask() {
        while (!isInitialized()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public char[] readConfigFile(String fileName) {
        char[] configText = null;

        //doSometing...

        return configText;
    }

    public void processConfigOptions(char[] configText, HashMap configOptions) {

        //doSometing...

    }

    //使用线程A中初始化好的配置信息
    public void doSomethingWithConfig() {

        //doSometing...

    }
}

/*
 ******************************** 4、使用lock实现线程间的有序性 ********************************
 */
class Ordering_Test_4 {

    private HashMap configOptions;
    private char[] configText;

    static Lock lock = new ReentrantLock();

    static boolean initialized = false;

    public static boolean isInitialized() {
        lock.lock();
        try {
            return initialized;
        } finally {
            lock.unlock();
        }
    }

    //假设以下代码在线程A中执行
//模拟读取配置信息，当读取完成后将initialized设置为true以通知其他线程配置可用
    public void init() {
        lock.lock();
        try {
            configOptions = new HashMap<>();
            configText = readConfigFile("fileName");
            processConfigOptions(configText, configOptions);
            initialized = true;
        } finally {
            lock.unlock();
        }
    }

    //假设以下代码在线程B中执行
//等待initialized为true，代表线程A已经把配置信息初始化完成
    public void startTask() {
        while (!Ordering_Test_4.isInitialized()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public char[] readConfigFile(String fileName) {
        char[] configText = null;

        //doSometing...

        return configText;
    }

    public void processConfigOptions(char[] configText, HashMap configOptions) {

        //doSometing...

    }

    //使用线程A中初始化好的配置信息
    public void doSomethingWithConfig() {

        //doSometing...

    }
}