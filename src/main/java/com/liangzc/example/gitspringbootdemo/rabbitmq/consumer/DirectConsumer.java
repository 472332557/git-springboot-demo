package com.liangzc.example.gitspringbootdemo.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "spring-boot-direct-queue")
public class DirectConsumer {


    @RabbitHandler
    public void process(String message){

        System.out.println("接收到一条消息--------->>>:"+message);
    }

}
