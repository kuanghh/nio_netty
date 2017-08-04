package com.khh._netty.demo_serialize.handler;

import com.khh._netty.demo_serialize.entity.Dept;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created by 951087952@qq.com on 2017/8/4.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("ServerHandler is active!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Dept dept;
        if(msg instanceof Dept){
             dept = (Dept)msg;
            System.out.println("从客户端收到的消息是 : " + msg.toString());
        }else {
            System.out.println("接受消息错误");
            return;
        }
        //收到消息之后返回给客户端一个新的部门消息
        Dept dept2 = new Dept(2,"dept2",new Date());
        ctx.write(dept2);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
