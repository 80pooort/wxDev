package com.fykj.wxDev.config;

import com.alibaba.druid.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * created by wujian on 2018/11/19 16:45
 */
@Configuration
public class TestConsumer extends ConsumerConfiguration implements ApplicationListener<ContextRefreshedEvent> {
  private static final Logger logger = LoggerFactory.getLogger(TestConsumer.class);

  private final String TOPIC = "t_topictest";

  private final String TAGS = "tag1";

  //业务实体及逻辑代码位置
  @Override
  public ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs) {
    MessageExt msg = msgs.get(0);
    if (!StringUtils.equals(TOPIC,msg.getTopic()) || !StringUtils.equals(TAGS,msg.getTags())) {
      return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
    try {
      String proBody = new String(msg.getBody(), "UTF-8");
      System.out.println(proBody);
    } catch (UnsupportedEncodingException e) {
      logger.error("消费body字符转换异常",e);
    }
    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    try {
      super.listener(TOPIC,TAGS);
    } catch (MQClientException e) {
      logger.error("监听器启动失败",e);
    }
  }
}
