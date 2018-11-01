package com.fykj.wxDev.vo;

/**
 * 文本消息类
 * @Author: wujian
 * @Date: 2018/11/1 21:31
 */
public class TextMessage extends CommentMessage {
    @Override
    public void runFun(){
        System.out.println("这是文本类消息");
    }
}
