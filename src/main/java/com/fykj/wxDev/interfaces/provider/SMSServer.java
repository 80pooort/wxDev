package com.fykj.wxDev.interfaces.provider;

import com.fykj.wxDev.vo.ResultVo;

/**
 * 发送短信服务,对外暴露即dubbo
 * created by wujian on 2018/11/22 17:16
 */
public interface SMSServer {

  /**
   * 产生一条6位数短信验证码
   * @param phoneNum
   * @return
   */
  ResultVo  createMsgCode(String phoneNum);

  /**
   * 验证码是否有效
   * @param phoneNum
   * @param code
   * @return
   */
  ResultVo verifyCode(String phoneNum,String code);

  /**
   * 发送短信
   * @param phoneNum
   * @param msgContent
   * @return
   */
  ResultVo sendMsg(String phoneNum,String msgContent);
}
