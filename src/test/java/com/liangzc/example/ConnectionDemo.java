package com.liangzc.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionDemo {


    //get
    @Test
    public void httpConnectionUrlTest() throws IOException {
        StringBuffer result = new StringBuffer();
        String url = "http://localhost:8081/start?id="+1;
        URL url1 = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(false);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            result.append(line);
        }
        bufferedReader.close();
        System.out.println(result.toString());
    }

    //post
    @Test
    public void httpConnectionUrlPostTest() throws IOException {
        StringBuffer result = new StringBuffer();
        String url = "http://localhost:8081/postTest";
        URL url1 = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.connect();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("id", 666);
        OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
        for (String key : requestMap.keySet()) {
            outputStream.write(key +"=" +requestMap.get(key));
        }

        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        InputStream inputStream;
        System.out.println("响应code="+responseCode);
        if(responseCode == 200){
            inputStream = connection.getInputStream();
        }else {
            inputStream = connection.getErrorStream();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            result.append(line);
        }
        bufferedReader.close();
        System.out.println("响应报文："+result.toString());
    }

}
