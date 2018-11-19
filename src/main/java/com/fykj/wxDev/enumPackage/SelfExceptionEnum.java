package com.fykj.wxDev.enumPackage;

/**
 * @Author: wujian
 * @Date: 2018/11/4 22:56
 */
public enum  SelfExceptionEnum {
    INVALIDPARAM("1001","无效的参数"),
    FACTOR("1002","递增因子必须大于0"),
    DESFACTOR("1003","递减因子必须大于0");

    private String code;
    private String msg;

    SelfExceptionEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

}
