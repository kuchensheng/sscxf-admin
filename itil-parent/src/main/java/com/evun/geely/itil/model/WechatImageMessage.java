package com.evun.geely.itil.model;

import com.evun.geely.itil.util.MessageUtil;

/**
 * @version 1.0
 * @Desription:
 * @Author:Hui
 * @CreateDate:2018/4/6 1:21
 */
public class WechatImageMessage extends AbstracWechattMessageModel {
    private String PicUrl;
    private String MediaId;

    public WechatImageMessage() {
        super.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
