package com.liangzc.example.rabbitmq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DelayConsumer {


    @RabbitListener(queues = "spring-boot-delay-queue")
    public void process(Message message){
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("接收到一条消息--------->>>:"+new String(message.getBody())+"\n接收时间：" +sf.format(new Date()));
    }
}
