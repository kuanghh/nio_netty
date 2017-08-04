package com.khh._netty.demo_echo.use_codec.client;

import com.khh._netty.demo_echo.use_codec.codec.MyCombineCodec;
import com.khh._netty.demo_echo.use_codec.entity.Person;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 */
public class MyClient {

    public static void main(String[] args) throws Exception {

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        String address = "localhost";
        int port = 8081;

        try{
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(address,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new MyCombineCodec());

                            pipeline.addLast(new SimpleChannelInboundHandler<Person>() {

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    Person person = new Person(1000,"Xiaoming",10);
                                    System.out.println("正在发送到服务端的消息是:" + person.toString());
                                    ctx.writeAndFlush(person);

                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, Person msg) throws Exception {
                                    System.out.println("从服务端收到的信息是：" + msg.toString());
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    cause.printStackTrace();
                                    ctx.close();
                                }
                            });
                        }
                    });

            ChannelFuture future = bootstrap.connect().sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();
        }



    }
}
