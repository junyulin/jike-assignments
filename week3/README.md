# 极客时间《JAVA进阶训练营》第三周作业

作业的主要代码位于 `src/main/java/io/github/linjn` 的文件夹下，`linjn` 文件夹下面的子文件夹分别代表：

- client：第二周开发的小型客户端。
- filter：过滤器。**本次作业的主要内容。**
- outbound：用来处理请求。**本次作业的主要内容。**
- route：路由。
- server：小型服务器。

对老师原有的代码进行了小小的改动，改动的地方为 `src/main/java/io/github/kimmking/gateway/inbound` 文件夹下面的 **HttpInboundHandler.java** 的 **39-41** 行。相比较老师的代码，调整为先调用过滤器过滤，再调用处理器处理。

