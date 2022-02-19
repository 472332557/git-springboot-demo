package com.liangzc.example.redis.nio.threadreactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiDisposeHandler implements Runnable{

    private SocketChannel socketChannel;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public MultiDisposeHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }


    @Override
    public void run() {
        dispatchRead();

    }

    private void dispatchRead() {
        executorService.submit(new MultiHandler());
    }


    class MultiHandler implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
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
}
