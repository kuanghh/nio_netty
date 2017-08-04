package com.khh._netty.deme_send_file.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.io.InputStream;

/**
 * Created by 951087952@qq.com on 2017/8/1.
 */
@ChannelHandler.Sharable
public class SendFileChannelHandler extends ChannelInboundHandlerAdapter {

    /**
     * 对于每个传入的消息都要调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("有客户端发来请求");

        InputStream in = SendFileChannelHandler.class.getClassLoader().getResourceAsStream("_netty/mysql_.jpg");

        ByteBuf buf = (ByteBuf)msg;
        System.out.println("server received : " + buf.toString(CharsetUtil.UTF_8));//将消息纪录到控制台

        byte[] buff = new byte[4096];
        ByteBuf byteBuf = null;
        while(in.read(buff) != -1){
            if(byteBuf == null){
                byteBuf = Unpooled.copiedBuffer(buff);
                continue;
            }
            int length = byteBuf.readableBytes();
            byte[] array = new byte[length];
            byteBuf.getBytes(byteBuf.readerIndex(),array);
            byteBuf = Unpooled.copiedBuffer(array,buff);
        }
        System.out.println("一次性输出所有...");
        ctx.write(byteBuf);
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
        System.out.println("server channelReadComplete ChannelHandlerContext close");
    }

    /**
     * 出现异常的时候
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
