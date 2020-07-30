public class Task10 {
    /*
    多态应用：父类引用指向子类
         多态的成员特点:
              成员变量  编译时看的是左边，运行时看的左边
              成员方法  编译时看的是左边，运行时看右边
              静态方法  编译时看的是左边，运行时看的也是左边
        编译时看的都是左边，运行时成员方法看的是右边，其他（成员变量和静态的方法）看的都是左边
    */
    public static void main(String[] args) {
        Dad d = new Kid();
        System.out.println(d.num);
        d.method();
        d.function();
    }
}

class Dad {
    int num = 20;

    public void method() {
        System.out.println("我是父类方法");
    }

    public static void function() {
        System.out.println("我是父类静态方法");
    }
}

class Kid extends Dad {
    int num = 10;

    public void method() {
        System.out.println("我是子类方法");
    }

    public static void function() {
        System.out.println("我是子类静态方法");
    }
}
