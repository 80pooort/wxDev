package com.fykj.wxDev.web;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fykj.wxDev.interfaces.WxMenuServer;
import com.fykj.wxDev.interfaces.WxSignServer;
import com.fykj.wxDev.util.WxUtil;
import com.fykj.wxDev.vo.ResultVo;
import com.fykj.wxDev.vo.WXBaseParams;
import com.fykj.wxDev.vo.WXProperties;
import com.fykj.wxDev.vo.WXUserInfo;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
public class WxSignController {

    private final Logger logger = LoggerFactory.getLogger(WxSignController.class);

//    @Value("${wx.token}")
//    private String wxToken;

    @Autowired
    private WXProperties wxProperties;

    @Autowired
    private WxSignServer wxSignServer;

    @Autowired
    private WxMenuServer wxMenuServer;

    @RequestMapping(name = "/wxPort", method = RequestMethod.GET)
    public void wxSign(HttpServletResponse response, HttpServletRequest request, WXBaseParams wxBaseParams) {
        try {
            logger.info("开始校验签名--------------");
            //排序
            String sortString = WxUtil.sort(wxProperties.getToken(), wxBaseParams.getTimestamp(), wxBaseParams.getNonce());
            //加密
            String mySignature = WxUtil.sha1(sortString);
            //校验签名
            if (!StringUtils.isEmpty(mySignature) && StringUtils.equals(mySignature,wxBaseParams.getSignature())) {
                logger.info("签名校验通过------------------");
                response.getWriter().write(wxBaseParams.getEchostr());
                ResultVo menuResult = wxMenuServer.createMenu();
                if (StringUtils.equals("0",menuResult.getErrcode()) && StringUtils.equals("success",menuResult.getErrmsg())) {
                    logger.info("wx菜单创建成功---------------");
                }else{
                    logger.info("wx菜单创建失败---reason:{}",menuResult.toString());
                }
            } else {
               logger.info("签名校验失败------------");
            }
        } catch (Exception e) {
            logger.error("签名校验异常:",e);
        }
    }

    @RequestMapping(name = "/wxPort", method = RequestMethod.POST)
    public void wxMsg(HttpServletRequest request, HttpServletResponse response) {
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

    @RequestMapping(value = "/getOpenId",method = RequestMethod.GET)
    public String getOpenId(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "currentUrl") String currentUrl,@RequestParam(value = "code") String code){
        try {
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(currentUrl)) {
                return "404.ftl";
            }
            String result = String.format(wxProperties.getSnsOauthUrl(), wxProperties.getAppId(), wxProperties.getAppSecret(),code);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (!StringUtils.isEmpty((String) jsonObject.get("errcode")) && StringUtils.isEmpty((String)jsonObject.get("errmsg"))) {
                logger.info("获取用户openId失败:{}",result);
                return "404.ftl";
            }
            WXUserInfo wxUserInfo = JSONObject.parseObject(result, WXUserInfo.class);
            HashMap<String, String> params = new HashMap<>();
            params.put("open_id",wxUserInfo.getOpenid());
            String url ="redirect:"  + WxUtil.unionUrl(currentUrl,params);
            return url;
        } catch (Exception e) {
            logger.error("权限获取用户openid失败:",e);
            return "404.ftl";
        }
    }


}
