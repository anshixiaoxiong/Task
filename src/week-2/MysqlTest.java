import java.sql.*;

public class MysqlTest {
    public static void main(String[] args) {
//        测试数据库连接
        JdbcUtils.tryConnection();
//        insert("Jim", 3);
//        delete(16);
//        update(15,100);
//        query(15);
        sum();
        avg();
        count();
        max();
        min();
    }

    /*
    求和函数： select sum(column) from table
    注：sum(column) 对字符串和时间无效
     */
    public static void sum() {
        String sql = "select sum(age) from user ";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);

            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("age之和: "+rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs, st, con);
        }
    }

    /*
    求平均值：select avg(column) from table;
    注：avg(column)对字符串和时间无效
     */
    public static void avg() {
        String sql = "select avg (age) from user ";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);

            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("平均age: "+rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs, st, con);
        }
    }
    /*
    计数：select count(*) from table;
    注：count(column)不包含NULL；
     */
    public static void count() {
        String sql = "select count(age) from user ";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);

            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("age总数: "+rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs, st, con);
        }
    }
    /*
    求最大值：select max(column) from table;
    注：max(column)返回字母序最大的，返回数值最大的
     */
    public static void max() {
        String sql = "select max(age) from user ";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);

            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("最大age: "+rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs, st, con);
        }
    }
    /*
    求最小值：select min(column) from table;
    注：min(column)返回字母序最小值，返回数值最小值
     */
    public static void min() {
        String sql = "select min(age) from user ";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);

            rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("最小age: "+rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs, st, con);
        }
    }

    /*
    简单的增、删、改、查
     */
    public static void insert(String name, int age) {
        String sql = "insert into user(name,age) values(?,?)";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setInt(2, age);
            int res = st.executeUpdate();
            if (res > 0) {
                System.out.println("插入成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs, st, con);
        }
    }

    public static void delete(int id) {
        String sql = "delete from user where id = ?";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            int res = st.executeUpdate();
            if (res > 0) {
                System.out.println("删除成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs, st, con);
        }
    }

    public static void update(int id, int val) {
        String sql = "update user set age = ? where id = ? ";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, val);
            st.setInt(2, id);
            int res = st.executeUpdate();
            if (res > 0) {
                System.out.println("更新成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs, st, con);
        }
    }

    public static void query(int id) {
        String sql = "select  * from user where id = ?";
        Connection con = JdbcUtils.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
//                按列属性取值
//                int uid = rs.getInt("id");
//                String name = rs.getString("name");
//                int age = rs.getInt("age");
//                按索引取值
                int uid = rs.getInt(1);
                String name = rs.getString(2);
                int age = rs.getInt(3);
                System.out.println(uid + " " + name + " " + age);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(rs, st, con);
        }
    }
}

class JdbcUtils {

    private static String url = "jdbc:mysql://localhost:3306/mytest";
    private static String user = "root";
    private static String password = "root";

    private JdbcUtils() {

    }

    //静态代码块保证驱动只注册一次
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //测试连接是否成功
    public static void tryConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                System.out.println("Succeeded connecting to the Database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //获取连接对象
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    //释放资源
    public static void close(ResultSet rs, PreparedStatement st, Connection con) {
        try {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } finally {
            try {
                if (st != null)
                    try {
                        st.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            } finally {
                if (con != null)
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
