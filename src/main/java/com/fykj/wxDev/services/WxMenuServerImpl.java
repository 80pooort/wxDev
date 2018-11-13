package com.fykj.wxDev.services;

import com.fykj.wxDev.dao.ThreeMenuDao;
import com.fykj.wxDev.interfaces.WxMenuServer;
import com.fykj.wxDev.pojo.ThreeMenu;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void testMenuFun() {
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("id",1);
        ThreeMenu threeMenus = threeMenuDao.selectOne(params);
        System.out.println(threeMenus);
    }

}
