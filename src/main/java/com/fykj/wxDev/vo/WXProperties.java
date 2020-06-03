package com.fykj.wxDev.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信相关属性封装类
 * created by wujian on 2018/11/22 9:54
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WXProperties {


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
