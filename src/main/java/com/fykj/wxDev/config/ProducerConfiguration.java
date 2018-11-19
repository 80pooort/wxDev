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
public class ProducerConfiguration {
  private static final Logger logger = LoggerFactory.getLogger(ProducerConfiguration.class);

  @Autowired
  private ProducerProperties producerProperties;

  @Bean
  @ConditionalOnProperty(prefix = ProducerProperties.PREFIX,value = "default",havingValue = "true")
  public DefaultMQProducer defaultMQProducer() throws MQClientException {
    logger.info(producerProperties.toString());
    logger.info("defaultMQProducer 正在创建-----------------------");
    DefaultMQProducer defaultMQProducer = new DefaultMQProducer(producerProperties.getGroupName());
    defaultMQProducer.setNamesrvAddr(producerProperties.getNamesrvAddr());
    defaultMQProducer().setVipChannelEnabled(false);
    defaultMQProducer.setRetryTimesWhenSendAsyncFailed(10);
    defaultMQProducer.start();
    logger.info("rocket mq server 启动成功-------------------------");
    return defaultMQProducer;
  }

}
