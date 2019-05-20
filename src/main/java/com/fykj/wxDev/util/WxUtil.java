package com.fykj.wxDev.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class WxUtil {

  private static final Logger logger = LoggerFactory.getLogger(WxUtil.class);

  /**
   * 对所有的xml节点加上CDATA标记
   */
  private static XStream xstream = new XStream(new XppDriver() {
    public HierarchicalStreamWriter createWriter(Writer out) {
      return new PrettyPrintWriter(out) {
        // 对所有xml节点的转换都增加CDATA标记
        boolean cdata = true;

        @SuppressWarnings("unchecked")
        public void startNode(String name, Class clazz) {
          super.startNode(name, clazz);
        }

        protected void writeText(QuickWriter writer, String text) {
          if (cdata) {
            writer.write("<![CDATA[");
            writer.write(text);
            writer.write("]]>");
          } else {
            writer.write(text);
          }
        }
      };
    }
  });


  /**
   * 排序
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
   * 对字符串进行加密
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
      logger.error("WxUtil sha1 加密异常：", e);
    }
    return "";
  }

  public static String getHttpsResponse(String reqUrl, String requestMethod, String outputString) {
    URL url;
    InputStream is;
    StringBuffer resultData = new StringBuffer();
    X509TrustManager xtm = new X509TrustManager() {
      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      @Override
      public void checkServerTrusted(X509Certificate[] arg0, String arg1)
          throws CertificateException {

      }

      @Override
      public void checkClientTrusted(X509Certificate[] arg0, String arg1)
          throws CertificateException {

      }

    };
    try {
      url = new URL(reqUrl);
      HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
      TrustManager[] tm = {xtm};

      SSLContext ctx = SSLContext.getInstance("TLS");
      ctx.init(null, tm, null);

      con.setSSLSocketFactory(ctx.getSocketFactory());
      con.setHostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
          return true;
        }
      });

      con.setDoInput(true); //允许输入流，即允许下载
      con.setDoOutput(true); //允许输出流，即允许上传
      con.setUseCaches(false); //不使用缓冲
      
      if (StringUtils.isNotBlank(requestMethod)) {
        con.setRequestMethod(requestMethod.toUpperCase()); //使用指定的方式
      } else {
        con.setRequestMethod("GET"); //使用get请求
      }
      // 当有数据需要提交时
      if (StringUtils.isNotBlank(outputString)) {
        OutputStream outputStream = con.getOutputStream();
        // 注意编码格式，防止中文乱码
        outputStream.write(outputString.getBytes("UTF-8"));
        outputStream.close();
      }

      is = con.getInputStream();   //获取输入流，此时才真正建立链接
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader bufferReader = new BufferedReader(isr);
      String inputLine;
      while ((inputLine = bufferReader.readLine()) != null) {
        resultData.append(inputLine).append("\n");
      }
    } catch (Exception e) {
      logger.error("WxUtil 请求获取token时异常：", e);
    }
    return resultData.toString();
  }

  /**
   * 将xml解析为map工具类
   */
  @SuppressWarnings("unchecked")
  public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
    Map<String, String> map = new HashMap<String, String>();

    // 从request中取得输入流
    InputStream inputStream = request.getInputStream();
    // 读取输入流
    SAXReader reader = new SAXReader();
    Document document = reader.read(inputStream);
    // 得到xml根元素
    Element root = document.getRootElement();
    // 得到根元素的所有子节点
    List<Element> elementList = root.elements();

    // 遍历所有子节点
    for (Element e : elementList) {
      map.put(e.getName(), e.getText());
    }

    // 释放资源
    inputStream.close();

    return map;
  }

  /**
   * 将对象转成xml格式字符串
   */
  public static <T> String messageToXml(T t) {
    if (t == null) {
      return "";
    }
    xstream.alias("xml", t.getClass());
    return xstream.toXML(t);
  }

  /**
   * 去掉json对象中某个节点
   */
  public static <T> void delJsonEle(T t, String e) {
    if (t == null || StringUtils.isEmpty(e)) {
      return;
    }

    boolean joFlag = t instanceof JSONObject;
    boolean jaFlag = t instanceof JSONArray;
    //控制递归结束,最后一个对象不是这两种类型
    if (!joFlag && !jaFlag) {
      System.out.println("不符合条件,该递归结束.");
      return;
    }
    if (joFlag) {
      JSONObject jsonObject = (JSONObject) t;
      for (String index : jsonObject.keySet()) {
        if (StringUtils.equals(e, index)) {
          jsonObject.remove(index);
          break;
        }
        delJsonEle(jsonObject.get(index), e);
      }
    }
    if (jaFlag) {
      //集合直接遍历清理
      JSONArray jsonArray = (JSONArray) t;
      if (CollectionUtils.isEmpty(jsonArray)) {
        return;
      }
      for (Object obj : jsonArray) {
        delJsonEle(obj, e);
      }
    }

  }

  /**
   * 将bean转换成map
   */
  public static <T> Map transBean2Map(T t) {
    Assert.notNull(t, "参数对象为空错误");
    HashMap<String, Object> resultMap = new HashMap<>(0);
    try {
      PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
      PropertyDescriptor[] propertyDescriptors = propertyUtilsBean.getPropertyDescriptors(t);
      Assert.notEmpty(propertyDescriptors, "对象成员属性数组为空");
      for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
        String name = propertyDescriptor.getName();
        if (StringUtils.equals(name, "class")) {
          resultMap.put(name, propertyUtilsBean.getNestedProperty(t, name));
        }
      }
    } catch (Exception e) {
      logger.error("bean 转换成 map异常", e);
    }
    return resultMap;
  }

  /**
   * url参数拼接
   */
  public static String unionUrl(String sourceUrl, Map<String, String> params) throws Exception{
    if (StringUtils.isBlank(sourceUrl)) {
      throw new IllegalArgumentException("链接地址为空!");
    }
    if (CollectionUtils.isEmpty(params)) {
      return sourceUrl;
    }
    StringBuffer stringBuffer = new StringBuffer(sourceUrl);
    if (sourceUrl.indexOf("?") == -1) {
      stringBuffer.append("?");
    }else{
      stringBuffer.append("&");
    }
    for (Map.Entry<String, String> entry : params.entrySet()) {
      String name = entry.getKey();
      String value = entry.getValue();
      stringBuffer.append(name).append("=").append(value).append("&");
    }
    return stringBuffer.substring(0,stringBuffer.lastIndexOf("&"));
  }

}
