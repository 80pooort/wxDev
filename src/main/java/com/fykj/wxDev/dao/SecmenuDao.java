package com.fykj.wxDev.dao;

import com.fykj.wxDev.pojo.Secmenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SecmenuDao {
    Secmenu selectOne(Map<String, Object> params);

    List<Secmenu> select(Map<String, Object> params);

    Integer insert(Secmenu obj);
}