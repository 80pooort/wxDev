package com.fykj.wxDev.interfaces;

import com.fykj.wxDev.vo.ResultVo;
import java.util.Map;

/**
 * @Author: wujian
 * @Date: 2018/11/4 23:09
 */
public interface WxSignServer {
    String processRequest(Map<String,String> requestMap) throws Exception;
    ResultVo getWXUserInfoByOpenId(String openId) throws Exception;
    String wxAccessToken() throws Exception;
    void testMQ() throws Exception;
}
