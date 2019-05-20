package com.fykj.wxDev.util;

import com.fykj.wxDev.vo.WXUserInfo;
import com.google.gson.Gson;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * bean工具类
 * created by wujian on 2019/5/7 17:32
 */
public class BeanUtil implements Serializable {

  /**
   * 通过反射将map转为指定的bean对象
   * @param mp
   * @param beanCls
   * @param <T>
   * @param <K>
   * @param <V>
   * @return
   * @throws Exception
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  public static <T, K, V> T map2BeanByReflect(Map<K, V> mp, Class<T> beanCls)
      throws Exception, IllegalArgumentException, InvocationTargetException {
    T t = null;
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(beanCls);
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

      t = beanCls.newInstance();
      for (PropertyDescriptor property : propertyDescriptors) {
        String key = property.getName();

        if (mp.containsKey(key)) {
          Object value = mp.get(key);
          // Java中提供了用来访问某个属性的
          Method setter = property.getWriteMethod();
          // getter/setter方法
          setter.invoke(t, value);
        }
      }
    } catch (IntrospectionException e) {
      e.printStackTrace();
    }
    return t;
  }

  /**
   * 通过反射将bean对象转为map
   * @param bean
   * @param <T>
   * @param <K>
   * @param <V>
   * @return
   * @throws Exception
   * @throws IllegalAccessException
   */
  public static <T, K, V> Map<String, Object> bean2MapByReflect(T bean)
      throws Exception, IllegalAccessException {
    if (bean == null) {
      return null;
    }

    Map<String, Object> mp = new HashMap<>();
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

      for (PropertyDescriptor property : propertyDescriptors) {
        String key = property.getName();
        if (!key.equals("class")) {
          // Java中提供了用来访问某个属性的
          Method getter = property.getReadMethod();
          // getter/setter方法
          Object value;
          value = getter.invoke(bean);
          mp.put(key, value);
        }
      }
    } catch (IntrospectionException e) {
      e.printStackTrace();
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return mp;
  }

  /**
   * 通过json将Map转成bean对象
   * @param mp
   * @param beanCls
   * @param <T>
   * @param <K>
   * @param <V>
   * @return
   */
  public static <T,K,V> T map2BeanByGson(Map<K, V> mp, Class<T> beanCls){
    if (mp.isEmpty()) {
      return null;
    }
    Gson gson = new Gson();
    String dataJsonStr = gson.toJson(mp);
    T t = gson.fromJson(dataJsonStr, beanCls);
    return t;
  }

  /**
   * 通过json将bean转成map
   * @param bean
   * @param <T>
   * @param <K>
   * @param <V>
   * @return
   */
  public static <T,K,V> Map<K,V> bean2MapByGson(T bean){
    if (bean == null) {
      return null;
    }
    Gson gson = new Gson();
    String dataJsonStr = gson.toJson(bean);
    Map resultMap = gson.fromJson(dataJsonStr, Map.class);
    return resultMap;
  }

  public static void main(String[] args) {
    Map<String, Object> map = new HashMap<>();
    map.put("subcribe","1");
    map.put("openid","qllllrrrr");
    map.put("nickname","男");
    map.put("groupid",12345L);
    try {
//      WXUserInfo wxUserInfo = map2BeanByReflect(map, WXUserInfo.class);
      WXUserInfo wxUserInfo1 = map2BeanByGson(map, WXUserInfo.class);
//      Map<String, Object> bean2Map = bean2MapByReflect(wxUserInfo);
//      Map<String, Object> bean2Map2 = bean2MapByGson(wxUserInfo);
      System.out.println(wxUserInfo1);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
