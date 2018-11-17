package com.fykj.wxDev.services;

import com.alibaba.fastjson.JSON;
import com.fykj.wxDev.dao.MenuDao;
import com.fykj.wxDev.dao.ThreeMenuDao;
import com.fykj.wxDev.interfaces.WxMenuServer;
import com.fykj.wxDev.pojo.Menu;
import com.fykj.wxDev.pojo.ThreeMenu;
import com.fykj.wxDev.vo.ResultVo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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

    public void testMenuFun() {
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("id",1);
        ThreeMenu threeMenus = threeMenuDao.selectOne(params);
        System.out.println(threeMenus);
    }

    @Override
    public ResultVo createMenu() throws Exception {
        List<Menu> menuForWx = menuDao.findMenuForWx();
        String s = JSON.toJSONString(menuForWx);
        System.out.println(s);
        return null;
    }

}
