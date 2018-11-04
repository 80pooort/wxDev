package com.fykj.wxDev.exception;

/**
 * @Author: wujian
 * @Date: 2018/11/4 22:50
 */
public class SelfException extends Exception {
    private String msg;

    public SelfException(String excMsg){
        super(excMsg);
        this.msg = excMsg;
    }

    public String getMsg() {
        return msg;
    }
}
