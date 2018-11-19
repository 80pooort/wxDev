package com.fykj.wxDev.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消费者属性
 * created by wujian on 2018/11/19 16:21
 */
@ConfigurationProperties(prefix = ConsumerProperties.PREFIX)
public class ConsumerProperties extends RocketMQProperties{
  public static final String PREFIX = "rocketmq.consumer";

}
