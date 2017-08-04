package com.khh._netty.demo_http.guide.try1;

import com.khh._netty.demo_http.HttpPipelineInitializer;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;


/**
 * Created by 951087952@qq.com on 2017/8/4.
 */
public class HttpServerInitializer extends ChannelInitializer{


    @Override
    protected void initChannel(Channel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));

//        pipeline.addLast(new HttpServerExpectContinueHandler());

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
//                    HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
                    FullHttpResponse response = new DefaultFullHttpResponse(((HttpRequest) msg).protocolVersion(),
                                    HttpResponseStatus.OK,
                                    Unpooled.wrappedBuffer("返回一个消息给你".getBytes()));

                    response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
                    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

                    boolean keepAlive = HttpUtil.isKeepAlive((HttpRequest)msg);

                    if(!keepAlive){
                        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                    }else{
                        response.headers().set(HttpHeaderNames.CONNECTION, new AsciiString("keep-alive"));
                        ctx.write(response);

                    }

                    System.out.println(response.toString());

                }else{
                    System.out.println("msg is not HttpRequest");
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
