package com.khh._netty.demo_echo.use_codec.codec;

import com.khh._netty.demo_echo.use_codec.entity.Person;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;


import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 */
public class ByteToPersonDecoder extends ByteToMessageDecoder{



    /**
     * 报错io.netty.handler.codec.DecoderException: ByteToPersonDecoder.decode() did not read anything but decoded a message.
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("开始解码...");
        Object obj = null;

        if(!in.hasArray()) {
            int length = in.readableBytes();
            byte[] arry = new byte[length];
            in.getBytes(in.readerIndex(), arry);

            ByteArrayInputStream bis = new ByteArrayInputStream(arry);
            ObjectInputStream ois = new ObjectInputStream(bis);

            obj = ois.readObject();
            out.add(obj);
            System.out.println("传过来的是直接缓冲区....");
        }


    }

    /**
     * 问题出在这里
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        super.channelRead(ctx,msg);//这里报错
//        ctx.fireChannelRead(msg);
//        ReferenceCountUtil.release(msg);//释放消息
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
