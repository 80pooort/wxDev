package com.fykj.wxDev.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 消费者属性
 * created by wujian on 2018/11/19 16:21
 */
@Data
@Component
@ConfigurationProperties(prefix = ConsumerProperties.PREFIX)
public class ConsumerProperties{
  public static final String PREFIX = "rocketmq.consumer";

}
