package homeword3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 使用事务，PrepareStatement 方式，批处理方式，改进上述操作
 *
 * @author LinJn
 * @since 2021/6/6 22:32
 */
public class Homeword2 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/ds0";
        // 建立连接
        Connection connection = DriverManager.getConnection(url, "root", "root");
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
