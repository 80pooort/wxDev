package com.fykj.wxDev.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 属性文件父类
 * created by wujian on 2018/11/19 15:26
 */
@Data
@Component
@ConfigurationProperties(prefix = RocketMQProperties.PREFIX)
public class RocketMQProperties {
  public static final String PREFIX = "rocketmq";

  //rocket服务地址
  private String namesrvAddr;

  //生产者组名
  private String groupName;

  @Autowired
  private ProducerProperties producerProperties;

  @Autowired
  private ConsumerProperties consumerProperties;

}
