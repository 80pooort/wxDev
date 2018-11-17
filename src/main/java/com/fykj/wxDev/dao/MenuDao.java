package com.fykj.wxDev.dao;

import com.fykj.wxDev.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuDao {

   List<Menu> findMenuForWx();
}