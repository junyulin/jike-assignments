package io.github.linjn.route;

import java.util.List;
import java.util.Random;

/**
 * 第三周作业
 * 随机路由器
 *
 * @author LinJn
 * @since 2021/5/23 21:20
 */
public class RandomRouter implements Router {

    @Override
    public String route(List<String> server) {
        Random random = new Random(System.currentTimeMillis());
        int i = random.nextInt(server.size());
        return server.get(i);
    }
}
