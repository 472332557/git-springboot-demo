package com.liangzc.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionDemo {


    //HttpURLConnection get
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

    //HttpURLConnection post
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

    //RestTemplate get
    @Test
    public void RestTemplateGet(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/start?id="+1;
        String result = restTemplate.getForObject(url, String.class);
        System.out.println("返回结果："+result);
    }

    //RestTemplate post
    @Test
    public void RestTemplatePost(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/postTest";

        //设置请求头信息
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(type);
        //设置请求参数
        MultiValueMap<String,Object> multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add("id","666");
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(multiValueMap, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
        String body = responseEntity.getBody();
        System.out.println("返回信息："+body);
    }

}
