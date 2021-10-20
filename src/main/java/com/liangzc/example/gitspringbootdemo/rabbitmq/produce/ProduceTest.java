package com.liangzc.example.gitspringbootdemo.rabbitmq.produce;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class ProduceTest {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(){

        System.out.println("准备发送消息。。。。。。。。。。。。。。");

        amqpTemplate.convertAndSend("spring-boot-exchange", "", "一条从spring boot 发来的消息");
    }


    public void sendDirect(){

        System.out.println("准备发送消息。。。。。。。。。。。。。。");

        amqpTemplate.convertAndSend("spring-boot-direct-exchange", "direct", "一条从spring boot 发来的直连消息！！！");
    }

    public void sendDelayMsg(){
        System.out.println("准备发送消息。。。。。。。。。。。。。。");
        // 延时投递，比如延时1分钟
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, +20);
        Date delayTime = calendar.getTime();
        MessageProperties properties = new MessageProperties();
        properties.setHeader("x-delay",delayTime.getTime() - now.getTime());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String msg = "发送时间：" + sf.format(now) + "，预计投递时间：" + sf.format(delayTime);
        Message message = new Message(msg.getBytes(), properties);
        amqpTemplate.convertAndSend("spring-boot-delay-exchange", "delay", message);
    }
}
