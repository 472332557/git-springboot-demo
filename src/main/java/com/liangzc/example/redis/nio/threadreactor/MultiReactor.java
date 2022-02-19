package com.liangzc.example.redis.nio.threadreactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 *  多线程reactor模型：
 *  reactor模型：对事件分发和IO处理做了一个分离，单线程reactor模型，不是一个新技术，而是一个设计思想，将事件分发交由reactor来处理，
 *  handler负责处理IO请求，单线程的意思是：处理IO的handler是单个线程处理的，所以一旦线程遇到阻塞，则会使IO请求处理阻塞，
 *  所以多线程reactor模型对此做了优化：就是处理IO的handler采用线程池的方式，也就是多线程的方式去处理，即使一个线程阻塞，也不会影响其他线程处理IO请求
 */

public class MultiReactor implements Runnable{

    Selector selector;

    ServerSocketChannel serverSocketChannel;

    public MultiReactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//必须配置为非阻塞
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT,new MultiAcceptor(selector,serverSocketChannel));//将连接请求注册到多路复用器上
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                selector.select();//阻塞监听获得事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    dispatcherKey(iterator.next());
                    iterator.remove();//移除事件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatcherKey(SelectionKey selectionKey) {
        Runnable runnable = (Runnable) selectionKey.attachment();
        if (runnable != null){
            runnable.run();
        }
    }
}
