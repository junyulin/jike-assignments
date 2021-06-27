# 极客时间《JAVA进阶训练营》第八周作业

- **必做作业1：设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github**

  先准备好 `order` 表的建表语句：

  ```mysql
  CREATE TABLE `order` (
    `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `order_no` varchar(50) NOT NULL COMMENT '订单编号',
    `user_id` int(10) NOT NULL COMMENT '关联 user 表的 id',
    `status` tinyint(4) NOT NULL COMMENT '订单状态。1：待支付；2：已支付；3：已过期；4：已取消',
    `price` varchar(20) NOT NULL COMMENT '订单总价格',
    `discount_price` varchar(20) NOT NULL COMMENT '折扣后价格',
     # 时间类型的数据与 jpa 配合无法使用
     # `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
    PRIMARY KEY (`id`),
    # 唯一索引无法使用  
    # UNIQUE KEY `order_no_uni` (`order_no`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';
  ```

  然后到官网下载 `apache-shardingsphere-5.0.0-alpha-shardingsphere-proxy-bin`，解压，修改配置文件 `server.yaml` 与 `config-sharding.yaml`。
  
  `server.yaml` 的配置为：
  
  ```yaml
  authentication:
    users:
      root:
        password: root
  
  props:
    max-connections-size-per-query: 1
    acceptor-size: 16  
    executor-size: 16 
    proxy-frontend-flush-threshold: 128  
    proxy-transaction-type: LOCAL
    proxy-opentracing-enabled: false
    proxy-hint-enabled: false
    query-with-cipher-column: true
    sql-show: true # 打印 sql 语句
    check-table-metadata-enabled: false
  ```
  
  `config-sharding.yaml` 的配置为：
  
  ```yaml
  # 逻辑数据库
  schemaName: sharding_db
  #
  dataSourceCommon:
    username: root
    password: root
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50
    minPoolSize: 1
    maintenanceIntervalMilliseconds: 30000
  #
  dataSources:
    ds_0:
      url: jdbc:mysql://127.0.0.1:3306/jike_sharding_0?serverTimezone=UTC&useSSL=false
    ds_1:
      url: jdbc:mysql://127.0.0.1:3306/jike_sharding_1?serverTimezone=UTC&useSSL=false
  #
  rules:
  - !SHARDING
    tables:
      # 逻辑数据表名称
      order:
        # 配置两个库，一个库 16 个表
        actualDataNodes: ds_${0..1}.order_${0..15}
        tableStrategy:
          standard:
            shardingColumn: id
            # 分表的策略为 order_inline
            shardingAlgorithmName: order_inline
        keyGenerateStrategy:
          column: id
          keyGeneratorName: snowflake
    defaultDatabaseStrategy:
      standard:
        shardingColumn: user_id
        shardingAlgorithmName: database_inline
    defaultTableStrategy:
      none:
  #  
    shardingAlgorithms:
      database_inline:
        type: INLINE
        props:
          # 根据 user_id 分库
          algorithm-expression: ds_${user_id % 2}
      order_inline:
        type: INLINE
        props:
          # 根据 id 分表
          algorithm-expression: order_${id % 16}
    keyGenerators:
      snowflake:
        type: SNOWFLAKE
        props:
          worker-id: 123
  ```

  配置完成后，到 bin 目录下启动，如果没有报错即说明启动成功。

  启动成功后，使用 mysql 客户端连接到这个虚拟数据库，执行上面的建表语句，建表成功后可以看到，两个真实的数据库分别创建了 16 个表。

  启动成功后使用 java 程序连接虚拟数据库，这里使用的是 `springboot` 项目，使用 `jpa` 作为 `orm` 框架。
  
  `springboot` 的配置文件为：
  
  ```yaml
  spring:
    datasource:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3307/sharding_db?characterEncoding=utf8&serverTimezone=UTC&useSSL=false
      # url: jdbc:mysql://localhost:3306/jike_sharding_0?characterEncoding=utf8&serverTimezone=UTC&useSSL=false
      username: root
      password: root
  
    jpa:
      show-sql: true
  ```

  到项目中建立 order 表对应的实体类（`entity` 包下）与 Repository （`repository` 包下）。主要的增删改查在 `domain` 包下面的 `CurdDemo` 类。`CurdDemo`  类实现了 `ApplicationRunner`，项目启动就会运行 `CurdDemo`  类的 `run` 方法。
  
  总体而言，整个过程比较简单，但是实际操作中遇到不少问题，完全不知道如何解决，这里分别列出来：

  1. 如果建表语句中有唯一索引，在`shardingsphere-proxy` 中无法成功建表。
  2. 如果实体类中有 Date(java.util.Date) 字段，对应建表语句中的 datetime 字段，插入数据的时候可以成功插入，但是无法成功查询，使用真实数据库并没有出现此问题。报错信息为：`Invalid format for type 2021-06-27T13:40:26. Value 'TIMESTAMP'`，应该是时间格式转换出现了问题。
  3. 使用 `jpa` 内置的 `save` 方法 来更新数据，无法更新成功。
  
  

