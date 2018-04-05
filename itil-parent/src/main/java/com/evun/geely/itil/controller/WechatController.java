package com.evun.geely.itil.controller;

import com.evun.geely.itil.service.WechatService;
import com.evun.geely.itil.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @Desription:
 * @Author:Hui
 * @CreateDate:2018/4/5 16:02
 */
@RestController
public class WechatController {

    @Value("${WX_TOKEN}")
    private String token;
    @Value("${WX_RET_SUCCESS}")
    private String wx_return;
    @Autowired
    private WechatService wechatService;

    @RequestMapping("/pong")
    public String pong(){
        return "pong";
    }

    @RequestMapping(value = "/connect",method = RequestMethod.GET)
    public String connect(
            @RequestParam(value = "signature",required = true) String signature,
            @RequestParam(value = "timestamp",required = true) String timestamp,
            @RequestParam(value = "nonce",required = true) String nonce,
            @RequestParam(value = "echostr",required = true) String echostr
    ){
        if(CheckUtil.checkSignature(signature,timestamp,nonce,token))
            return echostr;
        return null;

    }

    @RequestMapping(value = "/connect",method = RequestMethod.POST)
    public String onWxMessage(HttpServletRequest request){

        String weChatMessage = wechatService.reciveWeChatMessage(request);
        return null == weChatMessage ? wx_return : weChatMessage;
    }
}
