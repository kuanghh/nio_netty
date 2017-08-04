package com.khh._netty.demo_serialize.handler;

import com.khh._netty.demo_serialize.entity.Dept;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * Created by 951087952@qq.com on 2017/8/4.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Dept> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Dept dept = new Dept(1,"dept1",new Date());
        System.out.println("激活成功..传输数据....");
        ctx.writeAndFlush(dept);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Dept msg) throws Exception {
        System.out.println("从服务端收到数据了:" + msg.toString());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
