package com.fykj.wxDev.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信相关属性封装类
 * created by wujian on 2018/11/22 9:54
 */
@Setter
@Getter
@ConfigurationProperties(WXProperties.PREFIX)
public class WXProperties {
  public static final String PREFIX = "wx";

  private String token;
  private String appId;
  private String appSecret;
  private String accessTokenUrl;
  private String menuUrl;
  private String connectOauthUrl;
  private String snsOauthUrl;
  private String autoTokenSwitch;
  private String snsUserInfoUrl;


}
