package com.yxs.subject.application.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "test-consumer-group", topic = "test-topic")
@Slf4j
public class TestMQConsumer implements RocketMQListener {


    @Override
    public void onMessage(Object o) {
        log.info("接受到的MQ消息：{}", o);
    }
}
