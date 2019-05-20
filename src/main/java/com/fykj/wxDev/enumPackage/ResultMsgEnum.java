package com.fykj.wxDev.enumPackage;

/**
 * 返回结果集美剧
 * @Author: wujian
 * @Date: 2018/11/19 22:05
 */
public enum ResultMsgEnum {
    SUCCESS("0","success"),
    ERROR("20000","接口异常"),
    EMPTY("20001","微信接口无返回"),
    OPENIDEMPTY("20002","用户openId为空"),
    PARMAEMPTY("20003","必要参数为空"),
    CODEINVALID("20004","验证码失效"),
    ERRORCODE("20005","验证码错误")
    ;

    ResultMsgEnum(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    private String errcode;
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
