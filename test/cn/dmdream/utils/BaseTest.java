package cn.dmdream.utils;

import java.util.Random;

public class BaseTest {

	public static void main(String[] args) {
		
		//后缀名测试
		String str = "abc.jpg";
		int i = str.lastIndexOf(".") + 1;
		System.out.println(str.substring(i));
		
		//测试随机数
		System.out.println(new Random().nextInt(16) + 1);
	}
}
