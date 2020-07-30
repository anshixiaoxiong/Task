public class Task06 {
    /*
    final关键字,final修饰的类不能被继承，final修饰的方法不能被重写，
    final修饰的变量不可以被修改（有点像C语言的宏定义）
    final修饰的引用变量引用不能修改，但引用指向的内容可以改变
     */
    public static void main(String[] args) {
//        final修饰的变量不能被修改
        final int i = 0;
//        i = 1;
        new ChildObj().finalMethod();
        final Student stu = new Student(1);
//        final修饰的引用不能变
//        stu = new Student(2);
        System.out.println(stu.getAge());
//        引用内容可以修改
        stu.setAge(22);
        System.out.println(stu.getAge());
    }

    final class FinalObj {
        void method() {
            System.out.println("Final方法");
        }
    }

    //    不能被继承，报错
//    class FinalObject extends FinalObj {
//    }
    static class Obj {
        final void finalMethod() {
            System.out.println("final方法");
        }
    }

    static class ChildObj extends Obj {
//        final修饰方法不能被重写，报错
//       void finalMethod() {
//       }
    }

    static class Student {
        int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Student(int age) {
            this.age = age;
        }
    }
}
