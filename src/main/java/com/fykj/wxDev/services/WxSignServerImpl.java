package com.fykj.wxDev.services;

import com.alibaba.fastjson.JSONObject;
import com.fykj.wxDev.enumPackage.MsgTypeEnum;
import com.fykj.wxDev.enumPackage.ResultMsgEnum;
import com.fykj.wxDev.factory.CreateMsgFactory;
import com.fykj.wxDev.interfaces.WxSignServer;
import com.fykj.wxDev.util.CookieUtil;
import com.fykj.wxDev.util.RedisUtil;
import com.fykj.wxDev.util.WxUtil;
import com.fykj.wxDev.vo.AccessToken;
import com.fykj.wxDev.vo.CommentMessage;
import com.fykj.wxDev.vo.ResultVo;
import com.fykj.wxDev.vo.Setting;
import com.fykj.wxDev.vo.WXProperties;
import com.fykj.wxDev.vo.WXUserInfo;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wujian
 * @Date: 2018/11/4 23:11
 */
@Service
public class WxSignServerImpl implements WxSignServer {
    private static final Logger logger = LoggerFactory.getLogger(WxSignServerImpl.class);

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private WXProperties wxProperties;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String processRequest(HttpServletRequest request) throws Exception {
        String respXml = null;
        String respMsg = null;
        // 调用parseXml方法解析请求消息
        Map<String, String> requestMap = WxUtil.parseXml(request);
        // 发送方帐号
        String fromUserName = requestMap.get("FromUserName");
        // 开发者微信号
        String toUserName = requestMap.get("ToUserName");
        // 消息类型
        String msgType = requestMap.get("MsgType");
        CreateMsgFactory createMsgFactory = new CreateMsgFactory();
        CommentMessage msgObj = createMsgFactory.createMsg(MsgTypeEnum.TEXT.name());
        msgObj.setCreateTime(new Date().getTime());
        msgObj.setFromUserName(toUserName);
        msgObj.setToUserName(fromUserName);
        if (!MsgTypeEnum.isIegalType(msgType)) {
            respMsg = "未知的消息类型";
        }
        msgObj.replyMsg(respMsg);
        respXml = WxUtil.messageToXml(msgObj);
        return respXml;
    }

    /**
     * 获取微信用户基本信息
     * @return
     * @throws Exception
     */
    @Override
    public ResultVo getWXUserInfo(HttpServletRequest request) throws Exception {
        //从cookie中拿用户openId
        String openId = cookieUtil.getCookie(request, CookieUtil.OPEN_ID_COOKIE);
        if (StringUtils.isBlank(openId)) {
          return ResultVo.createFalse(ResultMsgEnum.OPENIDEMPTY);
        }
        //调微信接口获取用户基本信息
        String accessToken = wxAccessToken();
        String url = String.format(wxProperties.getSnsUserInfoUrl(),accessToken,openId);
        String wxResult = WxUtil.getHttpsResponse(url, "GET", null);
        if (StringUtils.isBlank(wxResult)) {
            return ResultVo.createFalse(ResultMsgEnum.EMPTY);
        }
        //封装结果数据并返回
        JSONObject jsonObject = JSONObject.parseObject(wxResult);
        if (StringUtils.isNotBlank((String) jsonObject.get("errcode")) && StringUtils.isNotBlank((String)jsonObject.get("errmsg"))) {
            return JSONObject.parseObject(wxResult,ResultVo.class);
        }
        return ResultVo.createSuccess("success",JSONObject.parseObject(wxResult, WXUserInfo.class));
    }

    @Override
    public String wxAccessToken() throws Exception {
        String tempStr = (String)redisUtil.get(Setting.ACCESS_TOKEN);
        AccessToken accessToken = JSONObject.parseObject(tempStr, AccessToken.class);
        return accessToken.getAccessToken();
    }
}
