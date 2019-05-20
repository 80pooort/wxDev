package com.fykj.wxDev.interfaces;

/**
 * 百度相关的api服务
 * created by wujian on 2019/4/24 9:56
 */
public interface ThirdApplyServer {

  /**
   * 获取token,缓存中拿,缓存没有请求接口
   */
  String getAccessTokenForOut() throws Exception;

  /**
   * 识别身份证,返回json格式字符串
   */
//  String getIdentieyCardInfo(String imgPath,String cardSide) throws Exception;

  /**
   * 请求百度Api接口获取accessToken
   */
  String getAccessTokenFromBaidu() throws Exception;

//  String getBankCardInfo(String imgPath) throws Exception;

//  String getIdentieyCardInfoFromAliyun(String imgPath,String cardSide) throws Exception;
}
