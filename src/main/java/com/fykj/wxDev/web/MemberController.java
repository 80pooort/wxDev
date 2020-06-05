package com.fykj.wxDev.web;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by wujian on 2019/1/2 9:20
 */
@RestController
@RequestMapping("/mem")
@Slf4j
public class MemberController extends BaseController {

  @RequestMapping("/hello.json")
  public void helloWord(HttpServletResponse response){
    PrintWriter writer = null;
    try {
      writer = response.getWriter();
    } catch (Exception e) {
      log.error("测试用例异常:{}{}",e.getMessage(),e);
    }
    writer.print("hello word");

  }
}
