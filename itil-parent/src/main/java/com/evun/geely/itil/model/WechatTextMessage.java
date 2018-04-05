package com.evun.geely.itil.model;

import com.evun.geely.itil.util.MessageUtil;

/**
 * @version 1.0
 * @Desription:
 * @Author:Hui
 * @CreateDate:2018/4/6 0:06
 */
public class WechatTextMessage extends AbstracWechattMessageModel {
    private String Content;

    public WechatTextMessage() {
        super.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
