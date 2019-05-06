package cn.dmdream.utils;

public class BaseTest {

	public static void main(String[] args) {
		
		//后缀名测试
		String str = "abc.jpg";
		int i = str.lastIndexOf(".") + 1;
		System.out.println(str.substring(i));
	}
}
