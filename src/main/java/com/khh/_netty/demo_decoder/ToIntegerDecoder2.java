package com.khh._netty.demo_decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * ReplayingDecoder 稍微慢于 ByteToMessageDecoder
 * Created by 951087952@qq.com on 2017/8/3.
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> {

    /**
     *
     * @param ctx
     * @param in  这里传入的 ByteBuf 是 ReplayingDecoderByteBuf
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //这里和之前一样，从ByteBuf中提取的int将会被加入到List中，如果没有足够的字节可用，这个方法将会抛出一个signal
        out.add(in.readInt());
    }
}
