package com.liangzc.example.gitspringbootdemo.redis.protogenic;

public class MainClientTest {

    public static void main(String[] args) {


        CustomerRedisClient redisClient = new CustomerRedisClient("119.23.189.136",6379);

        System.out.println(redisClient.set("firstKey", "firstValue"));

        System.out.println(redisClient.get("firstKey"));
    }
}
