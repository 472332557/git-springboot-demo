package com.liangzc.example.redis.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorExample implements Runnable{

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    public NioSelectorExample(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//将连接请求注册到多路复用器上
    }


    //启动一个线程去监听多路复用器注册的客户端channel（客户端的连接或者IO请求）
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            if(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                disposeRegister(selectionKey);
            }
        }
    }

    private void disposeRegister(SelectionKey selectionKey) {


    }
}
