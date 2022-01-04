package com.liangzc.example.redis.protogenic;

public class MainClientTest {

    public static void main(String[] args) {


        CustomerRedisClient redisClient = new CustomerRedisClient("106.55.227.209",6379);

        System.out.println(redisClient.set("firstKey", "firstValue"));

        System.out.println(redisClient.get("firstKey"));
    }
}
