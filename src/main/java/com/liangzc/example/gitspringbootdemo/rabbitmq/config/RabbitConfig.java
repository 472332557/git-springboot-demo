package com.liangzc.example.gitspringbootdemo.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:liangzcmq.properties")
public class RabbitConfig {

    @Value("${com.liangzc.queue_name}")
    private String queue;

    @Value("${com.liangzc.exchange_name}")
    private String exchange;

    @Bean("springbootQueue")
    public Queue getQueue(){
        return new Queue(queue);
    }

    @Bean("springbootExchange")
    public FanoutExchange getExchange(){
        return new FanoutExchange(exchange);
    }

    @Bean
    public Binding bindQueueAndExchange(@Qualifier("springbootQueue") Queue queue, @Qualifier("springbootExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }




}
