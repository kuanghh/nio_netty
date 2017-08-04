package com.khh._netty.demo_guide;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 * 服务器正在处理一个客户端的请求，这个请求需要将它充当第三方客户端
 */
public class ShareChannel {

    public static void main(String[] args) {

        //创建ServerBootstrap以创建ServerSocketChannel，并绑定它
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {

                    ChannelFuture connectFuture;

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        Bootstrap b = new Bootstrap();
                        b.channel(NioSocketChannel.class).handler(new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                System.out.println("Received data");
                            }
                        });
                        b.group(ctx.channel().eventLoop());
                        connectFuture = b.connect(new InetSocketAddress("localhost",80));
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        if(connectFuture.isDone()){
                            //do someting with the data
                        }
                    }
                });
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8081));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("Server bound");
                }else{
                    System.out.println("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
