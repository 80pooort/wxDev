package com.fykj.wxDev.vo;


import com.fykj.wxDev.enumPackage.MsgTypeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 文本消息类
 * @Author: wujian
 * @Date: 2018/11/1 21:31
 */
public class TextMessage extends CommentMessage {

    private String Content;

    public TextMessage(){
        this.MsgType= MsgTypeEnum.TEXT.name();
    }

    @Override
    public void replyMsg(String msg){
        this.Content = "请联系客服";
        if (StringUtils.isNotBlank(msg)) {
            this.Content = msg;
        }
    }
}
