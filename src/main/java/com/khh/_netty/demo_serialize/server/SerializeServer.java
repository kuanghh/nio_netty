package com.khh._netty.demo_serialize.server;

import com.khh._netty.demo_serialize.handler.ServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 951087952@qq.com on 2017/8/4.
 */
public class SerializeServer {

    public static void main(String[] args) throws Exception{

        int port = 8008;

        ServerBootstrap bootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup child = new NioEventLoopGroup();

        try{

            bootstrap.group(boss,child)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ServerHandlerInitializer());

            ChannelFuture future = bootstrap.bind().sync();

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isDone()){
                        System.out.println("Server 启动成功");
                    }else{
                        System.out.println("Server 启动失败");
                    }
                }
            });

            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully().sync();
            child.shutdownGracefully().sync();
        }

    }
}
