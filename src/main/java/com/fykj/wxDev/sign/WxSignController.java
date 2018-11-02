package com.fykj.wxDev.sign;

import com.fykj.wxDev.factory.CreateMsgFactory;
import com.fykj.wxDev.util.WxUtil;
import com.fykj.wxDev.vo.CommentMessage;
import com.fykj.wxDev.vo.WXBaseParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class WxSignController {

    @Value("${wx.token}")
    private String wxToken;

    @RequestMapping("/wxSign")
    public void wxSign(HttpServletResponse response, HttpServletRequest request, WXBaseParams wxBaseParams) {
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
            } else {
                System.out.println("签名校验失败.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/test")
    public void testFactory(){
        try {
            CreateMsgFactory createMsgFunctory = new CreateMsgFactory();
            CommentMessage msg = createMsgFunctory.createMsg("IMAGE");
            msg.runFun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
