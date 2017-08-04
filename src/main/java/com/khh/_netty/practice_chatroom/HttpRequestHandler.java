package com.khh._netty.practice_chatroom;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.spdy.SpdyHeaders;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

/**
 * Created by 951087952@qq.com on 2017/8/4.
 * 提供用于访问聊天室并显示由连接的客户端发送的消息的网页
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    private static final File INDEX;

    static{
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try{
            String path = location.toURI() + "index.html";
            path = !path.contains("file:") ? path : path.substring(5);
            INDEX = new File(path);
//            System.out.println(path);

        }catch (Exception e){
            throw new IllegalStateException("Unable to locate index.html");
        }
    }

    public HttpRequestHandler(String wsUri){
        this.wsUri = wsUri;
    }

    /**
     * 将任何目标的URI转发为/ws请求
     * @param ctx
     * @param request
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //如果请求了WebSocket协议升级，则增加引用计数(调用retain()方法),并将它传递给下一个ChannelInboundhandler
        if(wsUri.equalsIgnoreCase(request.uri())){
            ctx.fireChannelRead(request.retain());
        }else{
            //处理100Continue请求以符合Http1.1规范
            if(HttpUtil.is100ContinueExpected(request)){
                send100Continue(ctx);
            }

            RandomAccessFile file = new RandomAccessFile(INDEX,"r");//读取index.html
            HttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(),HttpResponseStatus.OK);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            if(keepAlive){//如果请求了keep-alive,则添加所需要的HTTP头信息
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH,file.length());
                response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
            }

            //将response写入到客户端
            ctx.write(response);

            ctx.write(new DefaultFileRegion(file.getChannel(),0,file.length()));
            //写LastHttpContent并冲刷至客户端
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

            if(!keepAlive){//如果没有请求keep-alive,则在写操作完成后关闭Channel
                future.addListener(ChannelFutureListener.CLOSE);
            }

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private static void send100Continue(ChannelHandlerContext ctx){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    public static void main(String[] args) {
        URL url = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(url.getPath());
    }
}
