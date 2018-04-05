package com.evun.geely.itil.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.evun.geely.itil.model.WechatImageMessage;
import com.evun.geely.itil.model.WechatTextMessage;
import com.evun.geely.itil.service.WechatService;
import com.evun.geely.itil.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @version 1.0
 * @Desription:
 * @Author:Hui
 * @CreateDate:2018/4/5 21:59
 */

@Service
public class WechatServiceImpl implements WechatService {
    private static final Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${WX_GET_ACCESS_TOKEN_URL}")
    private String accessTokenUrl;
    @Value("${WX_GET_USERINFO_URL}")
    private String userInfoUrl;

    @Value("${WX_APPID}")
    private String appId;
    @Value("${WX_APPSECRET}")
    private String appsecret;

    @Override
    public String reciveWeChatMessage(HttpServletRequest request) {
        String respMessage = null;
        try {
            Map<String, String> map = MessageUtil.xmlToMap(request.getInputStream());
            String fromUserName = map.get("FromUserName");//用户openId
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            switch (msgType){
                case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
                    logger.info("接收到文本消息：{}",map.get("Content"));
                    WechatTextMessage textMessage = new WechatTextMessage();
                    textMessage.setToUserName(fromUserName);
                    textMessage.setFromUserName(toUserName);
                    textMessage.setMsgType(msgType);
                    textMessage.setContent("当前客服不在线");
                    textMessage.setCreateTime(new Date().getTime()+"");
                    textMessage.setMsgId(map.get("MsgId"));
                    respMessage = MessageUtil.messgeToXml(textMessage);
                    break;
                case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
                    logger.info("接收到图片消息，图片地址：{}",map.get("PicUrl"));
                    WechatImageMessage imageMessage = new WechatImageMessage();
                    imageMessage.setToUserName(fromUserName);
                    imageMessage.setFromUserName(toUserName);
                    imageMessage.setCreateTime(new Date().getTime()+"");
                    imageMessage.setMsgType(msgType);
                    imageMessage.setPicUrl(map.get("PicUrl"));
                    imageMessage.setMediaId(map.get("MediaId"));
                    imageMessage.setMsgId(map.get("MsgId"));
                    respMessage = MessageUtil.messgeToXml(imageMessage);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("responseInfo is {}",respMessage);
        return respMessage;
    }

    @Override
    public JSONObject getUserInfo(String openId) {
        JSONObject accessToken = getAccessToken(appId, appsecret);
        if(null != accessToken){
            userInfoUrl = String.format(userInfoUrl,accessToken.getString("access_token"),openId);
            JSONObject body = restTemplate.getForEntity(userInfoUrl,JSONObject.class).getBody();
            return body;
        }
        return null;
    }

    @Override
    public JSONObject getAccessToken(String appId, String appsecret) {
        accessTokenUrl = String.format(accessTokenUrl,appId,appsecret);
        JSONObject body = restTemplate.getForEntity(accessTokenUrl, JSONObject.class).getBody();
        return body;
    }
}
