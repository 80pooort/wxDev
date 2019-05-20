package com.fykj.wxDev.services.provider;

import com.fykj.wxDev.enumPackage.ResultMsgEnum;
import com.fykj.wxDev.interfaces.provider.SMSServer;
import com.fykj.wxDev.util.RedisUtil;
import com.fykj.wxDev.vo.ResultVo;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * created by wujian on 2018/11/22 17:17
 */
public class SMSServerImpl implements SMSServer {
  private static final Logger logger = LoggerFactory.getLogger(SMSServerImpl.class);

  private final long EXPIRE_TIME = 10 * 60;

  @Autowired
  private RedisUtil redisUtil;

  @Override
  public ResultVo createMsgCode(String phoneNum) {
    if (StringUtils.isBlank(phoneNum)) {
      return ResultVo.createFalse(ResultMsgEnum.PARMAEMPTY);
    }
    try {
      //先查是否有值,有直接将该code给用户
      String code = (String)redisUtil.get(phoneNum);
      if (StringUtils.isNotBlank(code)) {
        return ResultVo.createSuccess("success",code);
      }
      //产生一个六位随机数
      StringBuffer stringBuffer = new StringBuffer();
      String source = "0123456789";
      Random random = new Random();
      for(int i = 0;i<6;i++){
        int singleCode = random.nextInt(9);
        stringBuffer.append(source.charAt(singleCode));
      }
      //存入redis,设置过期时间
      String msgCode = stringBuffer.toString();
      redisUtil.set(phoneNum,msgCode,EXPIRE_TIME);
      return ResultVo.createSuccess("success",msgCode);
    } catch (Exception e) {
      logger.error("生成验证码异常",e);
      return ResultVo.createFalse(ResultMsgEnum.ERROR);
    }
  }

  @Override
  public ResultVo verifyCode(String phoneNum, String code) {
    if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(code)) {
      return ResultVo.createFalse(ResultMsgEnum.PARMAEMPTY);
    }
    String originCode = (String)redisUtil.get(phoneNum);
    if (StringUtils.isBlank(phoneNum)) {
      return ResultVo.createFalse(ResultMsgEnum.CODEINVALID);
    }
    if (StringUtils.equals(code,originCode)) {
      redisUtil.del(phoneNum);
      return ResultVo.createSuccess("success",null);
    }
    return ResultVo.createFalse(ResultMsgEnum.ERRORCODE);
  }

  @Override
  public ResultVo sendMsg(String phoneNum, String msgContent) {
    return null;
  }
}
