package com.yxs.practice.service.handler;

import com.yxs.practice.service.enums.PracticeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Configuration
public class PracticeHandlerFactory implements InitializingBean {

    @Resource
    private List<PracticeHandler> practiceHandler;

    private HashMap<PracticeEnum, PracticeHandler> handlerMap = new HashMap<>();

    public PracticeHandler getHandler(int practiceEnumCode) {
        PracticeEnum practiceEnum = PracticeEnum.valueOfCode(practiceEnumCode);
        return handlerMap.get(practiceEnum);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        practiceHandler.forEach(handler->{
            handlerMap.put(handler.getPracticeEnum(), handler);
        });
    }
}
