package week_5;

/**
 * 外部类测试单例模式的实例化
 */
public class SingletonTest {
    public static void main(String[] args) {
//        以下通过new构造单例会报错，单例的构造函数为private,不能通过外部类创建
//        LazySingletonTest instance = new LazySingletonTest();
//        需要通过单例提供的静态方法来实例化
        LazySingletonTest singleton1 = LazySingletonTest.getInstance();
        System.out.println(singleton1.getInit());
        System.out.println("__________________________________");
        LazySingletonTest singleton2 = LazySingletonTest.getInstance();
        System.out.println(singleton2.getInit());
        if (singleton1 == singleton2) {
            System.out.println("是同一个实例");
        } else {
            System.out.println("不是同一个实例");
        }

    }
}
