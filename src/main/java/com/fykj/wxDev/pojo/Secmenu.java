package com.fykj.wxDev.pojo;

import java.io.Serializable;
import java.util.List;

public class Secmenu implements Serializable {
    private Integer id;
    private String name;
    private Integer parentId;
    private List<ThreeMenu> threeMenus;

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

    public List<ThreeMenu> getThreeMenus() {
        return threeMenus;
    }

    public void setThreeMenus(List<ThreeMenu> threeMenus) {
        this.threeMenus = threeMenus;
    }
}