package com.yxs.subject.application.mq;

import com.alibaba.fastjson.JSON;
import com.yxs.subject.domain.entity.SubjectLikedBO;
import com.yxs.subject.domain.entity.SubjectLikedMessage;
import com.yxs.subject.domain.service.SubjectLikedDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(consumerGroup = "subject-like-consumer", topic = "subject-liked-topic")
@Slf4j
public class SubjectLikeMQConsumer implements RocketMQListener {

    @Resource
    private SubjectLikedDomainService subjectLikedDomainService;

    @Override
    public void onMessage(Object o) {

        SubjectLikedBO subjectLikedBO = JSON.parseObject(o.toString(), SubjectLikedBO.class);

        subjectLikedDomainService.syncLikedByMsg(subjectLikedBO);
        log.info("接受到的MQ消息：{}", o);
    }
}
