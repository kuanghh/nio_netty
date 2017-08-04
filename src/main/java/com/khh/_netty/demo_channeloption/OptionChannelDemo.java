package com.khh._netty.demo_channeloption;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 */
public class OptionChannelDemo {

    public static void main(String[] args) throws Exception{
        //创建一个AttributeKey以标识该属性
        final AttributeKey<Integer> id = AttributeKey.newInstance("ID");

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChannelInboundHandler<ByteBuf>() {

                        /**
                         * 注册到channel时，并且能够被I/O调用时，调用改方法
                         *
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                            //使用AttributeKey检索属性以及它的值
                            Integer integer = ctx.channel().attr(id).get();
                            System.out.println("id is :" + integer);
//                            ctx.writeAndFlush(Unpooled.copiedBuffer("hello my id is" + integer, CharsetUtil.UTF_8));  //这句话服务端不会接受得到
                        }

                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            Integer integer = ctx.channel().attr(id).get();
                            System.out.println("111");
                            ctx.writeAndFlush(Unpooled.copiedBuffer("hello my id is " + integer, CharsetUtil.UTF_8));
                        }

                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                            System.out.println("Received data");
                            System.out.println(msg.toString(CharsetUtil.UTF_8));
                        }

                    });
            //设置ChannelOption，其将在connect()或者bind()方法被调用时被设置到已经创建的Channel上
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);


            bootstrap.attr(id, 123456);//存储该id属性
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8081));
            future.syncUninterruptibly();

//            future.sync();

            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();//关闭线程池并且释放所有的资源
        }

    }

}
