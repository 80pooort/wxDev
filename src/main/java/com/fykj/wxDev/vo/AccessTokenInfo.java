package com.fykj.wxDev.vo;

/**
 * @Author: wujian
 * @Date: 2018/11/1 20:29
 */
public class AccessTokenInfo {
    public static AccessToken accessToken;

    public static AccessToken getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(AccessToken accessToken) {
        AccessTokenInfo.accessToken = accessToken;
    }
}
