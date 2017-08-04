package com.khh._netty.demo_http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 * 添加Http支持
 */
public class HttpPipelineInitializer extends ChannelInitializer {

    private final boolean client;

    public HttpPipelineInitializer(boolean client){
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        if(client){
            //如果是客户端，则添加HttpRespnseDecoder以处理来自服务器的响应
            pipeline.addLast("decoder", new HttpResponseDecoder());
            //如果是客户端，则添加HttpRequestEncoder以向服务器发送请求
            pipeline.addLast("encoder",new HttpRequestEncoder());


        }else{
            //如果是服务端，则添加HttpRequestDecoder以处理来自客户端的请求
            pipeline.addLast("decoder",new HttpRequestDecoder());
            //如果是服务端，则添加HttpResponseEncoder以向客户端发送响应
            pipeline.addLast("encoder",new HttpResponseEncoder());
        }

    }
}
