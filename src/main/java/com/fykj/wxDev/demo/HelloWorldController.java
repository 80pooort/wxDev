package com.fykj.wxDev.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class HelloWorldController {

    @RequestMapping("index")
    public void helloDemo(HttpServletResponse response){
        try {
            PrintWriter writer = response.getWriter();
            writer.print("hello world");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
