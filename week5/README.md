# 极客时间《JAVA进阶训练营》第五周作业

1. **必做作业1：写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 GitHub**。

   作业位于 `src\main\java\homeword1`，使用的配置文件为：`applicationContext.xml`，被用来装配的类是 **Student**。采用的方式有：

   1. 直接在 `Student` 类上使用注解 `@Component` 进行注入。（`@service`、`@controller` 注解是一样的道理 ）
   2. 在 `applicationContext.xml` 使用 `bean` 标签进行注入。
   3. 使用注解 `@Configuration` 与 `@Bean`  进行注入。代码位于 src\main\java\homeword1\definition
   4. 使用自定义标签的方式。主要代码位于 `src\main\java\homeword1\definition` 文件夹下。
   5. 自动配置注入的方式。这部分的代码在必做作业2中实现。

2. **必做作业2：给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。**

   作业位于 `src\main\java\homeword2` 文件夹下，自动配置的类是这个文件夹下面的 `SchoolAutoConfiguration` 类。

3. **必做作业3：研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法**：**

   **1）使用 JDBC 原生接口，实现数据库的增删改查操作**。

   ​	作业位于 `src\main\java\homeword3` 文件下 `Homeword1` 类。

   **2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。**

   ​	作业位于 `src\main\java\homeword3` 文件下 `Homeword2` 类。

   **3）配置 Hikari 连接池，改进上述操作。提交代码到 GitHub。**

   ​	作业位于 `src\main\java\homeword3` 文件下 `Homeword3` 类。
