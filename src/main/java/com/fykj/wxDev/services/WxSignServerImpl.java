package com.fykj.wxDev.services;

import com.fykj.wxDev.enumPackage.MsgTypeEnum;
import com.fykj.wxDev.factory.CreateMsgFactory;
import com.fykj.wxDev.interfaces.WxSignServer;
import com.fykj.wxDev.util.WxUtil;
import com.fykj.wxDev.vo.CommentMessage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @Author: wujian
 * @Date: 2018/11/4 23:11
 */
@Service
public class WxSignServerImpl implements WxSignServer {
    @Override
    public String processRequest(HttpServletRequest request) throws Exception {
        String respXml = null;
        String respMsg = null;
        // 调用parseXml方法解析请求消息
        Map<String, String> requestMap = WxUtil.parseXml(request);
        // 发送方帐号
        String fromUserName = requestMap.get("FromUserName");
        // 开发者微信号
        String toUserName = requestMap.get("ToUserName");
        // 消息类型
        String msgType = requestMap.get("MsgType");
        if (MsgTypeEnum.isIegalType(msgType)) {
            respMsg = "未知的消息类型";
        }
        CreateMsgFactory createMsgFactory = new CreateMsgFactory();
        CommentMessage msgObj = createMsgFactory.createMsg(msgType);
        msgObj.setCreateTime(new Date().getTime());
        msgObj.setFromUserName(toUserName);
        msgObj.setToUserName(fromUserName);
        msgObj.replyMsg();
        return respXml;
    }
}
