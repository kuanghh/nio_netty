package com.khh._netty.demo_websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * Created by 951087952@qq.com on 2017/8/4.
 * 在服务器端支持WebSocket
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(65536),//为握手提供聚合的HttpRequest
                new WebSocketServerProtocolHandler("/websocket"),//如果被请求的端点是"/websocket"，则处理该升级握手
                new TextFrameHandler(),//TextFrameHandler处理TextWebSocketFrame
                new BinaryFrameHandler(),//BinaryFrameHandler处理BinaryWebSocketFrame
                new ContinuationFrameHandler());//ContinuationFrameHandler处理ContinuationWebSocketFrame

    }

    /**
     * 处理text frame
     */
    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        }
    }

    /**
     * 处理 binary frame
     */
    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {

        }
    }

    /**
     * 处理Continuation frame
     */
    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {

        }
    }



}
