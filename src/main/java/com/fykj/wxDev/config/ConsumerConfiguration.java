package com.fykj.wxDev.config;

import com.fykj.wxDev.vo.ConsumerProperties;
import com.fykj.wxDev.vo.RocketMQProperties;
import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * rocketmq 消费配置类
 * created by wujian on 2018/11/19 16:24
 */
@Configuration
@EnableConfigurationProperties(RocketMQProperties.class)
@ConditionalOnProperty(prefix = RocketMQProperties.PREFIX,value = "namesrvAddr")
public abstract class ConsumerConfiguration {
  private static final Logger logger = LoggerFactory.getLogger(ConsumerConfiguration.class);

  @Autowired
  private RocketMQProperties rocketMQProperties;

  //初始化监听器,开启消费类
  public void listener(String topic,String tag) throws MQClientException {
    logger.info(String.format("开启%s:%s消费者",topic,tag));
    logger.info(rocketMQProperties.toString());
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
        rocketMQProperties.getGroupName());
    consumer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
    consumer.setVipChannelEnabled(false);
    consumer.subscribe(topic,tag);
    //内部类的方式,提供抽象方法给不同消费者实现业务逻辑
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
          ConsumeConcurrentlyContext context) {
        return ConsumerConfiguration.this.dealBody(msgs);
      }
    });
    consumer.start();
    logger.info("消费类启动成功--------------------");
  }

  // 处理body的业务
  public abstract ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs);
}
