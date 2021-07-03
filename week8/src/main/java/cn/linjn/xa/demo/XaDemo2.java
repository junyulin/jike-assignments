package cn.linjn.xa.demo;

import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * xa 事务
 *
 * @author LinJn
 * @since 2021/7/3 11:21
 */
@RequiredArgsConstructor
@Component
public class XaDemo2 {

    private final DataSource dataSource;

    public void add() throws SQLException {
        TransactionTypeHolder.set(TransactionType.XA);
        Connection connection = this.dataSource.getConnection();
        String sql = "insert into `t_order`(`id`, `user_id`) values(?, ?)";
        try (final PreparedStatement p = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            for (int i = 1; i <= 4; i++) {
                p.setInt(1, i);
                p.setInt(2, 1);
                p.executeUpdate();
            }
            connection.commit();
        }
        System.out.println("First XA end");
        try (final PreparedStatement p = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            for (int i = 5; i <= 8; i++) {
                p.setInt(1, i);
                p.setInt(2, 2);
                if (i == 7) {
                    int a = 1 / 0;
                }
                p.executeUpdate();
            }
            connection.commit();
        } catch (Exception e) {
            System.out.println("tx rollback");
            connection.rollback();
        }
        connection.close();
    }

}
