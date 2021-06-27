package homework01;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 批量插入 100w 条数据
 *
 * @author LinJn
 * @since 2021/6/22 21:46
 */
public class AddBatchDemo2 {

    private static final HikariDataSource ds;

    static {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/jike?true&rewriteBatchedStatements=true");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        ds = new HikariDataSource(hikariConfig);
    }

    private static final ExecutorService executor = Executors.newFixedThreadPool(16);


    public static void main(String[] args) throws Exception {
        long bg = System.currentTimeMillis();
        test1();
        long end = System.currentTimeMillis();
        System.out.println((end - bg) + " ms");
    }


    public static void test1() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final int no = i;
            executor.execute(() -> {
                try {
                    Connection connection = ds.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `order`(`order_no`, `user_id`, `status`, `price`, `discount_price`, `count`, `address_snapshoot`, `place_time`, `expire_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    for (int j = no * 100000; j < (no + 1) * 100000; j++) {
                        preparedStatement.setString(1, "YT" + j);
                        preparedStatement.setInt(2, j);
                        preparedStatement.setInt(3, 1);
                        preparedStatement.setString(4, "56.23");
                        preparedStatement.setString(5, "54.12");
                        preparedStatement.setInt(6, 4);
                        preparedStatement.setString(7, "{    \"address\": \"南山区西丽街道旺棠大厦6B02\",    \"city\": \"深圳\",    \"name\": \"林xx\",    \"phone\": \"13546887599\",    \"provice\": \"广东\"}");
                        long l = System.currentTimeMillis();
                        preparedStatement.setDate(8, new Date(l));
                        preparedStatement.setDate(9, new Date(l));
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        // 关闭资源
    }
}
