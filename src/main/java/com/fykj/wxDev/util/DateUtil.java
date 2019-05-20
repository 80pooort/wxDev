package com.fykj.wxDev.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间工具类,线程安全
 * created by wujian on 2019/5/14 17:55
 */
public class DateUtil {

  private static final Logger logger  = LoggerFactory.getLogger(DateUtil.class);

  public static DateTimeFormatter dateTimeFormatter = null;

  /**
   * 获取指定模板的当前时间字符串
   * @param pattern
   * @return
   */
  public static String getCurrentTimeStrByPattern(String pattern){
    if (StringUtils.isBlank(pattern)) {
      return null;
    }
    try {
      dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
      String currentTime = dateTimeFormatter.format(LocalDateTime.now());
      return currentTime;
    }catch (Exception e){
      logger.error("获取当前时间字符串异常:"+e.getMessage(),e);
      return null;
    }
  }

  /**
   * 将指定模板的时间字符串转换为Date
   * @param pattern
   * @param dateStr
   * @return
   */
  public static Date getDateByPatternAndTimeStr(String pattern,String dateStr) {
    if (StringUtils.isBlank(pattern) || StringUtils.isBlank(dateStr)) {
      return null;
    }
    try {
      dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
      LocalDateTime paramDt = LocalDateTime.parse(dateStr, dateTimeFormatter);
      // LocalDateTime转换成Date
      ZoneId zoneId = ZoneId.systemDefault();
      ZonedDateTime zonedDateTime = paramDt.atZone(zoneId);
      Date date = Date.from(zonedDateTime.toInstant());
      return date;
    }catch (Exception e){
      logger.error("根据自定模板转换字符串为date异常:"+e.getMessage(),e);
      return null;
    }
  }

  public static void main(String[] args) {
    String currentTimeStr = getCurrentTimeStrByPattern("yyyy年MM月dd日 HH:mm:ss");
    Date date = getDateByPatternAndTimeStr("yyyy-MM-dd HH:mm:ss",
        "2019-05-14 12:05:25");
    System.out.println(currentTimeStr + ":::" + date);
  }
}
