package com.fykj.wxDev.pojo;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    private Integer id;
    private List<Secmenu> button;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Secmenu> getButton() {
        return button;
    }

    public void setButton(List<Secmenu> button) {
        this.button = button;
    }
}