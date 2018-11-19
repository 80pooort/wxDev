package com.fykj.wxDev.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fykj.wxDev.dao.MenuDao;
import com.fykj.wxDev.dao.ThreeMenuDao;
import com.fykj.wxDev.interfaces.WxMenuServer;
import com.fykj.wxDev.pojo.Menu;
import com.fykj.wxDev.pojo.ThreeMenu;
import com.fykj.wxDev.util.RedisUtil;
import com.fykj.wxDev.util.WxUtil;
import com.fykj.wxDev.vo.AccessToken;
import com.fykj.wxDev.vo.ResultVo;
import com.fykj.wxDev.vo.Setting;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author: wujian
 * @Date: 2018/11/12 20:03
 */
@Service
public class WxMenuServerImpl implements WxMenuServer {

    @Autowired
    private ThreeMenuDao threeMenuDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${wx.menuUrl}")
    private String menuUrl;

    public void testMenuFun() {
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("id",1);
        ThreeMenu threeMenus = threeMenuDao.selectOne(params);
        System.out.println(threeMenus);
    }

    @Override
    public ResultVo createMenu() throws Exception {
        Menu menuForWx = menuDao.findMenuForWx();
        Object menuObj = JSON.toJSON(menuForWx);
        WxUtil.delJsonEle(menuObj,"id");
        String params = JSON.toJSONString(menuObj);
        System.out.println(String.format("menu json:%s",params));
        String atStr = (String)redisUtil.get(Setting.ACCESS_TOKEN);
        AccessToken accessToken = JSONObject.parseObject(atStr, AccessToken.class);
        String result = WxUtil.getHttpsResponse(String.format(menuUrl, accessToken.getAccessToken()), "POST", params);
        if (StringUtils.isBlank(result)) {
            return ResultVo.createFalse("20001","微信接口无返回");
        }
        return JSONObject.parseObject(result,ResultVo.class);
    }

}
