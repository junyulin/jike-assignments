package io.github.linjn.outbound;

import io.github.linjn.client.MyHttpClient;
import io.github.linjn.route.RandomRouter;
import io.github.linjn.route.Router;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第三周作业
 *
 * @author LinJn
 * @since 2021/5/23 21:03
 */
public class HttpOutboundHandler implements OutboundHandler {

    private final List<String> proxyServer;

    private final MyHttpClient httpClient;

    private final Router router;

    public HttpOutboundHandler(List<String> proxyServer) {
        this.proxyServer = proxyServer;
        this.httpClient = new MyHttpClient();
        this.router = new RandomRouter();
    }

    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        // 获取路由的地址
        String route = router.route(this.proxyServer);
        System.out.println("路由的地址：" + route);
        FullHttpResponse response = null;
        try {
            HttpHeaders headers = fullRequest.headers();
            // 发送 http 请求获取数据
            String body = httpClient.getAsString(route, headerToList(headers));
            byte[] bytesArray = body.getBytes("UTF-8");
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(bytesArray));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", bytesArray.length);
        } catch (IOException e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
            ctx.close();
        } finally {
            // 释放资源
            if (fullRequest != null) {
                if(null == response){
                    response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
                }
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }

    public static List<Map<String, String>> headerToList(HttpHeaders headers) {
        List<Map<String, String>> list = new ArrayList<>(headers.size());
        for (Map.Entry<String, String> header : headers) {
            Map<String, String> map = new HashMap<>(16);
            map.put("name", header.getKey());
            map.put("value", header.getValue());
            list.add(map);
        }
        return list;
    }
}
