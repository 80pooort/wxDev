package com.fykj.wxDev.vo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Rocketmq配置文件属性值映射实体
 * created by wujian on 2018/11/19 12:04
 */
@ConfigurationProperties(RocketmqProperties.PREFIX)
public class RocketmqProperties {
  public static final String PREFIX = "fykj.rocketmq";
  //rocket服务地址
  private String namesrvAddr;
  //生产者和消费之组名
  private String producerGroupName;
  private String consumerGroupName;
  //事务生产者组名
  private String transactionProducerGroupName;
  //事务生产者实例名称
  private String producerTranInstanceName;
  //生产实例名称
  private String producerInstanceName;
  //消费实例名称
  private String consumerInstanceName;
  //一次最大消费数
  private int consumerBatchMaxSize;
 //是否广播消费
  private boolean consumerBroadcasting;
  //启动时是否消费历史广播
  private boolean enableHisConsumer;
  //是否启动顺序消费
  private boolean enableOrderConsumer;
  //消费topic:tag
  private List subscribe = new ArrayList<>();

  public String getNamesrvAddr() {
    return namesrvAddr;
  }

  public void setNamesrvAddr(String namesrvAddr) {
    this.namesrvAddr = namesrvAddr;
  }

  public String getProducerGroupName() {
    return producerGroupName;
  }

  public void setProducerGroupName(String producerGroupName) {
    this.producerGroupName = producerGroupName;
  }

  public String getConsumerGroupName() {
    return consumerGroupName;
  }

  public void setConsumerGroupName(String consumerGroupName) {
    this.consumerGroupName = consumerGroupName;
  }

  public String getTransactionProducerGroupName() {
    return transactionProducerGroupName;
  }

  public void setTransactionProducerGroupName(String transactionProducerGroupName) {
    this.transactionProducerGroupName = transactionProducerGroupName;
  }

  public String getProducerTranInstanceName() {
    return producerTranInstanceName;
  }

  public void setProducerTranInstanceName(String producerTranInstanceName) {
    this.producerTranInstanceName = producerTranInstanceName;
  }

  public String getProducerInstanceName() {
    return producerInstanceName;
  }

  public void setProducerInstanceName(String producerInstanceName) {
    this.producerInstanceName = producerInstanceName;
  }

  public String getConsumerInstanceName() {
    return consumerInstanceName;
  }

  public void setConsumerInstanceName(String consumerInstanceName) {
    this.consumerInstanceName = consumerInstanceName;
  }

  public int getConsumerBatchMaxSize() {
    return consumerBatchMaxSize;
  }

  public void setConsumerBatchMaxSize(int consumerBatchMaxSize) {
    this.consumerBatchMaxSize = consumerBatchMaxSize;
  }

  public boolean isConsumerBroadcasting() {
    return consumerBroadcasting;
  }

  public void setConsumerBroadcasting(boolean consumerBroadcasting) {
    this.consumerBroadcasting = consumerBroadcasting;
  }

  public boolean isEnableHisConsumer() {
    return enableHisConsumer;
  }

  public void setEnableHisConsumer(boolean enableHisConsumer) {
    this.enableHisConsumer = enableHisConsumer;
  }

  public boolean isEnableOrderConsumer() {
    return enableOrderConsumer;
  }

  public void setEnableOrderConsumer(boolean enableOrderConsumer) {
    this.enableOrderConsumer = enableOrderConsumer;
  }

  public List getSubscribe() {
    return subscribe;
  }

  public void setSubscribe(List subscribe) {
    this.subscribe = subscribe;
  }
}
