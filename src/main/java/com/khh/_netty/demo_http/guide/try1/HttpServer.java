package com.khh._netty.demo_http.guide.try1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 951087952@qq.com on 2017/8/4.
 * 尝试就发布服务端的http服务，看能否用浏览器打开
 */
public class HttpServer {

    public static void main(String[] args) throws Exception{

        String address = "localhost";
        int port = 8080;

        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try{

            bootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(address,port))
                    .childHandler(new HttpServerInitializer(false));

            ChannelFuture future = bootstrap.bind().sync();

            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
        }


    }

}
