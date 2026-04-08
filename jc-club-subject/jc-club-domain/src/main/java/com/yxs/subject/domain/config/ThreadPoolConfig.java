package com.yxs.subject.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.defaultThreadFactory;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor labelThreadPool(){
        return new ThreadPoolExecutor(
                20,
                100,
                2,
                TimeUnit.MINUTES,
                new LinkedBlockingDeque(40),
                new LabelThreadPool("label"),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
