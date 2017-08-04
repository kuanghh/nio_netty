package com.khh._netty.demo_echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by 951087952@qq.com on 2017/7/31.
 *
 * 通过ChannelHandler实现客户端逻辑
 */
@ChannelHandler.Sharable //标示该类的实例可以被多个Channel共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 当从服务器接收到一条消息时被调用
     * @param context
     * @param byteBuf
     * @throws Exception
     */

    @Override
    public void channelRead0(ChannelHandlerContext context, ByteBuf byteBuf) throws Exception {
        //记录已接收消息的转储
        System.out.println("Client received : " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 在到服务器的连接已经建立之后被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当被通知Channel是活跃状态的时候，发送一条消息
//        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
        ctx.writeAndFlush(Unpooled.copiedBuffer("中文来了...",CharsetUtil.UTF_8));
    }

    /**
     * 在处理过程中引发异常时被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //在发生异常的时候，记录错误的信息，并且关闭Channel
        cause.printStackTrace();
        ctx.close();
    }
}
