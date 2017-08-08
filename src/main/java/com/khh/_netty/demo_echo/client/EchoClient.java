package com.khh._netty.demo_echo.client;

import com.khh._netty.demo_combine_codec.CombineByteCharCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 951087952@qq.com on 2017/7/31.
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            //创建Bootstrap
            Bootstrap b = new Bootstrap();
            b.group(group)//指定EventLoopGroup以处理客户端时间，需要适用于NIO的实现
                    .channel(NioSocketChannel.class)//适用于NIO传输的Channel类
                    .remoteAddress(new InetSocketAddress(host,port))//设置服务器的InetSocketAddress
                    .handler(new ChannelInitializer<SocketChannel>() {//在创建Channel时，向ChannelPipeline中添加一个EchoClientHandler实例
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();//连接到远程节点，阻塞等待知道连接完成
            f.channel().closeFuture().sync();//阻塞，知道Channel关闭
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();//关闭线程池并且释放所有的资源
        }

    }

    public static void main(String[] args) throws Exception{
        String host = "localhost";
        int port = 8081;
        new EchoClient(host,port).start();
    }
}
