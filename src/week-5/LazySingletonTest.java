package week_5;

public class LazySingletonTest {
    //volatile  在此处volatile的禁止指令重排序作用保证了实例的初始化完成先于将对象地址写到instance字段。
    private static volatile LazySingletonTest instance = null;
    private int init;

    //private  避免类在外部被实例化
    private LazySingletonTest() {
        System.out.println("创建一个单例对象...");
        System.out.println("初始化单例对象...");
        init = 100;
    }

    public int getInit() {
        return init;
    }

    //    双重校验锁
    public static LazySingletonTest getInstance() {
        if (instance == null) {
            synchronized (LazySingletonTest.class) {
                if (instance == null) {
                    instance = new LazySingletonTest();
                }
            }
        } else {
            System.out.println("单例已经创建，不能重复创建，请直接使用！");
        }
        return instance;

        //getInstance方法前加同步(同步方法,同步方法耗时是只同步临界区的10倍到20倍)
//    public static synchronized LazySingleton getInstance() {
//        if (instance == null) {
//            instance = new LazySingleton();
//        }
//        return instance;
//    }
    }

    /**
     * 此处的main在单例内部，顾可以通过new new LazySingletonTest()创建多个不同实例（开发中肯定不会这样）
     * 由于在内部还可以访问private属性，如init
     */
    public static void main(String[] args) {
//        在本类内可以new成功但并没有实例化，但在外部类中由于构造函数为private，故不能new成功
        LazySingletonTest singleton1 = new LazySingletonTest();
//        LazySingletonTest singleton1 = LazySingletonTest.getInstance();
        System.out.println(singleton1.getInit());
//      访问static变量
        int value = singleton1.init;
        LazySingletonTest lazy = singleton1.instance;
        LazySingletonTest instance = singleton1.getInstance();

        System.out.println("instance:  " + instance);
        System.out.println("__________________________________");
        LazySingletonTest singleton2 = new LazySingletonTest();

        System.out.println(LazySingletonTest.instance);
        LazySingletonTest singleton = LazySingletonTest.getInstance();
        System.out.println("singleton:  " + singleton);

        System.out.println(singleton1);
        System.out.println(singleton2);
        System.out.println(LazySingletonTest.instance);
        System.out.println(singleton2.instance);

//        System.out.println(singleton2.getInit());
        if (singleton1 == singleton2) {
            System.out.println("是同一个实例");
        } else {
            System.out.println("不是同一个实例");
        }
    }
}
