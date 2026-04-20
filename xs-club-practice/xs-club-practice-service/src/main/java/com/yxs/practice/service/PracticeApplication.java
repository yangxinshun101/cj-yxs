package com.yxs.practice.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ComponentScan("com.yxs")
@MapperScan("com.yxs.**.dao")
@EnableFeignClients(basePackages = "com.yxs")
public class PracticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }
}
