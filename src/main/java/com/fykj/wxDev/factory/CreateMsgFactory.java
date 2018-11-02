package com.fykj.wxDev.factory;

import com.fykj.wxDev.MsgTypeEnum;
import com.fykj.wxDev.vo.CommentMessage;

/**
 * @Author: wujian
 * @Date: 2018/11/1 21:41
 */
public class CreateMsgFactory {

    //根据传入类型生产对应实例
    public  CommentMessage createMsg(String typeName) throws Exception{
        Class<?> msgClass = Class.forName(MsgTypeEnum.valueOf(typeName).getFunName());
        CommentMessage msg = (CommentMessage) msgClass.newInstance();
        return  msg;
    }
}
