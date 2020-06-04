package com.fykj.wxDev.config;

import com.fykj.wxDev.vo.RocketMQProperties;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rocketmq 配置类
 * created by wujian on 2018/11/19 12:52
 */
@Configuration
@EnableConfigurationProperties(RocketMQProperties.class)
@ConditionalOnProperty(prefix = RocketMQProperties.PREFIX,value = "namesrvAddr")
public class ProducerConfiguration {
  private static final Logger logger = LoggerFactory.getLogger(ProducerConfiguration.class);

  @Autowired
  private RocketMQProperties rocketMQProperties;

  @Bean
  @ConditionalOnProperty(prefix =  RocketMQProperties.PREFIX,value = "namesrvAddr")
  public DefaultMQProducer defaultMQProducer() throws MQClientException {
    logger.info(rocketMQProperties.toString());
    logger.info("defaultMQProducer 正在创建-----------------------");
    DefaultMQProducer defaultMQProducer = new DefaultMQProducer(rocketMQProperties.getGroupName());
    defaultMQProducer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
    defaultMQProducer.setRetryTimesWhenSendAsyncFailed(10);
    defaultMQProducer.setVipChannelEnabled(false);
    defaultMQProducer.start();
    logger.info("rocket mq server 启动成功-------------------------");
    return defaultMQProducer;
  }

}
