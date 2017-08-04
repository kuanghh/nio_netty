package com.khh._netty.deme_send_file.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 951087952@qq.com on 2017/8/1.
 */
public class SendFileServer {

    private final int port;

    public SendFileServer(int port){
        this.port = port;
    }


    public void start()throws  Exception{

        EventLoopGroup group = new NioEventLoopGroup();
        SendFileChannelHandler sendFileChannelHandler = new SendFileChannelHandler();
        ResponseChannelHandler responseChannelHandler = new ResponseChannelHandler();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("SendFileServer add SendFileChannelHandler");
//                            ch.pipeline().addLast(responseChannelHandler);//放在这里有输出
                            ch.pipeline().addLast(sendFileChannelHandler);
//                            ch.pipeline().addLast(responseChannelHandler);//放在这里就没有输出
                        }
                    });
            ChannelFuture f = serverBootstrap.bind().sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭EventLoopGroup,释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception{
        new SendFileServer(8002).start();
    }
}
