package cn.dmdream.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnAdmin;
import cn.dmdream.utils.FastDFSClient;
import cn.dmdream.vo.JsonMsg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestServlet extends BaseServlet {

	// 指定客户端配置文件绝对路径,配置文件上传对象
	private static String conf = "classpath:client.conf";
	protected static FastDFSClient fds;
	static {
		try {
			fds = new FastDFSClient(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//测试请求转发跳转
	public String toSuccess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("进入了请求转发测试方法");
		return "/resultPage/success.jsp";
	}
	//测试文件上传
	public String fileUpload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("进入了文件上传方法");
		// 1.存储表单中数据
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 2.利用req.getInputStream();获取到请求体中全部数据,进行拆分和封装
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			List<FileItem> list = upload.parseRequest(req);
			String path = "";
			// 3.遍历集合
			for (FileItem item : list) {
				if (item.isFormField()) {
					// 4.如果当前的FileItem对象是普通项
					// 将普通项上name属性的值作为键,将获取到的内容作为值,放入MAP中
					// {username<==>tom,password<==>1234}
					map.put(item.getFieldName(), item.getString("utf-8"));
				} else {
					// 5.如果当前的FileItem对象是上传项
					// 通过FileItem获取到输入流对象,通过输入流可以获取到图片二进制数据
					InputStream is = item.getInputStream();
					// 获取后缀
					String fullName = item.getName();
					String extName = fullName.substring(fullName.lastIndexOf(".") + 1);
					// 调用fastDFS上传文件
					path = fds.uploadFile(is, extName);
					// 从配置文件获取拼接的前串http://193.112.41.124/
					ResourceBundle resourceBundle = ResourceBundle.getBundle("resources");
					String head = resourceBundle.getString("IMAGE_SERVER_URL");
					// 最终图片路径
					path = head + path;
					System.out.println(path);
				}
			}
			// 6.将图片地址返回
			// http://193.112.41.124/group1/M00/00/00/rBAABVzNloqAZyZpAAebYAJE1TA251.jpg
			resp.sendRedirect(path);
			return null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//测试文本编码过滤器和json文本类型设置
	public String testEncodeAndJson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("username");
		SnAdmin snAdmin = new SnAdmin();
		snAdmin.setAdminUsername(username);
		//新建JsonMsg
		JsonMsg success = JsonMsg.makeSuccess("请求成功!", snAdmin);
		//转换成json数组
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonStr = objectMapper.writeValueAsString(success);
		//通过response返回
		resp.getWriter().write(jsonStr);
		return null;
	}
	
	//测试个人中心-我的书架
	
	
}
