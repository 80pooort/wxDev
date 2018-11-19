package com.fykj.wxDev.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 操作Cookie的工具类
 * created by wujian on 2018/11/19 9:52
 */
@Component
public class CookieUtil {

  private final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

  public static final String OPEN_ID_COOKIE = "openId";

  public static final String COOKIE_PATH = "/";

  @Value("${cookie.domain}")
  private String domain;

  /**
   * 添加cookie
   * @param response
   * @param name
   * @param value
   * @param expire
   * @return
   */
  public boolean setCookie(HttpServletResponse response,String name,String value,Integer expire){
    if (StringUtils.isBlank(name) || StringUtils.isBlank(value)) {
      return false;
    }
    try {
      Cookie cookie = new Cookie(name, value);
      cookie.setPath(COOKIE_PATH);
      cookie.setDomain(domain);
      if (expire != null) {
        cookie.setMaxAge(expire);
      }
      response.addCookie(cookie);
      return true;
    } catch (Exception e) {
      logger.error(String.format("add cookie : %s error",name) ,e);
      return  false;
    }
  }

  /**
   * 获取指定name的cookie 值
   * @param request
   * @param name
   * @return
   */
  public String getCookie(HttpServletRequest request,String name){
    if (StringUtils.isBlank(name)) {
      return "";
    }

    Cookie[] cookies = request.getCookies();
    if (Arrays.isNullOrEmpty(cookies)) {
      return "";
    }

    for (Cookie cookie:cookies) {
      if (StringUtils.equals(name,cookie.getName())) {
        return cookie.getValue();
      }
    }
    return "";
  }

}
