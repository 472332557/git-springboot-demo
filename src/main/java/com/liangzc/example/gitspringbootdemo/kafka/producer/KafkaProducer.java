package com.liangzc.example.gitspringbootdemo.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public String send(@RequestParam String msg){
        System.out.println("kafka准备发送消息中。。。。。。");
        kafkaTemplate.send("lzc-topic-test", msg);
        return "ok";
    }
}
