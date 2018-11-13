package com.fykj.wxDev.dao;

import com.fykj.wxDev.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuDao {
    Menu selectOne(Map<String, Object> params);

    List<Menu> select(Map<String, Object> params);

}