package com.fykj.wxDev.vo;

/**
 * AccessToken封装类
 */
public class AccessToken {
    private String accessToken;
    private Integer expiresin;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(Integer expiresin) {
        this.expiresin = expiresin;
    }
}
