package com.fykj.wxDev;

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
	private RedisUtil redisUtil;

	@Test
	public void contextLoads() {
//		boolean set = redisUtil.set("gender", "男");
		Object gender = redisUtil.get("age");
		System.out.println(gender);
	}

}
