package com.liangzc.example;

import com.alibaba.fastjson.JSONObject;
import com.liangzc.example.start.demo.model.User;
import okhttp3.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionDemo {

    public static final String getUrl = "http://localhost:8081/start?id="+666;

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
        System.out.println("??????code="+responseCode);
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
        System.out.println("???????????????"+result.toString());
    }

    //RestTemplate get
    @Test
    public void RestTemplateGet(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/start?id="+1;
        String result = restTemplate.getForObject(getUrl, String.class);
        System.out.println("???????????????"+result);
    }

    //RestTemplate post  key-value
    @Test
    public void RestTemplatePost(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/postTest";

        //?????????????????????
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(type);
        //??????????????????
        MultiValueMap<String,Object> multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add("id","666");
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(multiValueMap, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(postUrl, entity, String.class);
        String body = responseEntity.getBody();
        System.out.println("???????????????"+body);
    }

    //RestTemplate postJson
    @Test
    public void RestTemplatePostJson(){
        RestTemplate restTemplate = new RestTemplate();
        //?????????????????????
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(type);
        //??????????????????
        User user = new User();
        user.setName("lili");
        user.setAge("20");
        user.setGender("???");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(postUrlJson, user, String.class);
        String body = responseEntity.getBody();
        System.out.println("???????????????"+body);
    }

    //httpClient get
    @Test
    public void httpClientGet(){
        String url = "http://localhost:8081/start?id="+1;
        //??????http?????????
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //??????get??????
        HttpGet httpGet = new HttpGet(getUrl);
        CloseableHttpResponse response = null;
        try {
            //??????get??????
            response = httpClient.execute(httpGet);
            System.out.println("??????????????????"+response.getStatusLine());
            org.apache.http.HttpEntity entity = response.getEntity();
            if(entity != null){
                System.out.println("??????????????????"+entity.getContentLength());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line);
                }
                System.out.println("??????????????????"+builder.toString());
                System.out.println("??????????????????"+ JSONObject.toJSONString(entity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //????????????
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
        //??????http?????????
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //??????get??????
        HttpPost httpPost = new HttpPost(postUrl);
        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");

        CloseableHttpResponse response = null;
        try {
            //??????????????????
            List<NameValuePair> paramList = Lists.newArrayList();
            paramList.add(new BasicNameValuePair("id", "666"));
            //????????????????????????????????????
            httpPost.setEntity(new UrlEncodedFormEntity(paramList,"UTF-8"));
            //??????post??????
            response = httpClient.execute(httpPost);
            System.out.println("??????????????????"+response.getStatusLine());
            org.apache.http.HttpEntity responseEntity = response.getEntity();
            if(responseEntity != null){
                System.out.println("??????????????????"+responseEntity.getContentLength());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line);
                }
                System.out.println("??????????????????"+builder.toString());
                System.out.println("??????????????????"+ JSONObject.toJSONString(responseEntity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //????????????
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

    //httpClient post json????????????
    @Test
    public void httpClientPostOfJson(){
        //??????http?????????
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //??????get??????
        HttpPost httpPost = new HttpPost(postUrlJson);
        httpPost.setHeader("Content-Type","application/json;charset=utf8");
        //??????????????????
        User user = new User();
        user.setName("lili");
        user.setAge("20");
        user.setGender("???");
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(user), "UTF-8");
        //????????????????????????????????????
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try {
            //??????post??????
            response = httpClient.execute(httpPost);
            System.out.println("??????????????????"+response.getStatusLine());
            org.apache.http.HttpEntity responseEntity = response.getEntity();
            if(responseEntity != null){
                System.out.println("??????????????????"+responseEntity.getContentLength());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line);
                }
                System.out.println("??????????????????"+builder.toString());
                System.out.println("??????????????????"+ JSONObject.toJSONString(responseEntity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //????????????
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

    /**
     * HttpClient???????????????????????????????????????EntityUtils???????????????????????????????????????
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    @Test
    public void httpClientPostOfJsonNew(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(postUrlJson);
        httpPost.setHeader("Content-Type","application/json;charset=utf8");
        //??????????????????
        User user = new User();
        user.setName("lili");
        user.setAge("20");
        user.setGender("???");
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(user), "UTF-8");
        //????????????????????????????????????
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            System.out.println("????????????????????????"+statusCode);
            org.apache.http.HttpEntity responseEntity = response.getEntity();
            String toString = EntityUtils.toString(responseEntity);
            if(statusCode == HttpStatus.SC_OK){
                System.out.println("???????????????"+toString);
            }else {
                System.out.println("???????????????"+toString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //????????????
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


    // okHttp  get
    @Test
    public void okHttpGet(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(getUrl).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){

                ResponseBody responseBody = response.body();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseBody.byteStream()));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line);
                }
                System.out.println("??????????????????"+builder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }

    }

    // okHttp  post
    @Test
    public void okHttpPost(){
        OkHttpClient okHttpClient = new OkHttpClient();
        //??????????????????
        User user = new User();
        user.setName("lili");
        user.setAge("20");
        user.setGender("???");
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(JSONObject.toJSONString(user), mediaType);
        Request request = new Request.Builder().url(postUrlJson).post(body).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){

                ResponseBody responseBody = response.body();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseBody.byteStream()));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line);
                }
                System.out.println("??????????????????"+builder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }

    }

}
