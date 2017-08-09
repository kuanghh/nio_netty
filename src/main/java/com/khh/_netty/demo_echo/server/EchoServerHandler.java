package com.khh._netty.demo_echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by 951087952@qq.com on 2017/7/31.\
 * 通过ChannelHandler实现服务端逻辑
 */
//@ChannelHandler.Sharable//标示一个ChannelHandler可以被多个Channel安全地共享
/**
 * 这里详细说一下，因为EchoServer中，在ChannelInitializer外的地方定义了这个EchoServerHandler，而且只有一个，随后并放进了pipeline中
 * ,也就是所有连进来的Channel都会共享这一个EchoServerHandler，所以必须加上@ChannelHandler.Sharable，
 * 如果没有加上，则在第二个连进来的Channel会报错，
 * io.netty.channel.ChannelPipelineException: com.khh._netty.demo_echo.server.EchoServerHandler is not a @Sharable handler
 * , so can't be added or removed multiple times.
 *
 * 但是在EchoClient中，在ChannelInitializerr内中定义了EchoClientHandler，每个客户端启动的时候，都会用不一样的EchoClientHandler
 * ，此时@ChannelHandler.Sharable，加不加也无所谓了，因为线程是安全的
 *
 * 其次是,如果在EchoServer中，在ChannelInitializer内的地方定义了这个EchoServerHandler，那么每个新的客户端连接到服务端的时候
 * 都会用不同的EchoServerHanler，这样的线程是安全的，所以加不加@ChannelHandler.Sharable，也不重要了
 *
 * 总结：也就是当确定了线程是安全的时候，可以不用加上@ChannelHandler.Sharable注解
 *         当线程是不安全的情况下，可以根据业务逻辑，加上@ChannelHandler.Sharable注解
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter{


//    int count = 0;
    /**
     *  对于每个传入的消息都要调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        System.out.println("client count " + (count++));

        ByteBuf in = (ByteBuf)msg;
        System.out.println("server received : " + in.toString(CharsetUtil.UTF_8));//将消息纪录到控制台

        //将接受到的消息写给发送者，而不冲刷出站消息
        ctx.write(in);
    }

    /**
     * 通知ChannelInboundHandler最后一次对ChannelRead的调用时当前批量读取中的最后一条消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将未决消息冲刷到远程节点，并且关闭该Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//打印异常栈跟踪
        ctx.close();//关闭该Channel
    }
}
