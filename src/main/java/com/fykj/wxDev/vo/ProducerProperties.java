package com.fykj.wxDev.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Rocketmq配置文件属性值映射实体
 * created by wujian on 2018/11/19 12:04
 */
@ConfigurationProperties(ProducerProperties.PREFIX)
public class ProducerProperties  extends RocketMQProperties{
  public static final String PREFIX = "rocketmq.producer";
}
