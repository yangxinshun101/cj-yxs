package com.yxs.wx.handler;

import com.yxs.wx.redis.RedisUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ValidMesHandler implements WeChatMesHandler {

    @Resource
    private RedisUtils redisUtils;

    private String KeyWord = "验证码";
    private String LOGIN_PREFIX = "loginCode";


    @Override
    public WeChatCallBackEnums getMesType() {
        return WeChatCallBackEnums.TEXT_MESSAGE;
    }

    @Override
    public String sendValidMes(Map<String, String> messageMap) {
        String content = messageMap.get("Content");

        if (!content.equals(KeyWord)) {
            return "";
        }

        Random random = new Random();
        String code = String.valueOf(random.nextInt(1000000));

        String openId = messageMap.get("FromUserName");
        String key = redisUtils.buildKey(LOGIN_PREFIX, openId);
        redisUtils.setNx(key, openId, 5L, TimeUnit.MINUTES);
        String message = "<xml>\n" +
                " <ToUserName><![CDATA[" + openId + "]]></ToUserName>\n" +
                " <FromUserName><![CDATA[" + messageMap.get("toUserName") + "]]></FromUserName>\n" +
                " <CreateTime>" + LocalDateTime.now() + "</CreateTime>\n" +
                " <MsgType><![CDATA[text]]></MsgType>\n" +
                " <Content><![CDATA[你好，你的验证码是" + code + "，该验证码5分钟内有效！]]></Content>\n" +
                "</xml>";
        return message;
    }
}
