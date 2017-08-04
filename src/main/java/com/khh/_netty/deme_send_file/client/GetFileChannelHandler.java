package com.khh._netty.deme_send_file.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by 951087952@qq.com on 2017/8/1.
 */
@ChannelHandler.Sharable
public class GetFileChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

    ByteBuf byteBuf = Unpooled.buffer();

    public GetFileChannelHandler(){
        System.out.println("123456789");
    }
    /**
     * 收到服务器的信息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("我收到了服务器的信息了");
        byteBuf = Unpooled.copiedBuffer(byteBuf,msg);
        System.out.println("byteBuf length " + byteBuf.readableBytes());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("这是最后一条消息结束了");
        long l = System.currentTimeMillis();
        String filePath = "D:/";
        String fileName =  l + "_.jpg";
        OutputStream out = new FileOutputStream(filePath + fileName);


        int length = byteBuf.readableBytes();
        byte[] array = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(),array);
        System.out.println("array length : " + array.length);
        out.write(array);
        ctx.close();
    }

    /**
     * 连接成功
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client 连接成功，发送消息.....");
        ctx.writeAndFlush(Unpooled.copiedBuffer("我想拿个图片",CharsetUtil.UTF_8));
    }

    /**
     * 捕获异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();

    }
}
