package com.liangzc.example.gitspringbootdemo.rabbitmq.produce;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProduceTest {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(){

        System.out.println("准备发送消息。。。。。。。。。。。。。。");

        amqpTemplate.convertAndSend("spring-boot-exchange", "", "一条从spring boot 发来的消息");
    }
}
