package com.liangzc.example.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
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

    /**
     * 延时队列交换机
     * 这里的交换机类型是：CustomExchange
     * @return
     */
    @Bean("springCustomExchange")
    public CustomExchange getExchange(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(exchange, "x-delayed-message", true, false, args);
    }

    @Bean("springTopicQueue")
    public Queue getQueue(){
        return new Queue(queue);
    }

    /**
     * @param exchange
     * @param queue
     * @return
     */
    @Bean
    public Binding bindExchangeAndQueue(@Qualifier("springCustomExchange") CustomExchange exchange, @Qualifier("springTopicQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("delay").noargs();
    }

}
