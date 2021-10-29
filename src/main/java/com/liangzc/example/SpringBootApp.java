package com.liangzc.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//文件根目录下定义该类，使用注解@SpringBootApplication，说明spring启动时，会去扫描根目录以上的所有文件下的@Bean、@component等注解加载为BeanDefinition对象
@SpringBootApplication
// Servlet filter listener
@ServletComponentScan(basePackages = "com.liangzc.example")
@MapperScan("com.liangzc.example.start.demo.dao")
public class SpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }
}
