package com.yxs.wx.handler;

import lombok.Data;
import lombok.Getter;

@Getter
public enum WeChatCallBackEnums {

    SUBSCRIBE("event.subscribe", "关注"),
    TEXT_MESSAGE("text", "消息");

    private String msgType;
    private String description;

    WeChatCallBackEnums(String msgType, String description){
        this.msgType = msgType;
        this.description = description;
    }


    public static WeChatCallBackEnums geBytMsgType(String msgType) {
        for (WeChatCallBackEnums value : values()) {
            if (value.getMsgType().equals(msgType)){
                return value;
            }
        }
        return null;
    }
}
