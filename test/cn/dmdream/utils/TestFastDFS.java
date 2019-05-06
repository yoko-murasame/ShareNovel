package cn.dmdream.utils;

import java.util.ResourceBundle;

import org.junit.Test;

public class TestFastDFS {
	/**
	 * 需求: 测试使用工具类进行文件系统fastdfs上传操作
	 * @throws Exception
	 */
	@Test
	public void testUploadPicUtils() throws Exception{

	    //指定客户端配置文件绝对路径
	    String conf = "classpath:client.conf";	
	    //指定上传的图片地址
	    String pic = "F:\\我的图片\\214.jpg";

	    //使用工具类进行上传
	    //创建工具类对象
	    FastDFSClient fds = new FastDFSClient(conf);
	    //上传
	    String url = fds.uploadFile(pic);

	    System.out.println(url);
		//从配置文件获取拼接的前串http://193.112.41.124/
		ResourceBundle resourceBundle = ResourceBundle.getBundle("resources");
		String head = resourceBundle.getString("IMAGE_SERVER_URL");
		url = head + url;
		//最终图片地址
		//http://193.112.41.124/group1/M00/00/00/rBAABVzNhFKAXNXiAAEviRJSH0M870.jpg
		System.out.println(url);
	}
}
