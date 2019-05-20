package com.fykj.wxDev.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * created by wujian on 2019/4/24 10:40
 */
public class FileUtil implements Serializable {
  /**
   * 读取文件内容，作为字符串返回
   */
  public static String readFileAsString(String filePath) throws IOException {
    File file = new File(filePath);
    if (!file.exists()) {
      throw new FileNotFoundException(filePath);
    }

    if (file.length() > 1024 * 1024 * 1024) {
      throw new IOException("File is too large");
    }

    StringBuilder sb = new StringBuilder((int) (file.length()));
    // 创建字节输入流
    FileInputStream fis = new FileInputStream(filePath);
    // 创建一个长度为10240的Buffer
    byte[] bbuf = new byte[10240];
    // 用于保存实际读取的字节数
    int hasRead = 0;
    while ( (hasRead = fis.read(bbuf)) > 0 ) {
      sb.append(new String(bbuf, 0, hasRead));
    }
    fis.close();
    return sb.toString();
  }

  /**
   * 根据文件路径读取byte[] 数组
   */
  public static byte[] readFileByBytes(String filePath) throws IOException {
    File file = new File(filePath);
    if (!file.exists()) {
      throw new FileNotFoundException(filePath);
    } else {
      ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
      BufferedInputStream in = null;

      try {
        in = new BufferedInputStream(new FileInputStream(file));
        short bufSize = 1024;
        byte[] buffer = new byte[bufSize];
        int len1;
        while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
          bos.write(buffer, 0, len1);
        }

        byte[] var7 = bos.toByteArray();
        return var7;
      } finally {
        try {
          if (in != null) {
            in.close();
          }
        } catch (IOException var14) {
          var14.printStackTrace();
        }

        bos.close();
      }
    }
  }

  /**
   * 根据服务器图片路径获取图片字节码
   */
  public static byte[] readFileByBytesFromHttp(String imageFile) throws IOException{
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] data = new byte[1024];
    InputStream in = null;
    try {
      HttpURLConnection connection=(HttpURLConnection)new URL(imageFile).openConnection();
      connection.setReadTimeout(5000);
      connection.setConnectTimeout(5000);
      connection.setRequestMethod("GET");
      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        in = connection.getInputStream();
      }
      int len;
      while (-1 != (len = in.read(data,0,1024))){
        out.write(data,0,len);
      }
      return out.toByteArray();
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
      out.close();
    }
  }
}
