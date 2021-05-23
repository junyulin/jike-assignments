package io.github.linjn.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 第三周作业
 *
 * @author LinJn
 * @since 2021/5/23 20:47
 */
public interface OutboundHandler {

    /**
     * 处理
     * @param fullRequest request
     * @param ctx ctx
     */
    void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
