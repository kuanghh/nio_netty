package com.khh._nio;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * Created by FSTMP on 2017/7/25.
 *
 * Selector（选择器）是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。
 * 这样，一个单独的线程可以管理多个channel，从而管理多个网络连接。
 */
public class Example3 {


    public static void main(String[] args) {

        try {
            //创建一个Selector对象
            Selector selector = Selector.open();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
