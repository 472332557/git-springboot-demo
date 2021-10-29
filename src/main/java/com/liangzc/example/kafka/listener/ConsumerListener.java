package com.liangzc.example.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;

//@Component 暂时注释
public class ConsumerListener {

    @KafkaListener(topics = "lzc-topic-test",groupId = "lzc-test-group")
    public void listener(String msg){
        System.out.println("----收到消息："+msg+"----");
    }
}
