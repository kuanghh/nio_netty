package com.khh._nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

/**
 * Created by FSTMP on 2017/7/25.
 *
 *  Scatter/Gather
 *
 * 分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。
 * 因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。
 *
 * 聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，
 * 因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。
 */
public class Example2 {


    public static void scatter(FileChannel inChannel) throws Exception{
        ByteBuffer buff1 = ByteBuffer.allocate(48);
        ByteBuffer buff2 = ByteBuffer.allocate(50);

        ByteBuffer[] buffs = new ByteBuffer[]{buff1,buff2};

        long read = inChannel.read(buffs);

        while(read != -1){
            buff1.flip();
            buff2.flip();
            while(buff1.hasRemaining()){
                System.out.print((char)buff1.get());
            }
            while(buff2.hasRemaining()){
                System.out.print((char)buff2.get());
            }
            buff1.clear();
            buff2.clear();
            read = inChannel.read(buffs);
        }
        inChannel.close();
    }

    public static void gather(ByteBuffer[] buff) throws Exception{

        URL url = Example1.class.getClassLoader().getResource("_nio/test1.txt");
        File file = new File(url.getFile());

        RandomAccessFile aFile = new RandomAccessFile(file,"rw");
        FileChannel inChannel = aFile.getChannel();

        for(ByteBuffer buffer : buff){
            buffer.flip();
            System.out.println("buffer.position :" + buffer.position());
            System.out.println("buffer.remaining :" + buffer.remaining());
        }
        inChannel.write(buff);

        inChannel.close();
    }

    public static void main(String[] args) {

        try {
//            URL url = Example1.class.getClassLoader().getResource("_nio/test.txt");
//            File file = new File(url.getFile());
//            RandomAccessFile aFile = new RandomAccessFile(file,"rw");
//            FileChannel inChannel = aFile.getChannel();
//            scatter(inChannel);


            ByteBuffer buff1 = ByteBuffer.allocate(2);
            ByteBuffer buff2 = ByteBuffer.allocate(2);
            ByteBuffer buff3 = ByteBuffer.allocate(2);

            buff1.put((byte)'a');
            buff1.put((byte)'b');
            buff2.put((byte)1);
            buff2.put((byte)2);
            buff3.put((byte)3);
            buff3.put((byte)4);

            gather(new ByteBuffer[]{buff1,buff2,buff3});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
