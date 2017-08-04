package com.khh._netty.demo_http.guide.try1;

import com.khh._netty.demo_http.HttpPipelineInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Created by 951087952@qq.com on 2017/8/4.
 */
public class HttpServerInitializer extends HttpPipelineInitializer {
    public HttpServerInitializer(boolean client) {
        super(client);
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        super.initChannel(ch);

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new ChannelInboundHandlerAdapter(){

            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                System.out.println("连接成功..");
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if(msg instanceof HttpRequest){
                    System.out.println("msg is HttpRequest");
                    //返回一个HttpResponse消息
                }else{
                    System.out.println("msg is not HttpRequest");//这个,也不是FullHttpRequest
                }
            }

            @Override
            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                ctx.flush();
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                cause.printStackTrace();
                ctx.close();
            }
        });

    }


}
