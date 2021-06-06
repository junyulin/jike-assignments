package homeword3;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 配置 Hikari 连接池。
 * 这里这是简单使用了 Hikari，比较好的方式应该是将配置信息写在配置文件里，通过一个类读取配置信息，
 * 然后通过自动注入的方式注入 HikariDataSource
 *
 * @author LinJn
 * @since 2021/6/6 22:32
 */
public class Homeword3 {

    private static final HikariDataSource ds;

    static {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/ds0");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        ds = new HikariDataSource(hikariConfig);
    }

    public static void main(String[] args) throws SQLException {
        // 建立连接
        Connection connection = ds.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            // 自动事务取消
            connection.setAutoCommit(false);
            // 创建 preparedStatement
            String sql = "INSERT INTO item (`name`, user_id) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            // 批处理
            for (int i = 0; i < 10; i++) {
                preparedStatement.setString(1, "linjn" + i);
                preparedStatement.setInt(2, i);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 事务回滚
            connection.rollback();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            connection.close();
        }
    }

}
