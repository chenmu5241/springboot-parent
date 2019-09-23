package net.newglobe.test;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;

import net.newglobe.ApiApplication;
import net.newglobe.app.model.SysAccount;

@SpringBootTest(classes = ApiApplication.class)
public class TestComonsLang3 {
	@Test
	public void testDate() {
		try {
			// StringUtils常用方法
			System.out.println(StringUtils.isBlank("   "));// true----可以验证null, ""," "等
			System.out.println(StringUtils.isBlank("null"));// false
			System.out.println(StringUtils.isAllLowerCase("null"));// t
			System.out.println(StringUtils.isAllUpperCase("XXXXXX"));// t
			System.out.println(StringUtils.isEmpty(" "));// f---为null或者""返回true
			System.out.println(StringUtils.defaultIfEmpty(null, "default"));// 第二个参数是第一个为null或者""的时候的取值
			System.out.println(StringUtils.defaultIfBlank("    ", "default"));//// 第二个参数是第一个为null或者""或者" "的时候的取值
			
			System.out.println(StringUtils.abbreviate("15800418543", -1,10));
			
			for (int i = 0; i < 10; i++) {
				System.out.println(RandomUtils.nextInt(10, 11));
			}
			SysAccount a = new SysAccount();
			a.setUsername("u11");
			SysAccount clone = (SysAccount)BeanUtils.cloneBean(a);
			clone.setUsername("z1");
			BeanUtils.copyProperties(a, clone);
			System.out.println(JSON.toJSONString(a));
			System.out.println(JSON.toJSONString(clone));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
