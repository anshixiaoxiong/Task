package week_5;

public class HungrySingletonTest {
    /**
     * 类一旦加载就创建一个单例，保证在调用 getInstance 方法之前单例已经存在了。
     * 饿汉式单例在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，
     * 所以是线程安全的，可以直接用于多线程而不会出现问题。
     */
    private static final HungrySingletonTest instance = new HungrySingletonTest();
    private int init;
    private HungrySingletonTest() {
        System.out.println("产生一个单例对象...");
        System.out.println("初始化该单例对象...");
        init = 100;
    }

    public int getInit() {
        return init;
    }

    public static HungrySingletonTest getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        HungrySingletonTest singleton1 = HungrySingletonTest.getInstance();
        System.out.println(singleton1.getInit());
        HungrySingletonTest singleton2 = HungrySingletonTest.getInstance();
        System.out.println(singleton2.getInit());
        if (singleton1 == singleton2) {
            System.out.println("是同一个对象");
        } else {
            System.out.println("不是同一个对象");
        }
    }
}
