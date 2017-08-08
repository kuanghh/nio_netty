package com.khh._netty.demo_echo.server;

import com.khh._netty.demo_combine_codec.CombineByteCharCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 951087952@qq.com on 2017/7/31.
 *
 * 服务器实现中的重要步骤：
 *
 * 1、EchoServerHandler实现了业务逻辑
 * 2、main（）方法引导（启动）服务器：
 *       引导过程所需要的步骤如下：
 *          （1）创建一个ServerBootstrap的实例以引导和绑定服务器
 *          （2）创建并分配一个NioEventLoopGroup实例以进行事件的处理，如接受新连接以及读/写数据
 *          （3）指定服务器绑定的本地的InetSocketAddress
 *          （4）指定一个EchoChannelHandler的实例初始化一个新的Channel
 *          （5）调用ServerBootstrap.bind（）方法以绑定服务器
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public void start() throws Exception{

        final EchoServerHandler serverHandler = new EchoServerHandler();
        //创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();


        try{
            //创建ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)//指定所使用的NIO传输Channel
                    .localAddress(new InetSocketAddress(port))//使用指定的端口设置套接字地址
                    .childHandler(new ChannelInitializer<SocketChannel>() {//添加一个EchoServerHandler到子Channel的ChannelPipeline
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //EchoServerHandler被标注为@Shareable,所以我们可以总是使用同样的实例
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            //异步地绑定服务器，调用sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            //获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            //关闭EventLoopGroup,释放所有的资源
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception{

        int port = 8081;

        new EchoServer(port).start();
    }
}
