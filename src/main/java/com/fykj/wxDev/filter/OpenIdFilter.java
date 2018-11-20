package com.fykj.wxDev.filter;

import com.fykj.wxDev.util.CookieUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/**
 * order注解设置过滤器优先级别,数值小的优先级高
 * created by wujian on 2018/11/19 10:52
 */
@WebFilter(filterName = "OpenIdFilter",urlPatterns = "/*")
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class OpenIdFilter implements Filter {

  @Value("${fykj.unfilterUrl}")
  private String unfilterUrl;

  @Autowired
  private CookieUtil cookieUtil;

  private String[] unFilterUrlArr;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    if (StringUtils.isBlank(unfilterUrl)) {
      return;
    }
    unFilterUrlArr = unfilterUrl.split(",");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    HttpServletResponse servletResponse = (HttpServletResponse)response;

    String servletPath = servletRequest.getServletPath();
    //不走过滤器的路径
    if (!Arrays.isNullOrEmpty(unFilterUrlArr)) {
      for (String url:unFilterUrlArr) {
        if (servletPath.indexOf(url) != -1) {
          chain.doFilter(request,response);
          return;
        }
      }
    }

    String openId = "";
    openId = servletRequest.getParameter("open_id");
    if (StringUtils.isEmpty(openId)) {
      openId = cookieUtil.getCookie(servletRequest,CookieUtil.OPEN_ID_COOKIE);
    }
    if (StringUtils.isEmpty(openId)){
      //调接口获取用户openid
    }
    if (StringUtils.isEmpty(openId)) {
      //跳转错误页面
      return;
    }
    //保存openid到cookie
    cookieUtil.setCookie(servletResponse,CookieUtil.OPEN_ID_COOKIE,openId,3*24*60*60);
    chain.doFilter(request,response);
  }

  @Override
  public void destroy() {

  }
}
