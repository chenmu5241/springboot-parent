package net.newglobe.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import net.newglobe.ApiApplication;

@SpringBootTest(classes=ApiApplication.class)
public class TestRestTemplate {
	
	@Test
	public void test() {
		RestTemplate restTemplate = new RestTemplate();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("url", "http://localhost:8080/sss");
		param.put("token1", "http://localhost:8080/sss");
		String str = restTemplate.getForObject("http://localhost:8089/api/isLogin", String.class,param);
		System.out.println(str);
	}

}
