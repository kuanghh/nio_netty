package com.khh._netty.demo_decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 */
public class ByteToCharDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("开始解码");
        while(in.readableBytes() >= 2){
            out.add(in.readChar());
        }
    }


}
