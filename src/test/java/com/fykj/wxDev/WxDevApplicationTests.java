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

	@Test
	public void contextLoads() {
		wxMenuServer.testMenuFun();
	}

}
