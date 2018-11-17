package com.fykj.wxDev.dao;

import com.fykj.wxDev.pojo.Secmenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SecmenuDao {
   List<Secmenu> findAllSecMenu(@Param("id") int id);
}