package com.khh._nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by FSTMP on 2017/7/25.
 *
 * 这些是Java NIO中最重要的通道的实现：
 *
 *        FileChannel  : 从文件中读写数据
 *        DatagramChannel   :  能通过UDP读写网络中的数据
 *        SocketChannel     :  能通过TCP读写网络中的数据
 *        ServerSocketChannel   :  可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChanne
 */
public class Example1 {

    public static void main(String[] args) {

        try {

            URL url = Example1.class.getClassLoader().getResource("_nio/test.txt");
            File file = new File(url.getFile());

            RandomAccessFile aFile = new RandomAccessFile(file,"rw");
            FileChannel inChannel = aFile.getChannel();


            /**
             *
             * Buffer 拥有3个属性：
             *      position:  当你写数据进buffer的时候，position表示当前的位置，其一开始默认的状态为0，当读入一个byte后，
             *              position会向前移动一个可插入的Buffer单元
             *                  当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0，
             *                  当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。
             *
             *      limit:在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
             *            当切换Buffer到读模式时， limit表示你最多能读到多少数据。
             *
             *      capacity:Buffer的固定的大小值，一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据
             *
             *
             */

            //分配48个字节给buff
            ByteBuffer buff = ByteBuffer.allocate(48);

            int byteRead = inChannel.read(buff);//从Channel写数据到Buffer
            while(byteRead != -1){
                System.out.println("Read " + byteRead);
                buff.flip();                    //flip方法将Buffer从写模式切换到读模式

                while(buff.hasRemaining()){ //buff里面还有多少数据
                    System.out.print((char)buff.get());//读取中文会出现乱码
                }

//                byte[] array = buff.array();
//                System.out.println(new String(array,"UTF-8"));
                buff.clear();//清空buff里面的所有数据
                byteRead = inChannel.read(buff);
            }

            aFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
