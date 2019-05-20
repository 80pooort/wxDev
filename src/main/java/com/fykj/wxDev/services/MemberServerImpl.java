package com.fykj.wxDev.services;

import com.fykj.wxDev.interfaces.MemberServer;
import com.fykj.wxDev.util.BeanValidator;
import com.fykj.wxDev.vo.MemberInfo;
import com.fykj.wxDev.vo.ResultVo;
import org.springframework.stereotype.Service;

/**
 * created by wujian on 2018/11/23 16:14
 */
@Service
public class MemberServerImpl implements MemberServer {

  /**
   * 注册
   * @param memberInfo
   * @return
   * @throws Exception
   */
  @Override
  public ResultVo registerMember(MemberInfo memberInfo) throws Exception{
    //验证必要参数不能为空
    BeanValidator.validate(memberInfo);
    //参数验证成功插入数据,返回结果
    return null;
  }

  /**
   * 登录
   * @param phone
   * @param validateCode
   * @return
   * @throws Exception
   */
  @Override
  public ResultVo login(String phone, String validateCode) throws Exception {
    return null;
  }

  /**
   * 退出
   * @param phone
   * @return
   * @throws Exception
   */
  @Override
  public ResultVo logout(String phone) throws Exception {
    return null;
  }
}
