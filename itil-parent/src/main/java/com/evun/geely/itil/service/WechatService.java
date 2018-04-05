package com.evun.geely.itil.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @Desription:
 * @Author:Hui
 * @CreateDate:2018/4/5 21:57
 */
public interface WechatService {

    public String reciveWeChatMessage(HttpServletRequest request);

    /**
     * 获取用户基本信息
     * @param openId
     * @return
     */
    public JSONObject getUserInfo(String openId);

    /**
     * 获取access_token
     * @param appId
     * @param appsecret
     * @return
     */
    public JSONObject getAccessToken(String appId,String appsecret);
}
