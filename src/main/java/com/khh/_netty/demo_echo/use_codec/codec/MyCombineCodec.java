package com.khh._netty.demo_echo.use_codec.codec;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 * 编解码器
 */
public class MyCombineCodec extends CombinedChannelDuplexHandler<ByteToPersonDecoder,PersonToByteEncoder> {

    public MyCombineCodec(){
        super(new ByteToPersonDecoder(),new PersonToByteEncoder());
    }
}
