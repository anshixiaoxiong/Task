package week_5;

/**
 * 原型模式的克隆分为浅克隆和深克隆，Java 中的 Object 类提供了浅克隆的 clone() 方法，
 * 具体原型类只要实现 Cloneable 接口就可实现对象的浅克隆，这里的 Cloneable 接口就是抽象原型类
 */
public class PrototypeTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Prototype prototype = new Prototype();
        Prototype prototype1 = (Prototype) prototype.clone();
        if (prototype != prototype1) {
            System.out.println("两个不同的实例创建成功！");
        }
    }
}

class Prototype implements Cloneable {
    public Prototype() {
        System.out.println("创建原型实例");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        System.out.println("复制原型成功");
        return super.clone();
    }
}