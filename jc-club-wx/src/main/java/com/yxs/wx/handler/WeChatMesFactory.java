package com.yxs.wx.handler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WeChatMesFactory implements InitializingBean {

    @Resource
    private List<WeChatMesHandler> weChatMesHandlers;

    Map<WeChatCallBackEnums,WeChatMesHandler> weChatMesHandlerMap = new HashMap<>();

    public WeChatMesHandler getWeChatMesHandler(String mesType){
        WeChatCallBackEnums weChatCallBackEnums = WeChatCallBackEnums.geBytMsgType(mesType);
        return weChatMesHandlerMap.get(weChatCallBackEnums);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (WeChatMesHandler weChatMesHandler : weChatMesHandlers) {
            weChatMesHandlerMap.put(weChatMesHandler.getMesType(),weChatMesHandler);
            }
        }
    }
}
