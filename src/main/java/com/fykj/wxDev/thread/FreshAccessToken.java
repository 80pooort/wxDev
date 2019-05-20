package com.fykj.wxDev.thread;

import com.fykj.wxDev.util.RedisUtil;
import com.fykj.wxDev.util.WxUtil;
import com.fykj.wxDev.vo.Setting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FreshAccessToken implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(FreshAccessToken.class);


    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${wx.autoTokenSwitch}")
    private boolean autoTokenSwitch;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args){
        new Thread(() -> {
            while (true) {
                try {
                    if (autoTokenSwitch) {
                        //开关开启才去获取不然一直休眠
                        getAndSaveAccessToken();
                    }
                    if (StringUtils.isNotBlank((String) redisUtil.get(Setting.ACCESS_TOKEN))) {
                        //获取到access_token 休眠7000秒,大约2个小时左右
                        Thread.sleep(7000 * 1000);
                    } else {
                        //获取的access_token为空 休眠3秒
                        Thread.sleep(1000 * 3);
                    }
                } catch (Exception e) {
                    logger.error("获取token异常:",e);
                    try {
                        //发生异常休眠1秒
                        Thread.sleep(1000 * 10);
                    } catch (Exception e1) {
                        logger.error("获取token线程休眠异常:",e);
                    }
                }
            }
        }).start();
    }

    public void getAndSaveAccessToken() {
        String Url = String.format(accessTokenUrl, appId, appSecret);
        String result = WxUtil.getHttpsResponse(Url, "GET", null);
        logger.info("刷新获取到的access_token={}",result);
        redisUtil.set(Setting.ACCESS_TOKEN, result);
    }
}
