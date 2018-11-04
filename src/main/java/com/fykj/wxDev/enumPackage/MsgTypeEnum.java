package com.fykj.wxDev.enumPackage;

/**
 * @Author: wujian
 * @Date: 2018/11/1 21:35
 */
public enum MsgTypeEnum {
    TEXT("text"),
    IMAGE("image");

    MsgTypeEnum(String msgType) {
        this.msgType = msgType;
    }

    private String msgType;

    public static boolean isIegalType(String typeName){
        MsgTypeEnum[] values = MsgTypeEnum.values();
        for (MsgTypeEnum msgTypeEnum: values) {
            if (msgTypeEnum.msgType.equals(typeName)) {
                return true;
            }
        }
        return false;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
