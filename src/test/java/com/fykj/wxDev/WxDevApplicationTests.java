package com.fykj.wxDev;

import com.fykj.wxDev.interfaces.WxMenuServer;
import com.fykj.wxDev.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxDevApplicationTests {

	@Autowired
	private WxMenuServer wxMenuServer;

	@Autowired
	private RedisUtil redisUtil;

	@Test
	public void contextLoads() {
		wxMenuServer.testMenuFun();
	}

	@Test
	public void testRedis(){
		redisUtil.set("sex","{sex:男}");
		redisUtil.set("sex","{sex:女}");
		System.out.println((String) redisUtil.get("sex"));
	}

	@Test
	public void test2(){
		try {
			wxMenuServer.createMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
