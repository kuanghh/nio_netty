package com.khh._netty.deme_send_file.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 951087952@qq.com on 2017/8/1.
 */
public class GetFileClient {

    private final int port;
    private final String address;

//    private static GetFileChannelHandler channelHandler = new GetFileChannelHandler();//用来测试线程是否安全的

    public GetFileClient(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public void start() throws Exception{

        EventLoopGroup group = new NioEventLoopGroup();
        GetFileChannelHandler channelHandler = new GetFileChannelHandler();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(address,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(channelHandler);
                        }
                    });
            ChannelFuture f = bootstrap.connect().sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new GetFileClient(8002,"localhost").start();
//        new GetFileClient(8002,"localhost").start();
    }
}
