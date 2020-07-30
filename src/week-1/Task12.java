public class Task12 {
    /*
    用适配器模式体现面向对象的特征，类适配器和对象适配器
     */
    public static void main(String[] args) {
//        类适配器：
        Target target1 = new ClassAdapter();
        target1.request();

//        对象适配器：
        Target target2 = new ObjectAdapter(new Adapter());
        target2.request();

//        Adapter adapter = new ClassAdapter();
//        adapter.specialRequest();
//
//        ClassAdapter classAdapter = new ClassAdapter();
//        classAdapter.request();
//        classAdapter.specialRequest();

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
/*
抽象方法实现接口可以不进行实现，此处只是测试
 */
//abstract class adaptor implements Target {
//    abstract void say() ;
//
//}

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

