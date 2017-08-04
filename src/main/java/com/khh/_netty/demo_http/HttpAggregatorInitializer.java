package com.khh._netty.demo_http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 * 自动聚合Http消息片段
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {

    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient){
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if(isClient){
            pipeline.addLast("codec",new HttpClientCodec());
        }else{
            pipeline.addLast("codec",new HttpServerCodec());
        }

        /**
         * 将最大的消息大小为512KB的HttpObjectAggregator添加到ChannelPipeline
         */
        pipeline.addLast("aggregator",new HttpObjectAggregator(512 * 1024));

    }
}
