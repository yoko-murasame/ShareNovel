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
	    //String pic = "C:\\Users\\KuluS\\Desktop\\网游之邪龙逆天.txt";
	    //String pic = "C:\\Users\\KuluS\\Desktop\\网游之邪龙逆天.jpg";
	    //String pic = "C:\\Users\\KuluS\\Desktop\\逆天邪神.jpg";
	    String pic = "C:\\Users\\KuluS\\Desktop\\元尊.jpg";

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
		
		//网游之邪龙逆天.txt地址
		//http://193.112.41.124/group1/M00/00/00/rBAABVzRGrKAbhKPAH1lTXI8cK8029.txt
		//网游之邪龙逆天.jpg地址
		//http://193.112.41.124/group1/M00/00/00/rBAABVzRG5qADThpAABX1u4KyUQ675.jpg
		//逆天邪神.jpg
		//http://193.112.41.124/group1/M00/00/00/rBAABVzRHPyAVKeEAAGCMX8MHUQ669.jpg
		//元尊.jpg
		//http://193.112.41.124/group1/M00/00/00/rBAABVzRHTSAWjEEAAKvF_lV0sc827.jpg
		
		System.out.println(url);
	}
}
