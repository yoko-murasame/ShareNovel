package cn.dmdream.utils;

import org.junit.Assert;
import org.junit.Test;

public class EmailUitlTest {
	@Test
	public void sendEmailTest() {
		int ret=EmailUitl.AuthUserEmail("675274398@qq.com", "http://www.baidu.com");
		Assert.assertEquals(1,ret);
	}
}
