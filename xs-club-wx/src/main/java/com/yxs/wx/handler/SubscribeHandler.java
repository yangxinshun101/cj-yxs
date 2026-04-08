package com.yxs.wx.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SubscribeHandler implements WeChatMesHandler{
    @Override
    public WeChatCallBackEnums getMesType() {
        return WeChatCallBackEnums.SUBSCRIBE;
    }

    @Override
    public String sendValidMes(Map<String, String> messageMap) {

        log.info("触发用户关注事件！");

        String content = "<xml>\n" +
                " <ToUserName><![CDATA["+messageMap.get("FromUserName")+"]]></ToUserName>\n" +
                " <FromUserName><![CDATA["+messageMap.get("ToUserName")+"]]></FromUserName>\n" +
                " <CreateTime>1460537339</CreateTime>\n" +
                " <MsgType><![CDATA[text]]></MsgType>\n" +
                " <Content><![CDATA[你好呀，欢迎你关注新顺的学习公众号]]></Content>\n" ;
        return content;
    }
}
