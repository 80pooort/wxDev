package com.fykj.wxDev.interfaces;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wujian
 * @Date: 2018/11/4 23:09
 */
public interface WxSignServer {
    public String processRequest(HttpServletRequest request) throws Exception;
}
