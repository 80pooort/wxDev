package com.fykj.wxDev.interfaces;

import com.fykj.wxDev.vo.ResultVo;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wujian
 * @Date: 2018/11/4 23:09
 */
public interface WxSignServer {
    String processRequest(HttpServletRequest request) throws Exception;
    ResultVo getWXUserInfo(HttpServletRequest request) throws Exception;
    String wxAccessToken() throws Exception;
}
