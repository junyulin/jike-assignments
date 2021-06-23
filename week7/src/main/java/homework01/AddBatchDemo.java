package homework01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 批量插入 100w 条数据
 *
 * @author LinJn
 * @since 2021/6/22 21:46
 */
public class AddBatchDemo {

    private static final Logger LOGGER = LogManager.getLogger("AddBatchDemo");

    private static final String URL = "jdbc:mysql://localhost:3306/jike?true&rewriteBatchedStatements=true";

//    private static final String URL = "jdbc:mysql://localhost:3306/jike";

    private static final String USERNAME = "root";

    private static final String PWD = "root";

    /*private static final ExecutorService executor = Executors.newCachedThreadPool();*/

    private static final ExecutorService executor = Executors.newFixedThreadPool(16);


    public static void main(String[] args) throws Exception {
        long bg = System.currentTimeMillis();
         test1();
//        test2();
        long end = System.currentTimeMillis();
        System.out.println((end - bg) + " ms");
    }

    public static void test1() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, USERNAME, PWD);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `order`(`order_no`, `user_id`, `status`, `price`, `discount_price`, `count`, `address_snapshoot`, `place_time`, `expire_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        connection.setAutoCommit(false);
        long bg = System.currentTimeMillis();
        for (int i = 1; i <= 20000; i++) {
            preparedStatement.setString(1, "YT" + i);
            preparedStatement.setInt(2, i);
            preparedStatement.setInt(3, 1);
            preparedStatement.setString(4, "56.23");
            preparedStatement.setString(5, "54.12");
            preparedStatement.setInt(6, 4);
            preparedStatement.setString(7, "{    \"address\": \"南山区西丽街道旺棠大厦6B02\",    \"city\": \"深圳\",    \"name\": \"林xx\",    \"phone\": \"13546887599\",    \"provice\": \"广东\"}");
            long l = System.currentTimeMillis();
            preparedStatement.setDate(8, new Date(l));
            preparedStatement.setDate(9, new Date(l));
            preparedStatement.addBatch();
            /*if (i % 10000 == 0) {
                executor.execute(() -> {
                    try {
                        preparedStatement.executeBatch();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
            }*/
        }
        System.out.println((System.currentTimeMillis() - bg) + "ms");
        preparedStatement.executeBatch();
        connection.commit();
        executor.shutdown();
        // 关闭资源
        preparedStatement.close();
        connection.close();
    }

    public static void test2() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, USERNAME, PWD);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `test`(`order_no`) VALUES(?)");
        connection.setAutoCommit(false);
        long bg = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            preparedStatement.setInt(1, i);
            preparedStatement.addBatch();
            if (i % 50000 == 0) {
                executor.execute(() -> {
                    try {
                        preparedStatement.executeBatch();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
            }
        }
        System.out.println((System.currentTimeMillis() - bg) + "ms");
        preparedStatement.executeBatch();
        connection.commit();
        executor.shutdown();
        // 关闭资源
        preparedStatement.close();
        connection.close();
    }

}
