package com.khh._netty.demo_echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by 951087952@qq.com on 2017/7/31.\
 * 通过ChannelHandler实现服务端逻辑
 */
@ChannelHandler.Sharable//标示一个ChannelHandler可以被多个Channel安全地共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    /**
     *  对于每个传入的消息都要调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf in = (ByteBuf)msg;
        System.out.println("server received : " + in.toString(CharsetUtil.UTF_8));//将消息纪录到控制台

        //将接受到的消息写给发送者，而不冲刷出站消息
        ctx.write(in);
    }

    /**
     * 通知ChannelInboundHandler最后一次对ChannelRead的调用时当前批量读取中的最后一条消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将未决消息冲刷到远程节点，并且关闭该Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//打印异常栈跟踪
        ctx.close();//关闭该Channel
    }
}
