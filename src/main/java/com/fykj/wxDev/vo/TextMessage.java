package com.fykj.wxDev.vo;


import com.fykj.wxDev.enumPackage.MsgTypeEnum;

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
    public void replyMsg(){
    }
}
