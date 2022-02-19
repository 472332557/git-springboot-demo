package com.liangzc.example.redis.nio.threadreactor;

import com.liangzc.example.redis.nio.reactor.DisposeHandler;
import lombok.SneakyThrows;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class MultiAcceptor implements Runnable{

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public MultiAcceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @SneakyThrows
    @Override
    public void run() {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ,new MultiDisposeHandler(socketChannel));//客户端注册为读事件
    }
}
