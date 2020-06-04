package com.fykj.wxDev.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Rocketmq配置文件属性值映射实体
 * created by wujian on 2018/11/19 12:04
 */
@Data
@Component
@ConfigurationProperties(ProducerProperties.PREFIX)
public class ProducerProperties{

  public static final String PREFIX = "rocketmq.producer";

  private String tag;
}
