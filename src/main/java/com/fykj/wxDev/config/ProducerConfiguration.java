package com.fykj.wxDev.config;

import com.fykj.wxDev.vo.ProducerProperties;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rocketmq 配置类
 * created by wujian on 2018/11/19 12:52
 */
@Configuration
@EnableConfigurationProperties(ProducerProperties.class)
@ConditionalOnProperty(prefix = ProducerProperties.PREFIX,value = "namesrvAddr")
public class RocketmqAutoConfiguration {
  private static final Logger logger = LoggerFactory.getLogger(RocketmqAutoConfiguration.class);

  @Autowired
  private ProducerProperties properties;

  @Autowired
  private ApplicationEventPublisher publisher;

  private static boolean isFirstSub = true;

  @Bean
  @ConditionalOnProperty(prefix = ProducerProperties.PREFIX,value = "default",havingValue = "true")
  public DefaultMQProducer defaultMQProducer() throws MQClientException {
    logger.info();
    DefaultMQProducer defaultMQProducer = new DefaultMQProducer(properties.getGroupName());
    defaultMQProducer.setNamesrvAddr(properties.getNamesrvAddr());
    defaultMQProducer().setVipChannelEnabled(false);
    defaultMQProducer.setRetryTimesWhenSendAsyncFailed(10);
    defaultMQProducer.start();
    return defaultMQProducer;
  }

}
