package cn.linjn.xa.demo;

import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * xa 实例
 *
 * @author LinJn
 * @since 2021/7/3 10:37
 */
@Component
@RequiredArgsConstructor
public class XaDemo {

    private final JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA)
    public void add() throws InterruptedException {
        String sql = "insert into `t_order`(`id`, `user_id`) values(?, ?)";
        PreparedStatementCallback<Object> callback = s -> {
            for (int i = 1; i < 5; i++) {
                s.setInt(1, i);
                s.setInt(2, 1);
            }
            return null;
        };
        this.jdbcTemplate.execute(sql, callback);
        System.out.println("First Tx End");
        PreparedStatementCallback<Object> callback2 = s -> {
            for (int i = 5; i < 8; i++) {
                s.setInt(1, i);
                s.setInt(2, 2);
                if (i == 6) {
                    int a = 1 / 0;
                }
            }
            return null;
        };
        this.jdbcTemplate.execute(sql, callback2);
    }
}
