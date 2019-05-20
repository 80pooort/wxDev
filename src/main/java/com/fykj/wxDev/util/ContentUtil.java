package com.fykj.wxDev.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 对内容解析的工具类
 * created by wujian on 2019/5/5 10:54
 */
@Slf4j
public class ContentUtil implements Serializable {

  /**
   * 使用dom4j
   * 将xml格式字符串转为map
   */
  public static Map readXMLString(String XMLString){
    Document doc;
    Map<String,String> resultMap = new HashMap<>();
    try {
      //解析文档
      doc = DocumentHelper.parseText(XMLString);
      //获取根节点
      Element rootElement = doc.getRootElement();
      //获取根节点下子节点
      Iterator childElement = rootElement.elementIterator();
      while (childElement.hasNext()){
        Element childEle = (Element) childElement.next();
        Iterator secChildElement = childEle.elementIterator();
        while (secChildElement.hasNext()){
          Element secChildEle = (Element) secChildElement.next();
          resultMap.put(secChildEle.getName(),secChildEle.getTextTrim());
        }
      }
      return resultMap;
    } catch (DocumentException e) {
      log.error("解析xml字符串异常:",e);
      return null;
    }
  }

  public static void main(String[] args) {
    String testStr ="<html>" + "<head>" + "<title>dom4j解析一个例子</title>"
        + "<script>" + "<username>wujian</username>"
        + "<password>123456</password>" + "</script>" + "</head>"
        + "<body>" + "<result>0</result>" + "<form>"
        + "<banlce>1000</banlce>" + "<subID>36242519880716</subID>"
        + "</form>" + "</body>" + "</html>";
    String msgStr="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        + "<Message>"
        + "<Head>"
        + "<ResTime>2019-05-08 10:24:01</ResTime>"
        + "<ResCode>999999</ResCode>"
        + "<ResMsg>ReqId不能为空</ResMsg>"
        + "</Head>"
        + "<body>" + "<result>0</result>"
        + "<banlce>1000</banlce>" + "<subID>36242519880716</subID>"
        + "</body>"
        + "</Message>";
//    readXMLString(msgStr);
//    Date date = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
//        .parseDateTime("2019-05-05 14:12:30").toDate();
//    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    ThreadLocal<Long> longThreadLocal = new ThreadLocal<>();
    longThreadLocal.set(20L);
    Long aLong1 = longThreadLocal.get();
    Long aLong = new ThreadLocal<Long>().get();
    System.out.println(aLong + ":::" + aLong1);
  }
}
