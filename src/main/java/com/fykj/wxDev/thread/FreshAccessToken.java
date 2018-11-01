package com.fykj.wxDev.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fykj.wxDev.util.WxUtil;
import com.fykj.wxDev.vo.AccessToken;
import com.fykj.wxDev.vo.AccessTokenInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FreshAccessToken implements ApplicationRunner {

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.accessTokenUrl}")
    private String accessTokenUrl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        AccessTokenInfo.accessToken = getAccessToken();
                        if (AccessTokenInfo.accessToken != null) {
                            //获取到access_token 休眠7000秒,大约2个小时左右
                            Thread.sleep(7000 * 1000);
                        } else {
                            //获取的access_token为空 休眠3秒
                            Thread.sleep(1000 * 3);
                        }
                    } catch (Exception e) {
                        System.out.println("发生异常：" + e.getMessage());
                        e.printStackTrace();
                        try {
                            //发生异常休眠1秒
                            Thread.sleep(1000 * 10);
                        } catch (Exception e1) {

                        }
                    }
                }
            }
        }).start();
    }

    public AccessToken getAccessToken() {
        String Url = String.format(accessTokenUrl, appId, appSecret);
        String result = WxUtil.getHttpsResponse(Url, "");
        System.out.println("刷新获取到的access_token=" + result);
        JSONObject json = JSON.parseObject(result);
        AccessToken token = new AccessToken();
        token.setAccessToken(json.getString("access_token"));
        token.setExpiresin(json.getInteger("expires_in"));
        return token;
    }
}
