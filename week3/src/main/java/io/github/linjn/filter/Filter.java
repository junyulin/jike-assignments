package io.github.linjn.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 第三周作业，过滤器
 *
 * @author LinJn
 * @since 2021/5/23 22:00
 */
public interface Filter {

    /**
     * 过滤器
     * @param fullRequest fullRequest
     * @param ctx ctx
     */
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
