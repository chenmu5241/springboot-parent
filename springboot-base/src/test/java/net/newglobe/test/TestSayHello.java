package net.newglobe.test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.newglobe.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class TestSayHello {

	@Test
	public void testDate() throws ParseException  {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sf.parse("2019-04-03 23:59:59");
		Date date2 = new Date(date.getTime() + 1000);// 加1秒时间
		String formatDate = sf.format(date2);
		System.out.println(formatDate);
	}
	
	

}
