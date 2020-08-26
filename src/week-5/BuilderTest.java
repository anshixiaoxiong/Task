package week_5;

/**
 * 建造者（Builder）模式的定义：指将一个复杂对象的构造与它的表示分离，使同样的构建过程可以创建不同的表示
 * 它是将一个复杂的对象分解为多个简单的对象，然后一步一步构建而成。
 * 它将变与不变相分离，即产品的组成部分是不变的，但每一部分是可以灵活选择的。
 */
public class BuilderTest {
}

/**
 * (1) 产品角色：包含多个组成部件的复杂对象。(分解复杂对象为多个简单对象)
 */
class Productor {
    private String partA;
    private String partB;
    private String partC;

    public void setPartA(String partA) {
        this.partA = partA;
    }

    public void setPartB(String partB) {
        this.partB = partB;
    }

    public void setPartC(String partC) {
        this.partC = partC;
    }

    public void show() {
        //显示产品的特性
    }
}

/**
 * (2) 抽象建造者：包含创建产品各个子部件的抽象方法
 */
abstract class Builder {
    //创建产品对象
    protected Productor productor = new Productor();

    public abstract void buildPartA();

    public abstract void buildPartB();

    public abstract void buildPartC();

    //返回产品对象
    public Productor getResult() {
        return productor;
    }
}

/**
 * (3) 具体建造者：实现了抽象建造者接口。
 */
class ConcreteBuilder extends Builder {
    public void buildPartA() {
        productor.setPartA("建造 PartA");
    }

    public void buildPartB() {
        productor.setPartA("建造 PartB");
    }

    public void buildPartC() {
        productor.setPartA("建造 PartC");
    }
}

/**
 * (4) 指挥者：调用建造者中的方法完成复杂对象的创建
 */
class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    //产品构建与组装方法
    public Productor construct() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getResult();
    }
}

/**
 * (5) 客户类。
 */
class Client {
    public static void main(String[] args) {
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        Productor productor = director.construct();
        productor.show();
    }
}