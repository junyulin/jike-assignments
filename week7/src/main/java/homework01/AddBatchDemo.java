package homework01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
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

    private static final String USERNAME = "root";

    private static final String PWD = "root";

    private static final ExecutorService executor = Executors.newFixedThreadPool(16);


    public static void main(String[] args) throws Exception {
        long bg = System.currentTimeMillis();
         test1();
        long end = System.currentTimeMillis();
        LOGGER.info(" {} ms", end - bg);
    }


    public static void test1() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, USERNAME, PWD);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `test`(`order_no`) VALUES(?)");
        connection.setAutoCommit(false);
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
        executor.shutdown();
        preparedStatement.executeBatch();
        connection.commit();
        // 关闭资源
        preparedStatement.close();
        connection.close();
    }

}
