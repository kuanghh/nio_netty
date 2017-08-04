package com.khh._netty.demo_encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 * 将short类型转化成byte类型
 */
public class ShortToByteEncoder extends MessageToByteEncoder<Short> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out) throws Exception {
        out.writeShort(msg);//将short写入ByteBuf当中
    }
}
