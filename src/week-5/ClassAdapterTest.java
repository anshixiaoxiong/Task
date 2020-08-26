package week_5;

public class ClassAdapterTest {
    /**
     * 适配器模式很好体现了多态的思想
     */
    public static void main(String[] args) {
//        类适配器：
        Target target1 = new ClassAdapter();
        target1.request();

//        对象适配器：
        Target target2 = new ObjectAdapter(new Adapter());
        target2.request();

        Adapter adapter = new ClassAdapter();
        adapter.specialRequest();
//
        ClassAdapter classAdapter = new ClassAdapter();
        classAdapter.request();
        classAdapter.specialRequest();
//测试抽象类，抽象类不能直接通过new实例化
        new A().request();
        new A().say();
    }
}

//目标接口
interface Target {
    public void request();
}

//适配者接口
class Adapter {
    public void specialRequest() {

        System.out.println("适配者中的业务代码被调用！");
    }
}

//类适配器类
class ClassAdapter extends Adapter implements Target {

    @Override
    public void request() {
        specialRequest();
    }
}

abstract class adaptor implements Target {
    abstract void say();
}

class A extends adaptor {

    @Override
    public void request() {

    }

    @Override
    void say() {

    }
}


//对象适配器类
class ObjectAdapter implements Target {
    private Adapter adapter;

    public ObjectAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void request() {

        adapter.specialRequest();
    }
}

