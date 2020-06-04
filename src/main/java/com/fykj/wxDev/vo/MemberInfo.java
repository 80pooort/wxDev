package com.fykj.wxDev.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * created by wujian on 2018/11/23 16:16
 */
@Data
public class MemberInfo {

  @NotNull(message = "电话号码不能为空!")
  @Size(min = 1,message = "电话号码不能为空串!",max = 11)
  @Pattern(regexp = "/^1(3|4|5|7|8)\\d{9}$/",message = "电话号码不合规则!")
  private String phone;

  @NotNull(message = "密码不能为空")
  @Size(min = 6,message = "密码长度最少6位!")
  private String password;

  @NotNull(message = "账号名称不能为空")
  private String name;

  @NotNull(message = "性别不能为空")
  private String sex;

  @NotNull(message = "出生年月不能为空")
  private String birthday;


}
