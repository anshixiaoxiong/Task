import java.math.BigDecimal;

public class Task02 {
    /*
    BigDecimal的计算-加减乘除
     */
//    不论是float 还是double都是浮点数，而计算机是二进制的，浮点数会失去一定的精确度。
//    Java在java.math包中提供的API类BigDecimal，用来对超过16位有效位的数进行精确的运算。
//    BigDecimal所创建的是对象，我们不能使用传统的+、-、*、/等算术运算符直接对其对象进行数学运算，
//    而必须调用其相对应的方法。方法中的参数也必须是BigDecimal的对象。

    //    注意事项：
//    因为不是所有的浮点数都能够被精确的表示成一个double 类型值，有些浮点数值不能够被精确的表示成 double 类型值，
//    因此它会被表示成与它最接近的 double 类型的值。必须改用传入String的构造方法。这一点在BigDecimal类的构造方法注释中有说明。
    public static void main(String[] args) {
        double d = 0.005;
        BigDecimal num1 = new BigDecimal(0.005);
        BigDecimal num2 = new BigDecimal(1000000);
        BigDecimal num3 = new BigDecimal(-1000000);
        //尽量用字符串的形式初始化
        BigDecimal num = new BigDecimal(Double.toString(d));//用包装类转化为字符串或者  0.005+“ ”
        BigDecimal num12 = new BigDecimal("0.005");
        BigDecimal num22 = new BigDecimal("1000000");
        BigDecimal num32 = new BigDecimal("-1000000");

        //加法
        BigDecimal result1 = num1.add(num2);
        BigDecimal result12 = num12.add(num22);
        //减法
        BigDecimal result2 = num1.subtract(num2);
        BigDecimal result22 = num12.subtract(num22);
        //乘法
        BigDecimal result3 = num1.multiply(num2);
        BigDecimal result32 = num12.multiply(num22);
        //绝对值
        BigDecimal result4 = num3.abs();
        BigDecimal result42 = num32.abs();
        //除法
        BigDecimal result5 = num2.divide(num1, 20, BigDecimal.ROUND_HALF_UP);
        BigDecimal result52 = num22.divide(num12, 20, BigDecimal.ROUND_HALF_UP);

        System.out.println("加法用value结果：" + result1);
        System.out.println("加法用string结果：" + result12);

        System.out.println("减法value结果：" + result2);
        System.out.println("减法用string结果：" + result22);

        System.out.println("乘法用value结果：" + result3);
        System.out.println("乘法用string结果：" + result32);

        System.out.println("绝对值用value结果：" + result4);
        System.out.println("绝对值用string结果：" + result42);

        System.out.println("除法用value结果：" + result5);
        System.out.println("除法用string结果：" + result52);

//        BigDecimal用于表示精确的小数，常用于财务计算；
//        比较BigDecimal的值是否相等，必须使用compareTo()而不能使用equals()
        BigDecimal decimal1 = new BigDecimal("123.456");
        BigDecimal decimal2 = new BigDecimal("123.4560000");
//        获取小数位数：scale()方法
        System.out.println("decimal1的小数位数：" + decimal1.scale());
        System.out.println("值是否相等：" + decimal1.equals(decimal2));
        System.out.println("值是否相等：" + decimal1.compareTo(decimal2));
    }
//    八种舍入模式解释如下
//           1、ROUND_UP
//
//    舍入远离零的舍入模式。
//
//    在丢弃非零部分之前始终增加数字(始终对非零舍弃部分前面的数字加1)。
//
//    注意，此舍入模式始终不会减少计算值的大小。
//
//            2、ROUND_DOWN
//
//    接近零的舍入模式。
//
//    在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1，即截短)。
//
//    注意，此舍入模式始终不会增加计算值的大小。
//
//            3、ROUND_CEILING
//
//    接近正无穷大的舍入模式。
//
//    如果 BigDecimal 为正，则舍入行为与 ROUND_UP 相同;
//
//    如果为负，则舍入行为与 ROUND_DOWN 相同。
//
//    注意，此舍入模式始终不会减少计算值。
//
//            4、ROUND_FLOOR
//
//    接近负无穷大的舍入模式。
//
//    如果 BigDecimal 为正，则舍入行为与 ROUND_DOWN 相同;
//
//    如果为负，则舍入行为与 ROUND_UP 相同。
//
//    注意，此舍入模式始终不会增加计算值。
//
//            5、ROUND_HALF_UP
//
//    向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。
//
//    如果舍弃部分 >= 0.5，则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同。
//
//    注意，这是我们大多数人在小学时就学过的舍入模式(四舍五入)。
//
//            6、ROUND_HALF_DOWN
//
//    向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为上舍入的舍入模式。
//
//    如果舍弃部分 > 0.5，则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同(五舍六入)。
//
//            7、ROUND_HALF_EVEN
//
//    向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。
//
//    如果舍弃部分左边的数字为奇数，则舍入行为与 ROUND_HALF_UP 相同;
//
//    如果为偶数，则舍入行为与 ROUND_HALF_DOWN 相同。
//
//    注意，在重复进行一系列计算时，此舍入模式可以将累加错误减到最小。
//
//    此舍入模式也称为“银行家舍入法”，主要在美国使用。四舍六入，五分两种情况。
//
//    如果前一位为奇数，则入位，否则舍去。
//
//    以下例子为保留小数点1位，那么这种舍入方式下的结果。
//
//            1.15>1.2 1.25>1.2
//
//            8、ROUND_UNNECESSARY
//
//    断言请求的操作具有精确的结果，因此不需要舍入。
//    如果对获得精确结果的操作指定此舍入模式，则抛出ArithmeticException。

}
