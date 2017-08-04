package com.khh._netty.demo_echo.use_codec.server;

import com.khh._netty.demo_echo.use_codec.codec.MyCombineCodec;
import com.khh._netty.demo_echo.use_codec.entity.Person;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 */
public class MyServer {

    public static void main(String[] args) throws Exception{

        int port = 8081;

        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            bootstrap.group(group,new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new MyCombineCodec());

                            pipeline.addLast(new ChannelInboundHandlerAdapter(){

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    if(msg instanceof Person){
                                        System.out.println("接受到信息了" + msg.toString());
                                    }else{
                                        System.out.println("接受失败....");
                                    }
                                    Person p = new Person(1001,"Mike",5);
//                                    ctx.writeAndFlush(p);
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                    //将未决消息冲刷到远程节点，并且关闭该Channel
                                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    ctx.close();
                                    cause.printStackTrace();
                                }
                            });

                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();
        }



    }
}
