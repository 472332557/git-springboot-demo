package com.liangzc.example.gitspringbootdemo.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerListener {

    @KafkaListener(topics = "lzc-topic-test",groupId = "lzc-test-group")
    public void listener(String msg){
        System.out.println("----收到消息："+msg+"----");
    }
}
