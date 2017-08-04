package com.khh._netty.demo_combine_codec;

import com.khh._netty.demo_decoder.ByteToCharDecoder;
import com.khh._netty.demo_encoder.CharToByteEncoder;
import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Created by 951087952@qq.com on 2017/8/3.
 */
public class CombineByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder,CharToByteEncoder> {

    public CombineByteCharCodec(){
        super(new ByteToCharDecoder(),new CharToByteEncoder());
    }
}
