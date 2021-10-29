package com.liangzc.example.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "spring-boot-queue")
public class FanoutConsumer {


    @RabbitHandler
    public void process(String message){

        System.out.println("接收到一条消息--------->>>:"+message);
    }

}
