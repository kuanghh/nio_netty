package com.khh._netty.practice_chatroom2;

import io.netty.channel.*;

/**
 * Created by 951087952@qq.com on 2017/8/7.
 */
public class MyWebSocketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**************************************/
        Channel channel = ctx.channel();
        GlobalChannel.group.add(ctx.channel());
        System.out.println(ctx.channel() + "  连接到服务器了  " + "id 为 :" + channel.id().asLongText());
        /**************************************/
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        GlobalChannel.group.remove(ctx.channel());
        System.out.println(ctx.channel() + " 退出了服务器..");
    }

}
