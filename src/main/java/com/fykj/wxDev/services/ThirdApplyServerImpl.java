package com.fykj.wxDev.services;

import com.alibaba.fastjson.JSON;
import com.fykj.wxDev.interfaces.ThirdApplyServer;
import com.fykj.wxDev.util.Base64Util;
import com.fykj.wxDev.util.FileUtil;
import com.fykj.wxDev.util.HttpUtil;
import com.fykj.wxDev.util.RedisUtil;
import com.fykj.wxDev.util.WxUtil;
import com.fykj.wxDev.vo.Setting;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * created by wujian on 2019/4/24 9:57
 */
public class ThirdApplyServerImpl implements ThirdApplyServer {

  @Autowired
  private RedisUtil redisUtil;

  /**
   * 对外服务,获取百度accessToken,缓存处理
   * @return
   */
  @Override
  public String getAccessTokenForOut() throws Exception {
    String baiduAccessToken = (String)redisUtil.get(Setting.BAIDU_ACCESS_TOKEN);
    if (StringUtils.isNotBlank(baiduAccessToken)) {
      return baiduAccessToken;
    }
    String accessTokenResult = getAccessTokenFromBaidu();
    JSONObject jsonObject = new JSONObject(accessTokenResult);
    String accessToken = jsonObject.getString("access_token");
    long expiresIn = jsonObject.getLong("expires_in");
    redisUtil.set(Setting.BAIDU_ACCESS_TOKEN,accessToken,expiresIn);
    return accessToken;
  }

  /**
   * 根据图片路径和,正反面参数获取身份证相关信息
   * @param imgPath
   * @param cardSide
   * @return
   */
//  @Override
  public static String getIdentieyCardInfo(String imgPath, String cardSide) throws Exception {
    byte[] imgData = FileUtil.readFileByBytes(imgPath);
    String imgStr = Base64Util.encode(imgData);
    String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
    String accessToken = "24.b2a28a17c7da5b8d21c8c7101abe55aa.2592000.1558664963.282335-16097522";
    String params = "id_card_side=%s&"+ URLEncoder
        .encode("image", "UTF-8") +"=%s";
    String image = URLEncoder.encode(imgStr,"UTF-8");
    String idCardParams = String.format(params, cardSide, image);
    String idCardResult = HttpUtil.post(idcardIdentificate, accessToken, idCardParams);
    return idCardResult;
  }

  /**
   * 请求百度api获取账号下的accessToken,key根据环境可配置
   * @return
   */
  @Override
  public String getAccessTokenFromBaidu() throws Exception {
    String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s";
    String apiKey = "l4IPsi1k1THmIuhPjdHiqhQI";
    String secretKey = "Y9avfGL0HOTcDq3Y8aXn0iYVyLydBZIU";
    String tokenUrl = String.format(url, apiKey, secretKey);
    String baiduTokenResult = WxUtil.getHttpsResponse(tokenUrl, "GET", null);
    return baiduTokenResult;
  }

  /**
   * 根据图片获取身份证信息,阿里
   * @param imgPath
   * @param cardSide
   * @return
   * @throws Exception
   */
//  @Override
  public static String getIdentieyCardInfoFromAliyun(String imgPath, String cardSide) throws Exception {
    String host = "https://dm-51.data.aliyun.com";
    String path = "/rest/160601/ocr/ocr_idcard.json";
    String appcode = "03dca81e32e94d599f7a7b640cae080e";
    //请根据线上文档修改configure字段
    JSONObject configObj = new JSONObject();
    configObj.put("side", "face");
    String config_str = configObj.toString();
    String method = "POST";
    Map<String, String> headers = new HashMap<String, String>();
    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
    headers.put("Authorization", "APPCODE " + appcode);

    Map<String, String> querys = new HashMap<String, String>();

    // 对图像进行base64编码
    byte[] imgData = FileUtil.readFileByBytes(imgPath);
    String imgBase64 = Base64Util.encode(imgData);
    // 拼装请求body的json字符串
    JSONObject requestObj = new JSONObject();
    requestObj.put("image", imgBase64);
    if(config_str.length() > 0) {
      requestObj.put("configure", config_str);
    }
    String bodys = requestObj.toString();

    HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
    int stat = response.getStatusLine().getStatusCode();
    if (stat != 200) {
        return null;
      }
    String res = EntityUtils.toString(response.getEntity());
    return res;

  }


  /**
   * 根据银行卡图片获取图片信息
   * @param imgPath
   * @return
   * @throws Exception
   */
//  @Override
  public static String getBankCardInfo(String imgPath) throws Exception {
    byte[] imgData = FileUtil.readFileByBytes(imgPath);
    String imgStr = Base64Util.encode(imgData);
    String bankcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/bankcard";
    String accessToken = "24.b2a28a17c7da5b8d21c8c7101abe55aa.2592000.1558664963.282335-16097522";
    String params = URLEncoder.encode("image", "UTF-8") +"=%s";
    String image = URLEncoder.encode(imgStr,"UTF-8");
    String bankCardParams = String.format(params,image);
    String result = HttpUtil.post(bankcardIdentificate, accessToken, bankCardParams);
    return result;
  }

  public static void main(String[] args) {
    try {
//      getIdentieyCardInfo("C:\\Users\\wujia\\Desktop\\image\\副本1.jpg","front");
//      getBankCardInfo("C:\\Users\\wujia\\Desktop\\image\\timg.jpg");
      getIdentieyCardInfoFromAliyun("C:\\Users\\wujia\\Desktop\\image\\副本1.jpg","face");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
