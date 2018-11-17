package com.fykj.wxDev.vo;

import java.io.Serializable;

/**
 * 通用结果对象
 *
 * @Author: wujian
 * @Date: 2018/11/15 21:16
 */
public class ResultVo<T> implements Serializable {
    private static final long serialVersionUID = 8412493911097577364L;
    private boolean success;
    private String code;
    private String msg;
    private T data;

    ResultVo(){}

    ResultVo(boolean success) {
        this.success = success;
    }

    public static ResultVo createSuccess(String msg, Object data) {
        ResultVo resultVo = new ResultVo(true);
        resultVo.setMsg(msg);
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo createFalse(String code, String msg) {
        ResultVo resultVo = new ResultVo(false);
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
