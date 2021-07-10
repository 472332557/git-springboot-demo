package com.liangzc.example.gitspringbootdemo;
import com.liangzc.example.gitspringbootdemo.rabbitmq.produce.ProduceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class RabbitMQTest {

    @Resource
    ProduceTest produceTest;

    @Test
    public void contextLoads() {
        produceTest.send();
    }
}
