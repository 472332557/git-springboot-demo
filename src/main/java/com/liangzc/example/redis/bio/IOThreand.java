package com.liangzc.example.redis.bio;

import java.io.*;
import java.net.Socket;

public class IOThreand implements Runnable{

    private Socket socket = null;

    public IOThreand(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            //收到客户端发送的信息，inputStream是阻塞的
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = bufferedReader.readLine();
            System.out.println("socketServer receive a message:"+msg);

            //给客户端发送信息
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("send a message:"+msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
