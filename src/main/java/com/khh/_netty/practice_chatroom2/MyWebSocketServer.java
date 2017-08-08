package com.khh._netty.practice_chatroom2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 951087952@qq.com on 2017/8/7.
 */
public class MyWebSocketServer {

    private static int port = 8081;

    /**
     * 开启服务端
     * @throws Exception
     */
    public void start(int port) throws Exception{

        NioEventLoopGroup boss = new NioEventLoopGroup(1);

        NioEventLoopGroup child = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        try{

            bootstrap.group(boss,child)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new MyWebSocketHandlerInitializer());

            ChannelFuture future = bootstrap.bind().sync();
            Channel serverChannel = future.channel();

            GlobalChannel.channelMap.put(serverChannel.id().asLongText(),serverChannel);

            serverChannel.closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully().sync();
            child.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception{
        new MyWebSocketServer().start(MyWebSocketServer.port);
    }
}
