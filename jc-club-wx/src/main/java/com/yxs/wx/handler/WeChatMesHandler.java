package com.yxs.wx.handler;


import java.util.Map;

public interface WeChatMesHandler {


    WeChatCallBackEnums getMesType();

    String sendValidMes (Map<String, String>  messageMap);
}
