package com.fykj.wxDev.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * created by wujian on 2019/4/24 10:36
 */
public class HttpUtil implements Serializable {
  public static String post(String requestUrl, String accessToken, String params)
      throws Exception {
    String contentType = "application/x-www-form-urlencoded";
    return HttpUtil.post(requestUrl, accessToken, contentType, params);
  }

  public static String post(String requestUrl, String accessToken, String contentType, String params)
      throws Exception {
    String encoding = "UTF-8";
    if (requestUrl.contains("nlp")) {
      encoding = "GBK";
    }
    return HttpUtil.post(requestUrl, accessToken, contentType, params, encoding);
  }

  public static String post(String requestUrl, String accessToken, String contentType, String params, String encoding)
      throws Exception {
    String url = requestUrl + "?access_token=" + accessToken;
    return HttpUtil.postGeneralUrl(url, contentType, params, encoding);
  }

  public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
      throws Exception {
    URL url = new URL(generalUrl);
    // 打开和URL之间的连接
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    // 设置通用的请求属性
    connection.setRequestProperty("Content-Type", contentType);
    connection.setRequestProperty("Connection", "Keep-Alive");
    connection.setUseCaches(false);
    connection.setDoOutput(true);
    connection.setDoInput(true);

    // 得到请求的输出流对象
    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
    out.write(params.getBytes(encoding));
    out.flush();
    out.close();

    // 建立实际的连接
    connection.connect();
    // 获取所有响应头字段
    Map<String, List<String>> headers = connection.getHeaderFields();
    // 遍历所有的响应头字段
    for (String key : headers.keySet()) {
      System.err.println(key + "--->" + headers.get(key));
    }
    // 定义 BufferedReader输入流来读取URL的响应
    BufferedReader in = null;
    in = new BufferedReader(
        new InputStreamReader(connection.getInputStream(), encoding));
    String result = "";
    String getLine;
    while ((getLine = in.readLine()) != null) {
      result += getLine;
    }
    in.close();
    System.err.println("result:" + result);
    return result;
  }

  /**
   * 根据传入的实体字符串返回
   * @param host
   * @param path
   * @param method
   * @param headers
   * @param querys
   * @param body
   * @return
   * @throws Exception
   */
  public static HttpResponse doPost(String host, String path, String method,
      Map<String, String> headers,
      Map<String, String> querys,
      String body)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpPost request = new HttpPost(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }

    if (StringUtils.isNotBlank(body)) {
      request.setEntity(new StringEntity(body, "utf-8"));
    }

    return httpClient.execute(request);
  }


  /**
   * 根据请求方式来拼接url
   * @param host
   * @param path
   * @param querys
   * @return
   * @throws UnsupportedEncodingException
   */
  private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
    StringBuilder sbUrl = new StringBuilder();
    sbUrl.append(host);
    if (!StringUtils.isBlank(path)) {
      sbUrl.append(path);
    }
    if (null != querys) {
      StringBuilder sbQuery = new StringBuilder();
      for (Map.Entry<String, String> query : querys.entrySet()) {
        if (0 < sbQuery.length()) {
          sbQuery.append("&");
        }
        if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
          sbQuery.append(query.getValue());
        }
        if (!StringUtils.isBlank(query.getKey())) {
          sbQuery.append(query.getKey());
          if (!StringUtils.isBlank(query.getValue())) {
            sbQuery.append("=");
            sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
          }
        }
      }
      if (0 < sbQuery.length()) {
        sbUrl.append("?").append(sbQuery);
      }
    }

    return sbUrl.toString();
  }

  /**
   * 开启客户端连接
   * @param host
   * @return
   */
  private static HttpClient wrapClient(String host) {
    HttpClient httpClient = new DefaultHttpClient();
    if (host.startsWith("https://")) {
      sslClient(httpClient);
    }
    return httpClient;
  }

  /**
   * 客户端安全连接
   * @param httpClient
   */
  private static void sslClient(HttpClient httpClient) {
    try {
      SSLContext ctx = SSLContext.getInstance("TLS");
      X509TrustManager tm = new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }
        public void checkClientTrusted(X509Certificate[] xcs, String str) {

        }
        public void checkServerTrusted(X509Certificate[] xcs, String str) {

        }
      };
      ctx.init(null, new TrustManager[] { tm }, null);
      SSLSocketFactory ssf = new SSLSocketFactory(ctx);
      ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      ClientConnectionManager ccm = httpClient.getConnectionManager();
      SchemeRegistry registry = ccm.getSchemeRegistry();
      registry.register(new Scheme("https", 443, ssf));
    } catch (KeyManagementException ex) {
      throw new RuntimeException(ex);
    } catch (NoSuchAlgorithmException ex) {
      throw new RuntimeException(ex);
    }
  }
}
