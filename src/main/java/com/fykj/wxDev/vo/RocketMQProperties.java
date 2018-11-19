package com.fykj.wxDev.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 属性文件父类
 * created by wujian on 2018/11/19 15:26
 */
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = RocketMQProperties.PREFIX)
public class RocketMQProperties {
  public static final String PREFIX = "rocketmq";

  //rocket服务地址
  private String namesrvAddr;

  //生产者组名
  private String groupName;


}
