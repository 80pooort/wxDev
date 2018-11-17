package com.fykj.wxDev.dao;

import com.fykj.wxDev.pojo.ThreeMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ThreeMenuDao {
    ThreeMenu selectOne(Map<String,Object> params);
    List<ThreeMenu> findAllThreeMenu(@Param("id") int id);
}