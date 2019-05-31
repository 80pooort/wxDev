package com.fykj.wxDev.util;

import java.io.InputStream;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 签名工具
 * created by wujian on 2019/4/30 9:58
 */
public class SignUtil implements Serializable {

  @Autowired
  private RedisUtil redisUtil;
  /**
   * 按ascii排序并拼接参数
   * @param params 所有参数的map
   */
  public static String sortAndJoinParam(Map<String,Object> params){
    if (CollectionUtils.isEmpty(params)) {
      return null;
    }
    ArrayList<String> keyList = new ArrayList<>(params.keySet());
    Collections.sort(keyList);
    StringBuffer resultStrBuff = new StringBuffer();
    keyList.forEach(paramKey -> {
      Object paramValue = params.get(paramKey);
      resultStrBuff.append(paramKey).append("=").append(paramValue).append("&");
    });
    return resultStrBuff.substring(0,resultStrBuff.length() - 1);
  }

  /**
   * 使用秘钥签名
   * @param params 参数字符串
   * @return
   */
  public static String getSign(String params) throws Exception{
    if (StringUtils.isEmpty(params)) {
      return null;
    }
    //拿私钥
    PrivateKey privateKey = getPrivateKey("rsa/3-光大测试环境私钥.pfx","111111");
    Signature signature  = Signature.getInstance("SHA1withRSA");
    signature.initSign(privateKey);
    signature.update(params.getBytes());
    String resultStr = Base64Util.encode(signature.sign());
    return resultStr;
  }


  /**
   * 获取私钥
   * @return
   * @param strPfx 私钥文件相对路径
   * @param strPassword 密码
   */
  private static PrivateKey getPrivateKey(String strPfx, String strPassword) throws Exception{
//  从缓存里拿,缓存里没有就读文件
//   PrivateKey privateKey = (PrivateKey) redisUtil.get(Setting.GUANGDA_PRI_KEY);
//   if (privateKey != null) {
//     return privateKey;
//   }
    //设置实例类型
    KeyStore ks = KeyStore.getInstance("PKCS12");
    //私钥流文件
    ClassPathResource classPathResource = new ClassPathResource(strPfx);
    InputStream fis = classPathResource.getInputStream();
//  FileInputStream fis = new FileInputStream("C:\\Users\\wujia\\Desktop\\projectRelated\\B2B金融相关\\测试所需要的参数\\3-光大测试环境私钥.pfx");
    //设置密码
    char[] nPassword;
    if ((strPassword == null) || strPassword.trim().equals("")){
      nPassword = null;
    } else {
      nPassword = strPassword.toCharArray();
    }
    //读取私钥文件
    ks.load(fis, nPassword);
    fis.close();
    Enumeration enumas = ks.aliases();
    String keyAlias = null;
    if (enumas.hasMoreElements()) {
      keyAlias = (String)enumas.nextElement();
    }
    PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
//  Certificate cert = ks.getCertificate(keyAlias);
//  PublicKey pubkey = cert.getPublicKey();
//  redisUtil.set(Setting.GUANGDA_PRI_KEY,prikey);
    return prikey;
  }

  public static void main(String[] args) {
    /**
     * CifAddr=北京市东城区东四八条
     * &CifClientId=123456
     * &CifEnName=test
     * &CifIdExpiredDate=20200123
     * &CifName=张三
     * &CifPhoneCode=18614092852
     * &CifPostCode=100007
     * &IdNo=130825198911021432
     * &IdType=P00
     * &OperateType=0
     * &ReqId=BFinSign
     * &ReqJnlNo=t1447836389238s1
     * &ReqTime=2016-01-19 14:30:22
     * &UserId=tianronghulian
     */
    Map<String, Object> paramsMap = new HashMap<>();
    paramsMap.put("CifClientId","123456");
    paramsMap.put("CifAddr","北京市东城区东四八条");
    paramsMap.put("CifName","张三");
    paramsMap.put("CifEnName","test");
    paramsMap.put("CifIdExpiredDate","20200123");
    paramsMap.put("UserId","tianronghulian");
    paramsMap.put("ReqJnlNo","t1447836389238s1");
    paramsMap.put("ReqId","BFinSign");
    paramsMap.put("OperateType",0);
    paramsMap.put("ReqTime","2016-01-19 14:30:22");
    paramsMap.put("IdType","P00");
    paramsMap.put("IdNo","130825198911021432");
    paramsMap.put("CifPostCode","100007");
    paramsMap.put("CifPhoneCode","18614092852");
//    printRsa();
    String resurtParam = sortAndJoinParam(paramsMap);
    try {
      String sign = getSign(resurtParam);
      System.out.println(sign);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void printRsa(){
    TreeSet<String> algorithms = new TreeSet<>();
    for (Provider provider : Security.getProviders())
      for (Service service : provider.getServices())
        if (service.getType().equals("Signature"))
          algorithms.add(service.getAlgorithm());
    for (String algorithm : algorithms)
      System.out.println(algorithm);
  }
}
