package com.khh._netty.demo_serialize.client;

import com.khh._netty.demo_serialize.handler.ClientHandlerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 951087952@qq.com on 2017/8/4.
 */
public class SerializeClient {

    public static void main(String[] args) throws Exception{

        int port = 8008;
        String address = "localhost";

        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup();

        try{

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(address,port))
                    .handler(new ClientHandlerInitializer());


            ChannelFuture future = bootstrap.connect().sync();

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isDone()){
                        System.out.println("Client 启动成功");
                    }else{
                        System.out.println("Client 启动失败");
                    }
                }
            });

            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();
        }


    }
}
