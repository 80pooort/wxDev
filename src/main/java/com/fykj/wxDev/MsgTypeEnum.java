package com.fykj.wxDev;

/**
 * @Author: wujian
 * @Date: 2018/11/1 21:35
 */
public enum MsgTypeEnum {
    TEXT("TEXT","com.fykj.wxDev.vo.TextMessage"),
    IMAGE("IMAGE","com.fykj.wxDev.vo.ImageMessage");

    MsgTypeEnum(String name, String funName) {
        this.name = name;
        this.funName = funName;
    }

    private String name;
    private String funName;

    public static boolean isIegalType(String typeName){
        MsgTypeEnum[] values = MsgTypeEnum.values();
        for (MsgTypeEnum msgTypeEnum: values) {
            if (msgTypeEnum.name.equals(typeName)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }
}
