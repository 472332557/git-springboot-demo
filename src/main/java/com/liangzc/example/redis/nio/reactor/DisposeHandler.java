package com.liangzc.example.redis.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class DisposeHandler implements Runnable{

    private SocketChannel socketChannel;

    public DisposeHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        System.out.println("---------读事件----------");
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            System.out.println("server receive message:"+new String(byteBuffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
