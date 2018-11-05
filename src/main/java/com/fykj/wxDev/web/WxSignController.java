package com.fykj.wxDev.web;

import com.fykj.wxDev.interfaces.WxSignServer;
import com.fykj.wxDev.util.WxUtil;
import com.fykj.wxDev.vo.WXBaseParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RestController
public class WxSignController {

    private final Logger logger = LoggerFactory.getLogger(WxSignController.class);

    @Value("${wx.token}")
    private String wxToken;

    @Autowired
    private WxSignServer wxSignServer;

    @RequestMapping(name = "wxSign", method = RequestMethod.GET)
    public void wxDoor(HttpServletResponse response, HttpServletRequest request, WXBaseParams wxBaseParams) {
        try {
            System.out.println("开始校验签名");
            //排序
            String sortString = WxUtil.sort(wxToken, wxBaseParams.getTimestamp(), wxBaseParams.getNonce());
            //加密
            String mySignature = WxUtil.sha1(sortString);
            //校验签名
            if (mySignature != null && mySignature != "" && mySignature.equals(wxBaseParams.getSignature())) {
                System.out.println("签名校验通过。");
                response.getWriter().write(wxBaseParams.getEchostr());
                System.out.println("初始化菜单");

            } else {
                System.out.println("签名校验失败.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(name = "wxSign", method = RequestMethod.POST)
    public void wxDoor(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        String respXml = "";
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            respXml = wxSignServer.processRequest(request);
        } catch (Exception e) {
            logger.error("响应用户信息异常", e);
        }
        out.print(respXml);
        out.close();

    }
}
