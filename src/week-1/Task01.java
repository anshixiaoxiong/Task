import java.math.BigDecimal;
import java.math.BigInteger;

public class Task01 {
    //    java 8种基本数据类型及互相转换，类型的强制转换和自动转换
//    java的关键字，运算符，正则表达式及java流程控制的条件，循环，跳转的使用
//    BigDecimal
//    BigInteger
//     类型:  byte  short  int long  double float  char boolean
//     字节:    1    2      4   8      8     4     2      1
    public static void main(String[] args) {
//        1.自动类型转换
//        自动类型转换是指：数字表示范围小的数据类型可以自动转换成范围大的数据类型。
//        如：
        int a = 100;
        long aa = a;
        double aaa = a;
        System.out.println("aa="+aa);//aa=100
        System.out.println("aaa="+aaa);//aaa=100.0
        float fl = 0.75f;
        System.out.println("fl"+fl);
        double dou = (double) fl;
        System.out.println("dou:"+dou);//0.75

//        自动转换也会出现数据溢出问题
//        如：
        int count1 = 100000000;
        int price1 = 1999;
        long totalPrice1 = count1 * price1;
        System.out.println("totalPrice1:"+totalPrice1);
//        编译没任何问题，但结果却输出的是负数，这是因为两个int 相乘得到的结果是 int, 相乘的结果超出了 int 的代表范围。
//        这种情况，一般把第一个数据转换成范围大的数据类型再和其他的数据进行运算。

        int count2 = 100000000;
        int price2 = 1999;
        long totalPrice2 = (long) count2 * price2;
        System.out.println("totalPrice2:"+totalPrice2);
//        另外，向下转换时可以直接将 int 常量字面量赋值给 byte、short、char 等数据类型，而不需要强制转换，只要该常量值不超过该类型的表示范围都能自动转换。

//        2.强制类型转换
//        强制类型转换我们再清楚不过了，即强制显示的把一个数据类型转换为另外一种数据类型。
//        如：
        short s = 199;
        int i = s;// 199
        System.out.println("i:"+i);

        double d = 10.24;
        long ll = (long) d;// 10
        System.out.println("ll:"+ll);
//        以上的转换结果都在我们的预期之内，属于正常的转换和丢失精度的情况，下面的例子就一样属于数据溢出的情况。

        int ii = 300;
        byte b = (byte) ii;
        System.out.println("b:"+b);
//        300 已经超出了 byte 类型表示的范围，所以会转换成一个毫无意义的数字。


    }

}
