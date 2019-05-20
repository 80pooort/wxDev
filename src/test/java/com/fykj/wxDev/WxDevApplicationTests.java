package com.fykj.wxDev;

import com.fykj.wxDev.interfaces.WxMenuServer;
import com.fykj.wxDev.interfaces.WxSignServer;
import com.fykj.wxDev.util.RedisUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

	@Autowired
	private WxSignServer wxSignServer;

	@Test
	public void testRedis(){
		redisUtil.set("sex","{sex:男}");
		redisUtil.set("sex","{sex:女}");
		System.out.println((String) redisUtil.get("sex"));
	}

	public void testGetUserInfo() throws Exception{
		String openId = "";
		wxSignServer.getWXUserInfoByOpenId(openId);
	}

	@Test
	public void testCreateMenu(){
		try {
			wxMenuServer.createMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMQ() {
		try {
			wxSignServer.testMQ();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCust(){
		File csv = new File("C:\\Users\\wujia\\Desktop\\cust.csv");  // CSV文件路径
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		String line = "";
		String everyLine = "";
		try {
			List<String> allString = new ArrayList<>();
			while ((line = br.readLine()) != null)  //读取到的内容给line变量
			{
				everyLine = line;
				System.out.println(everyLine);
				allString.add(everyLine);
			}
			System.out.println("csv表格中所有行数："+allString.size());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
