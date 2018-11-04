package com.fykj.wxDev.enumPackage;

/**
 * @Author: wujian
 * @Date: 2018/11/4 22:56
 */
public enum  SelfExceptionEnum {
    INVALIDPARAM("无效的参数");

    private String msg;

    SelfExceptionEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
