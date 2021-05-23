package io.github.linjn.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 第三周作业
 * 过滤器
 *
 * @author LinJn
 * @since 2021/5/23 21:58
 */
public class ProxyBizFilter implements Filter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().add("name", "linjn");
    }

}
