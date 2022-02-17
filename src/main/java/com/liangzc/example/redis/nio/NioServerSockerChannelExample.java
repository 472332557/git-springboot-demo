package com.liangzc.example.redis.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioServerSockerChannelExample {

    private static ServerSocketChannel serverSocketChannel;

    public NioServerSockerChannelExample(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//连接非阻塞
        serverSocketChannel.bind(new InetSocketAddress(port));
    }



    public void test() throws IOException, InterruptedException {
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();//获得客户端连接
            //I/O非阻塞
//            socketChannel.configureBlocking(false);
            if (socketChannel != null){
                System.out.println("有客户端连接------");
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(byteBuffer);//此时IO还是阻塞的，必须先连接的客户端，IO操作完成后，才会执行后面连接的IO
                System.out.println("server receive msg:"+new String(byteBuffer.array()));
            }else {
                Thread.sleep(1000);
                System.out.println("连接未就绪！");
            }
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        NioServerSockerChannelExample example = new NioServerSockerChannelExample(8888);
        example.test();

    }

}
