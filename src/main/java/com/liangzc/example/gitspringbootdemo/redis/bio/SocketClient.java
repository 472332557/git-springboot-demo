package com.liangzc.example.gitspringbootdemo.redis.bio;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("192.168.1.70",8888);

        //给服务端发送消息
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write("this is a socket-client message");
        bufferedWriter.newLine();
        bufferedWriter.flush();

        //收到服务端发送的消息
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String readLine = bufferedReader.readLine();
        System.out.println("收到服务端发送的消息：" + readLine);






    }
}
