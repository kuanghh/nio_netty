package com.khh._netty.practice_chatroom2;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 951087952@qq.com on 2017/8/7.
 */
public class GlobalChannel  {

    /**
     * key :    id
     * value:   channel
     */
    public static Map<String,Channel> channelMap = new ConcurrentHashMap<>();

    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


}
