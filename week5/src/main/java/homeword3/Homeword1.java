package homeword3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用 JDBC 原生接口，实现数据库的增删改查操作。
 *
 * @author LinJn
 * @since 2021/6/6 22:32
 */
public class Homeword1 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/ds0";
        // 建立连接
        Connection connection = DriverManager.getConnection(url, "root", "root");
        // 创建 Statement
        Statement statement = connection.createStatement();
        // 查询
        select(statement);
        // 增加
        String insertSql = "INSERT INTO item (`name`, user_id) VALUES ('linjn', 3)";
        update(statement, insertSql);
        // 删除
        String deleteSql = "DELETE FROM item WHERE id = 2";
        update(statement, deleteSql);
        // 修改
        String updateSql = "UPDATE item SET `name` = 'linjn1' WHERE id = 1";
        update(statement, updateSql);
        // 关闭资源
        statement.close();
        connection.close();
    }

    public static void select(Statement statement) throws SQLException {
        // 执行查询语句，将结果存到结果集 ResultSet 中去
        ResultSet resultSet = statement.executeQuery("select * from item");
        List<Item> itemList = new ArrayList<>();
        // 遍历结果集取出查询的数据
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int userId = resultSet.getInt("user_id");
            // 封装到 item 中去
            Item item = new Item();
            item.setId(id);
            item.setName(name);
            item.setUserId(userId);
            itemList.add(item);
        }
        System.out.println(itemList);
        // 关闭资源
        resultSet.close();
    }

    public static void update(Statement statement, String sql) throws SQLException {
        int i = statement.executeUpdate(sql);
        System.out.println(i);
    }
}
