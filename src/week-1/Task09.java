public class Task09 {
    /*
    在jdk8之前，interface之中可以定义变量和方法，变量必须是public、static、final的，方法必须是public、abstract的。
    JDK8及以后，允许我们在接口中定义static方法和default方法。
    静态方法，只能通过接口名调用，不可以通过实现类的类名或者实现类的对象调用。
    default方法，只能通过接口实现类的对象来调用。
     */
    public static void main(String[] args) {
        Jdk8Interface.staticMethod();
        new Jdk8InterfaceImp1().defaultMethod();
        new Jdk8InterfaceImp2().defaultMethod();

    }

    interface Jdk8Interface {
        //static修饰符定义静态方法
        static void staticMethod() {
            System.out.println("接口中的static方法");
        }

        //default修饰符定义默认方法
        default void defaultMethod() {
            System.out.println("接口中的default方法");
        }
    }

    static class Jdk8InterfaceImp1 implements Jdk8Interface {
        //实现接口后，因为默认方法不是抽象方法，所以可以不重写，但是如果开发需要，也可以重写
    }

    static class Jdk8InterfaceImp2 implements Jdk8Interface {
        @Override
        public void defaultMethod() {
            System.out.println("接口实现类重写了接口中的default方法");
        }
    }

    interface Target1 {
        String method1();
    }

    interface Target2 {
        String method2();
    }

    /*
    接口可以多继承
     */
    interface Target extends Target1, Target2 {
        String method();
    }

    class TargetImp implements Target {

        @Override
        public String method1() {
            return null;
        }

        @Override
        public String method2() {
            return null;
        }

        @Override
        public String method() {
            return null;
        }
    }
}
