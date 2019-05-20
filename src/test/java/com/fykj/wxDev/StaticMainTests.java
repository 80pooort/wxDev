package com.fykj.wxDev;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * created by wujian on 2019/5/18 16:05
 */
public class StaticMainTests {
  // 总的请求个数
  public static final int requestTotal = 10000;

  // 同一时刻最大的并发线程的个数
  public static final int concurrentThreadNum = 2000;

  private static Set<String> resultSet= new HashSet();

  private static final long LIMIT = 10000000000000L;
  private static long last = 0;

  public static long getID() {
    // 10 digits.
    long id = System.currentTimeMillis() % LIMIT;
    if ( id <= last ) {
      id = (last + 1) % LIMIT;
    }
    return last = id;
  }

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    CountDownLatch countDownLatch = new CountDownLatch(requestTotal);
    Semaphore semaphore = new Semaphore(concurrentThreadNum);
    for (int i = 0; i< requestTotal; i++) {
      executorService.execute(()->{
        try {
          semaphore.acquire();
          String id = UUID.randomUUID().toString();
          System.out.println("id:::" + id);
          if (resultSet.contains(id)) {
            System.out.println("id重复:::"+id);
          }
          resultSet.add(id);
          semaphore.release();
        } catch (InterruptedException e) {
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    executorService.shutdown();
    System.out.println("请求完成");
  }
}
