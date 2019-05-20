package com.fykj.wxDev.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fykj.wxDev.dao.MenuDao;
import com.fykj.wxDev.dao.ThreeMenuDao;
import com.fykj.wxDev.enumPackage.ResultMsgEnum;
import com.fykj.wxDev.interfaces.WxMenuServer;
import com.fykj.wxDev.pojo.Menu;
import com.fykj.wxDev.util.RedisUtil;
import com.fykj.wxDev.util.WxUtil;
import com.fykj.wxDev.vo.AccessToken;
import com.fykj.wxDev.vo.ResultVo;
import com.fykj.wxDev.vo.Setting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: wujian
 * @Date: 2018/11/12 20:03
 */
@Service
public class WxMenuServerImpl implements WxMenuServer {

    private static final Logger logger = LoggerFactory.getLogger(WxMenuServerImpl.class);

    @Autowired
    private ThreeMenuDao threeMenuDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${wx.menuUrl}")
    private String menuUrl;


    @Override
    public ResultVo createMenu() throws Exception {
        logger.info("wx 菜单创建开始----------------");
        Menu menuForWx = menuDao.findMenuForWx();
        Object menuObj = JSON.toJSON(menuForWx);
        WxUtil.delJsonEle(menuObj,"id");
        String params = JSON.toJSONString(menuObj);
        logger.info("menu params json:{}",params);
        String atStr = (String)redisUtil.get(Setting.ACCESS_TOKEN);
        AccessToken accessToken = JSONObject.parseObject(atStr, AccessToken.class);
        String result = WxUtil.getHttpsResponse(String.format(menuUrl, accessToken.getAccessToken()), "POST", params);
        if (StringUtils.isBlank(result)) {
            return ResultVo.createFalse(ResultMsgEnum.EMPTY);
        }
        return JSONObject.parseObject(result,ResultVo.class);
    }

}
