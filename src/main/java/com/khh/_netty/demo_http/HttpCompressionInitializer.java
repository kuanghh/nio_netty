package com.khh._netty.demo_http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.*;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 * 自动压缩Http消息
 */
public class HttpCompressionInitializer extends ChannelInitializer<Channel> {

    private final boolean isClient;

    public HttpCompressionInitializer(boolean isClient){
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if(isClient){
            pipeline.addLast("codec",new HttpClientCodec());
            //如果是客户端，则添加HttpContentDecompressor以处理来自服务器的压缩内容
            pipeline.addLast("decompressor",new HttpContentDecompressor());
        }else{
            pipeline.addLast("codec",new HttpServerCodec());
            //如果是服务端，则添加HttpContentCompressor来压缩数据（如果客户端支持的话）
            pipeline.addLast("compressor",new HttpContentCompressor());
        }

    }
}
