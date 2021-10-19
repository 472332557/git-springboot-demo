package com.liangzc.example.gitspringbootdemo.redis.protogenic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CustomerRedisClientSocket {

    private Socket socket;

    private InputStream inputStream;

    private OutputStream outputStream;

    public CustomerRedisClientSocket(String ip,Integer port){
        try {
            socket = new Socket(ip, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void send(String cmd){
        try {
            outputStream.write(cmd.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read(){
        int cout = 0;
        byte[] bytes = new byte[1024];
        try {
            cout = inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(bytes, 0, cout);
    }
}
