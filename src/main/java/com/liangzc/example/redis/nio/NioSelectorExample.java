package com.liangzc.example.redis.nio;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorExample implements Runnable{

    private static Selector selector;

    private ServerSocketChannel serverSocketChannel;

    public NioSelectorExample(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);//必须配置为非阻塞
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//将连接请求注册到多路复用器上
    }


    //启动一个线程去监听多路复用器注册的客户端channel（客户端的连接或者IO请求）
    @SneakyThrows
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

    private void disposeRegister(SelectionKey selectionKey) throws IOException {

        if(selectionKey.isAcceptable()){            //连接事件
            System.out.println("---------连接事件----------");
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);//客户端注册为读事件
        }else if (selectionKey.isReadable()){        //读事件
            System.out.println("---------读事件----------");
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            System.out.println("server receive message:"+new String(byteBuffer.array()));
        }else if (selectionKey.isWritable()){       //写事件
            System.out.println("---------写事件----------");
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.write(byteBuffer);
        }
    }


    public static void main(String[] args) throws IOException {
        NioSelectorExample nioSelectorExample = new NioSelectorExample(8888);
        new Thread(nioSelectorExample).start();
    }
}
