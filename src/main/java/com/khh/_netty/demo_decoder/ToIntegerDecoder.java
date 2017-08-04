package com.khh._netty.demo_decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 * 将字节转换成Int类型的解码器
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() >= 4){//检查是否至少有4个字节可读，因为int类型字节长度为4
            out.add(in.readInt());//从入站ByteBuf中读取一个int，并将其添加到解码消息的List中
        }
    }
}
