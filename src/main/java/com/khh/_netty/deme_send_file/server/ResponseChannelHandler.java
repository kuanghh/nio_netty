package com.khh._netty.deme_send_file.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * Created by 951087952@qq.com on 2017/8/2.
 */
@ChannelHandler.Sharable
public class ResponseChannelHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("正在写出....");
        super.write(ctx, msg, promise);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        System.out.println("正在绑定....");
        super.bind(ctx, localAddress, promise);
        System.out.println("绑定成功....");
    }
}
