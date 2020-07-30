import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task08 {
    /*
    正则表达式练习
     */
    public static void main(String[] args) {
        /*
        1.过滤非法字符，并将字符串倒序输出。（单词不倒序）
         */
        String line = "I am @!#$software1234engineer1234";
        String regx = "[^A-Za-z]";
        String str = line.replaceAll(regx, " ");
//        String[] split = str.split(" +");//一个或者多个空格
        String[] split = str.split("\\s+");//一个或者多个空格,制表符等
        System.out.println(split.length);
        StringBuffer sb = new StringBuffer();
        for (int i = split.length - 1; i >= 0; i--) {
            sb.append(split[i]).append(" ");
        }
        System.out.println(sb.toString().trim());

        /*
        2.匹配163邮箱
         */
        String in = "zhouyuao-666_Ace@163.com";
        String regx1 = "^[A-Za-z0-9-_]+@163\\.com$";
        boolean b = Pattern.matches(regx1, in);
        System.out.println(b);

        String lines = "read[addr=0x17,mask=0xff,val=0x7],read_his[addr=0xff,mask=0xff,val=0x1],read[addr=0xf0,mask=0xff,val=0x80]";
        String chars = "read";
        System.out.print(matchRegister(chars, lines));
    }

    /*
        3.分组匹配：寄存器匹配
        输入：
            read
            read[addr=0x17,mask=0xff,val=0x7],read_his[addr=0xff,mask=0xff,val=0x1],read[addr=0xf0,mask=0xff,val=0x80]
       输出：
            0x17 0xff 0x7
            0xf0 0xff 0x80
         */
    public static String matchRegister(String str, String lines) {
        String regex = str + "\\[addr=(0[xX][0-9a-fA-F]+),mask=(0[xX][0-9a-fA-F]+),val=(0[xX][0-9a-fA-F]+)\\]?";
        String[] splits = lines.split("],");
        Pattern pattern = Pattern.compile(regex);
        boolean isMatched = false;
        StringBuffer sb = new StringBuffer();
        for (String split : splits) {
            Matcher matcher = pattern.matcher(split);
            if (matcher.matches()) {
                isMatched = true;
                sb.append(matcher.group(1)).append(" ")
                        .append(matcher.group(2)).append(" ")
                        .append(matcher.group(3)).append("\r\n");
            }

        }
        if (!isMatched) {
            return "FAIL";
        }
        return sb.toString();
    }

}
