package com.fykj.wxDev.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * order注解设置过滤器优先级别,数值小的优先级高
 * created by wujian on 2018/11/19 10:52
 */
@WebFilter(filterName = "OpenIdFilter",urlPatterns = "/*")
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class OpenIdFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

  }

  @Override
  public void destroy() {

  }
}
