package com.fykj.wxDev.interfaces;

import com.fykj.wxDev.vo.MemberInfo;
import com.fykj.wxDev.vo.ResultVo;

/**
 * created by wujian on 2018/11/23 16:14
 */
public interface MemberServer {

  ResultVo registerMember(MemberInfo memberInfo) throws Exception;

  ResultVo login(String phone,String validateCode) throws Exception;

  ResultVo logout(String phone) throws Exception;

}
