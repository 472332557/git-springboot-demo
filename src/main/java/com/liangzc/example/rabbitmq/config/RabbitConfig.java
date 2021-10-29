package com.liangzc.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:liangzcmq.properties")
public class RabbitConfig {

    //广播
    @Value("${com.liangzc.queue_name}")
    private String queue;

    @Value("${com.liangzc.exchange_name}")
    private String exchange;

    //直连
    @Value("${com.liangzc.direct_queue_name}")
    private String directQueue;

    @Value("${com.liangzc.direct_exchange_name}")
    private String directExchange;

    @Bean("springbootQueue")
    public Queue getQueue(){
        return new Queue(queue);
    }

    @Bean("springbootExchange")
    public FanoutExchange getExchange(){
        return new FanoutExchange(exchange);
    }

    @Bean("springbootDirectQueue")
    public Queue getDirectQueue(){
        return new Queue(directQueue);
    }

    @Bean("springbootDirectExchange")
    public DirectExchange getDirectExchange(){
        return new DirectExchange(directExchange);
    }

    //广播交换机和队列绑定
    @Bean
    public Binding bindQueueAndExchange(@Qualifier("springbootQueue") Queue queue, @Qualifier("springbootExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding binndQueueAndExchangeDirect(@Qualifier("springbootDirectQueue")Queue queue,@Qualifier("springbootDirectExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("direct");
    }

}
