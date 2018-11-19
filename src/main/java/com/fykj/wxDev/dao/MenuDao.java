package com.fykj.wxDev.dao;

import com.fykj.wxDev.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuDao {

   Menu findMenuForWx();
}