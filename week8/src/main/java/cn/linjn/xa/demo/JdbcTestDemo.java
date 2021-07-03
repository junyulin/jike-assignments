package cn.linjn.xa.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 测试一下配置的 sharingsphere-jdbc 是否可用
 *
 * @author LinJn
 * @since 2021/7/2 20:18
 */
@RequiredArgsConstructor
@Component
public class JdbcTestDemo implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    private final XaDemo xaDemo;

    private final XaDemo2 xaDemo2;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        createTable();
//        add();
//        addBatch();
//        query();
        this.xaDemo.add();
//        this.xaDemo2.add();
    }

    public void createTable() {
        final String createTableSql = "CREATE TABLE `t_order` ( `id` BIGINT , `user_id` BIGINT, `filed` varchar(50), PRIMARY KEY ( `id` ) ) ENGINE = INNODB DEFAULT CHARSET = utf8mb4";
        this.jdbcTemplate.execute(createTableSql);
    }

    public void addBatch() {
        String sqlTemplate = "insert into `t_order`(`id`, `user_id`) values(%d, %d)";
        for (int i = 1; i <= 8; i++) {
            String sql;
            if (i <= 4) {
                sql = String.format(sqlTemplate, i, 1);
            } else {
                sql = String.format(sqlTemplate, i, 2);
            }
            this.jdbcTemplate.execute(sql);
        }
    }

    public void add() {
         String sql = "insert into `t_order`(`id`, `user_id`) values(1, 1)";
//        String sql = "insert into `order`(`id`, `user_id`) values(2, 2)";
//        String sql = "insert into `t_order`(`id`, `user_id`) values(6, 3)";
        this.jdbcTemplate.execute(sql);
    }

    public void query() {
        String sql = "select * from t_order";
        List<Map<String, Object>> maps = this.jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.print(" key：" + entry.getKey() + " value：" + entry.getValue());
            }
            System.out.println();
        }
    }
}
