package com.khh._netty.demo_echo.use_codec.codec;

import com.khh._netty.demo_echo.use_codec.entity.Person;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 */
public class PersonToByteEncoder extends MessageToByteEncoder<Person> {

    public PersonToByteEncoder(){
        System.out.println("PersonToByteEncoder  created");
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {
        System.out.println("开始编码...");
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try{

            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(msg);

            byte[] personByte = bos.toByteArray();

            System.out.println("对象的字节长度为: " + personByte.length);
            out.writeBytes(personByte);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bos.close();
            oos.close();
        }

    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
        ReferenceCountUtil.release(msg);//手动释放资源，不然会报错 io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
