public class Task11 {
    /*
    多态应用：接口引用指向实现类对象
     */
    public static void main(String[] args) {
        PhoneFactory factory = new PhoneFactory();
        factory.createPhone(new P30());
        factory.createPhone(new P40());
    }
}

class PhoneFactory {
    public void createPhone(Phone p) {
        p.creat();
    }
}

interface Phone {
    void creat();
}

//P30手机
class P30 implements Phone {
    public void creat() {
        System.out.println("生产p30手机");
    }
}

//P40手机
class P40 implements Phone {
    public void creat() {
        System.out.println("生产p40手机");
    }

}
