package com.khh._netty.demo_http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;


/**
 * Created by 951087952@qq.com on 2017/8/3.
 * 使用Https
 */
public class HttpsCodecinitializer extends ChannelInitializer<Channel> {
    private final SslContext sslContext;
    private final boolean isClient;

    public HttpsCodecinitializer(SslContext sslContext, boolean isClient) {
        this.sslContext = sslContext;
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine sslEngine = sslContext.newEngine(ch.alloc());
        pipeline.addFirst("ssl",new SslHandler(sslEngine));
        if(isClient){
            pipeline.addLast("codec",new HttpClientCodec());
        }else{
            pipeline.addLast("codec",new HttpServerCodec());
        }

    }
}
