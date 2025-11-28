package com.yxs.wx.controller;

import com.yxs.wx.handler.WeChatMesFactory;
import com.yxs.wx.handler.WeChatMesHandler;
import com.yxs.wx.utils.MessageUtil;
import com.yxs.wx.utils.SHA1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@RestController
//@RequestMapping("wx")
@Slf4j
public class CallBackController {

    @Resource
    private WeChatMesFactory weChatMesFactory;

    private static final String TOKEN = "yxsaihjy";

    @GetMapping("/test")
    public String test() {
        return "hello world";
    }

    @GetMapping("/callback")
    public String callBack(@RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("echostr") String echostr) {

        log.info("Received verification request: signature={}, timestamp={}, nonce={}, echostr={}",
                signature, timestamp, nonce, echostr);

        String token = SHA1.getSHA1(TOKEN, timestamp, nonce, "");
        if (signature.equals(token)) {
            return echostr;
        }

        return "unknown";
    }

    @PostMapping(value = "/callback", produces = "application/xml; charset=UTF-8")
    public String callBack(
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(value = "msg_signature", required = false) String msgSignature) {

        log.info("接收到微信消息：requestBody：{}", requestBody);

        Map<String, String> requstMessageMap = MessageUtil.parseXml(requestBody);
        String msgType = requstMessageMap.get("MsgType");
        String event = requstMessageMap.get("Event") == null ? "" : requstMessageMap.get("Event");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(msgType);
        if (StringUtils.isEmpty(event)){
            stringBuilder.append(".");
            stringBuilder.append(event);
        }

        String builderString = stringBuilder.toString();
        WeChatMesHandler weChatMesHandler = weChatMesFactory.getWeChatMesHandler(builderString);

        if (Objects.isNull(weChatMesHandler)){
            return "";
        }
        String sentMessage = weChatMesHandler.sendValidMes(requstMessageMap);


        log.info("返回微信消息：{}", sentMessage);

        return sentMessage;
    }

}
