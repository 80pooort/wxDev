package com.fykj.wxDev.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信用户信息封装类
 * created by wujian on 2018/11/22 10:28
 */
@Setter
@Getter
public class WXUserInfo {

  private int subcribe;
  private String openid;
  private String nickname;
  private int sex;
  private String language;
  private String city;
  private String province;
  private String country;
  private String headimgurl;
  private long subscribe_time;
  private String unionid;
  private String remark;
  private long groupid;
  private long[] tagid_list;
  private String subscribe_scene;
  private long qr_scene;
  private String qr_scene_str;

}
