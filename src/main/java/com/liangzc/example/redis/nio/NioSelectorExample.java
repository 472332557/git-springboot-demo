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
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorExample implements Runnable{

    Selector selector;

    ServerSocketChannel serverSocketChannel;

    public NioSelectorExample(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//必须配置为非阻塞
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//将连接请求注册到多路复用器上
    }


    //启动一个线程去监听多路复用器注册的客户端channel（客户端的连接或者IO请求）
    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                selector.select();//阻塞等待事件就绪
                Set<SelectionKey> selectionKeys = selector.selectedKeys();//事件列表
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    disposeRegister(selectionKey);
                    iterator.remove();//移除当前就绪的事件
                }
            } catch (IOException e) {
                e.printStackTrace();
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
            ByteBuffer byteBuffer = ByteBuffer.wrap("发送信息".getBytes(StandardCharsets.UTF_8));
            socketChannel.write(byteBuffer);
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        NioSelectorExample nioSelectorExample = new NioSelectorExample(8888);
        Thread.sleep(2000);
        new Thread(nioSelectorExample).start();
    }
}
