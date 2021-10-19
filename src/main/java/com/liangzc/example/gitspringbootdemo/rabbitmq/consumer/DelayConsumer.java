package com.liangzc.example.gitspringbootdemo.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component

public class DelayConsumer {


    @RabbitHandler
    @RabbitListener(queues = "spring-boot-delay-queue")
    public void process(String message){
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("接收到一条消息--------->>>:"+message+"\n接收时间：" +sf.format(new Date()));
    }
}
