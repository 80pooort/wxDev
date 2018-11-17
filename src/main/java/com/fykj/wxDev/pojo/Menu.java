package com.fykj.wxDev.pojo;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    private Integer id;
    private List<Secmenu> secmenus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Secmenu> getSecmenus() {
        return secmenus;
    }

    public void setSecmenus(List<Secmenu> secmenus) {
        this.secmenus = secmenus;
    }
}