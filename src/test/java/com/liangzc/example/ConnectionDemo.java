package com.liangzc.example;

import com.alibaba.fastjson.JSONObject;
import com.liangzc.example.start.demo.model.User;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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

    public static final String getUrl = "http://localhost:8081/start?id="+1;

    public static final String postUrl = "http://localhost:8081/postTest";

    public static final String postUrlJson = "http://localhost:8081/postJsonTest";


    //HttpURLConnection get
    @Test
    public void httpConnectionUrlTest() throws IOException {
        StringBuffer result = new StringBuffer();
        URL url1 = new URL(getUrl);
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
        URL url1 = new URL(postUrl);
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
        String result = restTemplate.getForObject(getUrl, String.class);
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

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(postUrl, entity, String.class);
        String body = responseEntity.getBody();
        System.out.println("返回信息："+body);
    }

    //httpClient get
    @Test
    public void httpClientGet(){
        String url = "http://localhost:8081/start?id="+1;
        //获得http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //创建get请求
        HttpGet httpGet = new HttpGet(getUrl);
        CloseableHttpResponse response = null;
        try {
            //发送get请求
            response = httpClient.execute(httpGet);
            System.out.println("响应状态为："+response.getStatusLine());
            org.apache.http.HttpEntity entity = response.getEntity();
            if(entity != null){
                System.out.println("响应长度为："+entity.getContentLength());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line);
                }
                System.out.println("返回信息为："+builder.toString());
                System.out.println("响应内容为："+ JSONObject.toJSONString(entity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            try {
                if(httpClient != null){
                    httpClient.close();
                }
                if (response != null){
                    response.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //httpClient post
    @Test
    public void httpClientPost(){
        //获得http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //创建get请求
        HttpPost httpPost = new HttpPost(postUrl);
        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
        //构建请求参数
        StringEntity stringEntity = new StringEntity("id=" +666, "UTF-8");
        //设置请求参数，放入请求体
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try {
            //发送post请求
            response = httpClient.execute(httpPost);
            System.out.println("响应状态为："+response.getStatusLine());
            org.apache.http.HttpEntity responseEntity = response.getEntity();
            if(responseEntity != null){
                System.out.println("响应长度为："+responseEntity.getContentLength());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line);
                }
                System.out.println("返回信息为："+builder.toString());
                System.out.println("响应内容为："+ JSONObject.toJSONString(responseEntity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            try {
                if(httpClient != null){
                    httpClient.close();
                }
                if (response != null){
                    response.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //httpClient post json格式请求
    @Test
    public void httpClientPostOfJson(){
        //获得http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //创建get请求
        HttpPost httpPost = new HttpPost(postUrlJson);
        httpPost.setHeader("Content-Type","application/json;charset=utf8");
        //构建请求参数
        User user = new User();
        user.setName("lili");
        user.setAge("20");
        user.setGender("女");
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(user), "UTF-8");
        //设置请求参数，放入请求体
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try {
            //发送post请求
            response = httpClient.execute(httpPost);
            System.out.println("响应状态为："+response.getStatusLine());
            org.apache.http.HttpEntity responseEntity = response.getEntity();
            if(responseEntity != null){
                System.out.println("响应长度为："+responseEntity.getContentLength());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line);
                }
                System.out.println("返回信息为："+builder.toString());
                System.out.println("响应内容为："+ JSONObject.toJSONString(responseEntity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            try {
                if(httpClient != null){
                    httpClient.close();
                }
                if (response != null){
                    response.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



}
