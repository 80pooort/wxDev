package com.fykj.wxDev.exception;

import com.fykj.wxDev.enumPackage.SelfExceptionEnum;

/**
 * @Author: wujian
 * @Date: 2018/11/4 22:50
 */
public class SelfException extends Exception {
    private String code;

    public SelfException(String code,String excMsg){
        super(excMsg);
        this.code = code;
    }

    public SelfException(SelfExceptionEnum seEnum){
        super(seEnum.getMsg());
        this.code = seEnum.getCode();
    }

    public String getCode() {
        return code;
    }

}
