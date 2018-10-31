package com.fykj.wxDev.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class WxUtil {

    /**
     * 排序
     * @param strArry
     * @return
     */
    public static String sort(String... strArry) {
        Arrays.sort(strArry);
        StringBuilder stringBuilder = new StringBuilder();
        for (String parm : strArry) {
            stringBuilder.append(parm);
        }
        return stringBuilder.toString();
    }

    /**
     * 加密
     */
    public static String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
