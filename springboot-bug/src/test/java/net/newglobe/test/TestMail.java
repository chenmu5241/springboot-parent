package net.newglobe.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.newglobe.Application;
import net.newglobe.util.MailUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class TestMail {
	
	@Autowired
	private MailUtil mailUtil;
	
	@Test
	public void testSendMail() {
		mailUtil.sendSms("462372228@qq.com", "今日天气", "今天最高温度25度！");
	}

}
