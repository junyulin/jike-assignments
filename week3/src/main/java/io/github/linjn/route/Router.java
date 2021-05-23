package io.github.linjn.route;

import java.util.List;

/**
 * 第三周作业
 * 路由器
 *
 * @author LinJn
 * @since 2021/5/23 21:20
 */
public interface Router {

    /**
     * 路由器
     * @param server 可路由的地址
     * @return 路由到的地址
     */
    String route(List<String> server);

}
