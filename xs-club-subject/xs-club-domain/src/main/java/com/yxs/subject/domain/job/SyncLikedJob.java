package com.yxs.subject.domain.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.yxs.subject.domain.service.SubjectLikedDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.bind.annotation.XmlAnyAttribute;

@Component
@Slf4j
public class SyncLikedJob {

    //将需要执行的服务注册进来呢
    @Resource
    private SubjectLikedDomainService subjectLikedDomainService;

    @XxlJob("syncLikedJobHandler")
    public void syncLikedJobHandler(){
        XxlJobHelper.log("syncLikedJobHandler.start");
        try {
            subjectLikedDomainService.syncLiked();
        } catch (Exception e) {
            XxlJobHelper.log("syncLikedJobHandler.error" + e.getMessage());
        }
    }
}
