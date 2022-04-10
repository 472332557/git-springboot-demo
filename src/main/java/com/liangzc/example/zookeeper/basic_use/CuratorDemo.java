package com.liangzc.example.zookeeper.basic_use;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorDemo {

    public static void main(String[] args) throws Exception {


        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectionTimeoutMs(10000)
                .connectString("121.37.249.94:2181")
                //指定重试
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .sessionTimeoutMs(10000)
                .build();
        curatorFramework.start();//启动


        byte[] path = curatorFramework.getData().forPath("/person/name");
        System.out.println(new String(path));

    }
}
