package com.fykj.wxDev.dao;

import com.fykj.wxDev.pojo.ThreeMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ThreeMenuDao {
    ThreeMenu selectOne(Map<String,Object> params);
}