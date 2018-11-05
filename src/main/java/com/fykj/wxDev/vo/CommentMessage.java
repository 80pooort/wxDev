package com.fykj.wxDev.vo;

/**
 * 消息父类类
 *
 * @Author: wujian
 * @Date: 2018/11/1 21:27
 */
public class CommentMessage {
    String ToUserName;
    String FromUserName;
    Long CreateTime;
    String MsgType;

    public void replyMsg(String msg) {
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
