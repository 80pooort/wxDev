package com.fykj.wxDev.pojo;

import java.io.Serializable;
import java.util.List;

public class Secmenu implements Serializable {
    private Integer id;
    private String name;
    private Integer parentId;
    private List<ThreeMenu> sub_button;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<ThreeMenu> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<ThreeMenu> sub_button) {
        this.sub_button = sub_button;
    }
}