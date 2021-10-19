package com.liangzc.example.gitspringbootdemo.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:liangzcmq.properties")
public class RabbitDelayConfig {

    @Value("${delay_queue_name}")
    private String queue;

    @Value("${delay_exchange_name}")
    private String exchange;

    @Bean("springTopicExchange")
    public TopicExchange getExchange(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new TopicExchange(exchange,true,false,args);
    }

    @Bean("springTopicQueue")
    public Queue getQueue(){
        return new Queue(queue);
    }

    /**
     * 绑定，#全匹配
     * @param exchange
     * @param queue
     * @return
     */
    @Bean
    public Binding bindExchangeAndQueue(@Qualifier("springTopicExchange") TopicExchange exchange, @Qualifier("springTopicQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("#");
    }

}
