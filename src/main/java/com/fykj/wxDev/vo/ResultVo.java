package com.fykj.wxDev.vo;

import com.fykj.wxDev.enumPackage.ResultMsgEnum;
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
    private String errcode;
    private String errmsg;
    private T data;

    ResultVo(){}

    ResultVo(boolean success) {
        this.success = success;
    }

    public static ResultVo createSuccess(String msg, Object data) {
        ResultVo resultVo = new ResultVo(true);
        resultVo.setErrcode("0");
        resultVo.setErrmsg(msg);
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo createFalse(String code, String msg) {
        ResultVo resultVo = new ResultVo(false);
        resultVo.setErrcode(code);
        resultVo.setErrmsg(msg);
        return resultVo;
    }

    public static ResultVo createFalse(ResultMsgEnum rme){
        ResultVo resultVo = new ResultVo(false);
        resultVo.setErrcode(rme.getErrcode());
        resultVo.setErrmsg(rme.getErrmsg());
        return resultVo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
            "errcode='" + errcode + '\'' +
            ", errmsg='" + errmsg + '\'' +
            '}';
    }
}
