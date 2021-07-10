package com.liangzc.example.gitspringbootdemo;

import com.liangzc.example.gitspringbootdemo.kafka.producer.KafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTest {

    @Resource
    KafkaProducer kafkaProducer;

    @Test
    public void send(){
        kafkaProducer.send("一条发往kafka的消息.........");
    }
}
