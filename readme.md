[TOC]

# 星象小说
搜索服务见 https://github.com/yoko-murasame/ShareNovel_Search
在线演示视频：http://img.dmdream.cn/starnovel.mp4
## 前言

请不要用于商业用途

文档中可能留存有我编码测试时使用的服务器（地址），请改成你自己的服务器地址

PS：请不要攻击我，谢谢QWQ

所有编码格式一律使用UTF-8

## 架构设计

### 工程目录

* src-源代码

  * cn.dmdream
    * controller
    * service
    * dao
    * entity
    * utils-存放工具类

* test-单元测试/其他测试

  * 测试类的包结构应与src下相同

* config-存放配置文件

* WebContent

  > 注意页面引入js插件(jq,bootstrap一律使用CDN加速)

  开发版

  ```html
  <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/css/bootstrap.css" rel="stylesheet">
  <script src="https://cdn.bootcss.com/jquery/3.4.0/jquery.js"></script>
  <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/js/bootstrap.js"></script>
  ```

  生产版

  ```html
  <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.bootcss.com/jquery/3.4.0/jquery.min.js"></script>
  <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/js/bootstrap.min.js"></script>
  ```

  * img
  * admin
  * novel
  * search

### 继承Servlet工具类

##### 1.工具类

> 请将所有后续创建的servlet继承该servlet

```java
package cn.dmdream.controller.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
	
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// localhost:8080/store/productServlet?method=addProduct
		String method = req.getParameter("method");
		if (null == method || "".equals(method) || method.trim().equals("")) {
			method = "execute";
		}

		// 注意:此处的this代表的是子类的对象
		// System.out.println(this);
		// 子类对象字节码对象
		Class clazz = this.getClass();

		try {
			// 查找子类对象对应的字节码中的名称为method的方法.这个方法的参数类型是:HttpServletRequest.class,HttpServletResponse.class
			Method md = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			if(null!=md){
				String jspPath = (String) md.invoke(this, req, resp);
				if (null != jspPath) {
					req.getRequestDispatcher(jspPath).forward(req, resp);
				}
			}
		} catch (Exception e) {
			String message = e.getMessage();
			System.out.println(message);
			if(message.indexOf("Admin") > 0){
				//访问不到方法时跳转
				req.getRequestDispatcher("/admin/login.jsp").forward(req, resp);
			}else{
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
		}

	}

	// 默认方法
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}

}
```

##### 2.注意

> 继承baseServlet后, 后续的所有请求格式为
>
> 前台:
> `servlet映射路径.do?method=方法名&其他参数1&其他参数2...`
>
> 后台:
> 直接`return 需要请求转发的路径;`
>
> PS: 重定向还需要以原先的方式写`resp.sendRedirect(url)` 

**示例代码**

html

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>测试页面</title>
<link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/css/bootstrap.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/jquery/3.4.0/jquery.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/js/bootstrap.js"></script>
</head>
<body>
<div class="container">
	<ul>
		<li><a class="btn btn-default" href="testServlet.do?method=toSuccess">点我测试跳转</a></li>
	
		<li>
			<form action="testServlet.do?method=fileUpload" method="post" enctype="multipart/form-data">
				<input type="file" name="pic"/>
				<input type="submit" class="btn btn-default" value="点我文件上传" />
			</form>
		</li>
	</ul>
</div>
</body>
</html>
```

controller

```java
package cn.dmdream.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.utils.ByteToInputStream;
import cn.dmdream.utils.FastDFSClient;

public class TestServlet extends BaseServlet {
	
    //指定客户端配置文件绝对路径,配置文件上传对象
    private static String conf = "classpath:client.conf";
    protected static FastDFSClient fds;
    static{
    	try {
    		fds = new FastDFSClient(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	//请求转发测试
	public String toSuccess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("进入了请求转发测试方法");
		return "/resultPage/success.jsp";
	}

    //文件上传测试
	public String fileUpload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("进入了文件上传方法");
		//1.存储表单中数据
		Map<String, String> map = new HashMap<String, String>();
		try {
			//2.利用req.getInputStream();获取到请求体中全部数据,进行拆分和封装
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			List<FileItem> list = upload.parseRequest(req);
			String path = "";
			//3.遍历集合
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
					//获取后缀
					String fullName = item.getName();
					String extName = fullName.substring(fullName.lastIndexOf(".") + 1);
					//调用fastDFS上传文件
					path = fds.uploadFile(is,extName);
					//从配置文件获取拼接的前串
					ResourceBundle resourceBundle = ResourceBundle.getBundle("resources");
					String head = resourceBundle.getString("IMAGE_SERVER_URL");
					//最终图片路径
					path = head + path;
					System.out.println(path);
				}
			}
			//6.将图片地址返回
			resp.sendRedirect(path);//重定向返回因此return null
			return null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}

```



### 公共页面的设计(重要)

##### 1.页首

**功能:**

* 网站logo
* 用户登录注册
* 导航栏
* 引入JQ/BootStrap等其他插件



```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>星象小说</title>
		<!-- 公共css -->
		<link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/css/bootstrap.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/css/user/style.css" rel="stylesheet"/>
		<link href="${pageContext.request.contextPath}/js/admin/assets/js/gritter/css/jquery.gritter.css" rel="stylesheet"/>
		<!-- 当前页面css -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mainpage.css">
		<!-- 公共js -->
		<script src="https://cdn.bootcss.com/jquery/3.4.0/jquery.min.js"></script>
		<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/admin/admin-common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/assets/js/gritter/js/jquery.gritter.js"></script>
		<!-- 当前页面js -->
		<script type="text/javascript">
			$(function() {
				//首页点击搜索按钮 访问搜索servlet请求转发
				$("#btnSearch").click(function() {
					var str = $("#searchStr").val();
					var url = "${pageContext.request.contextPath}/userSearch.do?method=toSearchPage&keyword=" + str;
					window.location.href = url;
				})
			})
		</script>
	</head>
	<body>
		<!--描述：菜单栏-->
		<div class="container-fluid" style="margin: 10px auto;">
			<div class="col-md-4 col-sm-4 col-xs-4">
				<a href="${pageContext.request.contextPath}/"><img src="img/logo.beebc.png"></a>
			</div>
			<div class="col-md-5 col-sm-5 col-xs-5">
				
			</div>
			<div class="col-md-3 col-sm-3 col-xs-3" style="padding-top:20px;display:flex;justify-content:flex-end;">
				<c:if test="${empty user }">
					<div class="txtCenter" id="logintip">
						<span>亲,请 <a href="javascript:openmodel()">登录</a></span>
					</div>
					<div class="divider_1"></div>
					<div class="txtCenter">
						<a href="${pageContext.request.contextPath}/register.jsp"> <span style="color:red;">注册</span>
						</a>
					</div>
				</c:if>
				<c:if test="${not empty user }">
				
					<ol class="list-inline">
						<li><span style="float:right"><span style="color:red;">${user.userUsername }</span>,你好!</span></li>
						<li><a href="${pageContext.request.contextPath}/usercenter.jsp" class="pull-right">我的收藏</a></li>
						<li><a href="${pageContext.request.contextPath}/usercenter.jsp" class="pull-right">个人中心&nbsp;</a></li>
					</ol>
				</c:if>
			</div>
		</div>
		<!-- 菜单栏 /-->
		<!--描述：导航条-->
		<div class="container-fluid">
			<nav class="navbar navbar-inverse">
				<div class="container-fluid">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="true">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="${pageContext.request.contextPath}/">首页</a>
					</div>

					<!-- Collect the nav links, forms, and other content for toggling -->
					<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav" id="myUL">
							<li><a href="${pageContext.request.contextPath}/classfiypage.jsp">全部作品</a></li>
							<!-- <li><a href="#">排行榜</a></li>
							<li><a href="#">最近更新</a></li>
							<li><a href="#">全本小说</a></li> -->
						</ul>
						<form class="navbar-form navbar-right" role="search">
							<div class="form-group">
								<input value="${keyword }" id="searchStr" type="text" class="form-control" placeholder="请输入小说名/作者/内容">
							</div>
							<button type="button" id="btnSearch" class="btn btn-default">搜索</button>
						</form>

					</div>
					<!-- /.navbar-collapse -->
				</div>
				<!-- /.container-fluid -->
			</nav>
		</div>
		<!-- 导航条  /-->
	</body>
</html>
```



##### 2.页脚

**功能:**

* 存放登录模态框
* 存放登录注册的js监听
* 存放页脚信息/版权信息

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>星象小说</title>
	</head>
<body>
	<!--登入模态框  -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<div>
						<center>
							<h3>用户登录</h3>
						</center>
					</div>
				</div>
				<div class="modal-body">
					<form id="loginform">
						<div class="form-group">
							<input type="text" class="form-control" id="username" name="name"
								placeholder="邮箱(必须已经验证)/用户名">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="password"
								name="password" placeholder="密码">
						</div>
						<div class="form-group">
							<div style="float: left">
								<input type="checkbox" value="auto">记住密码
							</div>
							<div style="float: right">
								<a href="#" class="text-right">忘记密码</a>
							</div>
						</div>
						<div class="form-group">
							<p id="tip"></p>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-danger btn-block"
							id="loginbt">登录</button>
					</center>
					<center>
						<div>
							<a href="${pageContext.request.contextPath}/register.jsp">免费注册</a>
						</div>
					</center>
				</div>
			</div>
		</div>
	</div>
	<!--登入模态框  /-->
	<!--描述：页脚部分-->
	<div class="container-fluid" style="">
		<div style="margin-top:50px;clear:both;" class="footer text-center">
			<hr>
			<p>请所有作者发布作品时务必遵守国家互联网信息管理办法规定，我们拒绝任何色情小说，一经发现，即作删除！举报电话：110</p>
			<p>本站所收录的作品、社区话题、用户评论、用户上传内容或图片等均属用户个人行为。如前述内容侵害您的权益，欢迎举报投诉，一经核实，立即删除，本站不承担任何责任</p>
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a href="http://www.baidu.com">关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a>支付方式</a></li>
				<li><a>配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2019-2020 中软小组-码之形 版权所有
		</div>
	</div>
	<!--描述：页脚部分/-->
</body>
<!--登入模态框  /-->
<script type="text/javascript">
	//设置模态框
	function openmodel() {
		$('#myModal').modal({
			keyboard : false,
		})
	}
	$("#loginbt").click(function() {
		var data = $("#loginform").serialize();
		$.ajax({
			url : "${pageContext.request.contextPath}/user.do?method=userLogin",
			method : "post",
			data : data,
			success : function(json) {
				if (json.status == 200) {
					window.location.href = window.location.href;
				} else {
					$("#tip").text(json.msg);
				}
			}
		});
	});
</script>
</html>
```





### 图片上传使用FastDFS

##### 1.使用

1. 导入依赖jar(已导入)

   - commons-fileupload-1.3.1.jar
   - commons-io-2.5.jar
   - fastdfs-1.2.jar

2. FastDFS服务器地址配置文件client.conf

   ```shell
   tracker_server=193.112.41.124:22122
   ```

3. 资源文件resources.properties配置服务器ip和协议

   ```properties
   #图片服务器IP地址,协议
   IMAGE_SERVER_URL=http\://193.112.41.124/
   ```

4. 测试类

   ```java
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
   		System.out.println(url);
   	}
   }
   
   ```



##### 2.工具类

```java
package cn.dmdream.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSClient {
	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;
	private StorageClient1 storageClient = null;

	public FastDFSClient(String conf) throws Exception {
		if (conf.contains("classpath:")) {
			conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
			conf = java.net.URLDecoder.decode(conf, "utf-8");//防止中文路径乱码
		}
		ClientGlobal.init(conf);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient1(trackerServer, storageServer);
	}

	/**
	 * 上传文件方法
	 * <p>
	 * Title: uploadFile
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param fileName
	 *            文件全路径
	 * @param extName
	 *            文件扩展名，不包含（.）
	 * @param metas
	 *            文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
		String result = storageClient.upload_file1(fileName, extName, metas);
		return result;
	}

	public String uploadFile(String fileName) throws Exception {
		return uploadFile(fileName, null, null);
	}

	public String uploadFile(String fileName, String extName) throws Exception {
		return uploadFile(fileName, extName, null);
	}

	/**
	 * 上传文件方法
	 * <p>
	 * Title: uploadFile
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param fileContent
	 *            文件的内容，字节数组
	 * @param extName
	 *            文件扩展名
	 * @param metas
	 *            文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {

		String result = storageClient.upload_file1(fileContent, extName, metas);
		return result;
	}

	public String uploadFile(byte[] fileContent) throws Exception {
		return uploadFile(fileContent, null, null);
	}

	public String uploadFile(byte[] fileContent, String extName) throws Exception {
		return uploadFile(fileContent, extName, null);
	}
	
	/**
	 * 上传文件方法
	 * <p>
	 * Title: uploadFile
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param inputStream
	 *            文件输入流
	 * @param extName
	 *            文件扩展名
	 * @param metas
	 *            文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(InputStream inputStream, String extName, NameValuePair[] metas) throws Exception {

		String result = storageClient.upload_file1(input2byte(inputStream), extName, metas);
		return result;
	}

	public String uploadFile(InputStream inputStream) throws Exception {
		return uploadFile(inputStream, null, null);
	}

	public String uploadFile(InputStream inputStream, String extName) throws Exception {
		return uploadFile(inputStream, extName, null);
	}
	
	/**
	 * input流转byte
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
    public static final byte[] input2byte(InputStream inStream)  
            throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }
}

```

**扩展**

```java
package cn.dmdream.utils;
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
  
/**
 * 流和byte数组的相互转化
 * @author KuluS
 *
 */
public class ByteToInputStream {  
  
    public static final InputStream byte2Input(byte[] buf) {  
        return new ByteArrayInputStream(buf);  
    }  
  
    public static final byte[] input2byte(InputStream inStream)  
            throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }  
  
}
```



### 前端相关

##### 1.富文本编辑器

**快速使用**

> [官方文档](<http://kindeditor.net/doc.php>)

1. 导入页面依赖js

   ```html
   <link rel="stylesheet" href="js/kindeditor/themes/default/default.css" />
   <link rel="stylesheet" href="css/bootstrap.css" />
   <script type="text/javascript" src="js/kindeditor/kindeditor-all.js"></script>
   <script type="text/javascript" src="js/kindeditor/lang/zh-CN.js"></script>
   <script type="text/javascript" src="js/jquery-3.4.0.js"></script>
   ```

2. 在页面最底部初始化富文本编辑器对象

   ```html
   <!-- 正文区域 /-->
   <script type="text/javascript">
       var editor;
       KindEditor.ready(function(K) {
           editor = K.create('textarea[name="content"]', {
               allowFileManager : true
           });
       });
   </script>
   ```

**示例代码**

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="js/kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="css/bootstrap.css" />
		<script type="text/javascript" src="js/kindeditor/kindeditor-all.js"></script>
		<script type="text/javascript" src="js/kindeditor/lang/zh-CN.js"></script>
		<script type="text/javascript" src="js/jquery-3.4.0.js"></script>
		<title>富文本编辑器</title>
	</head>

	<body>

		<textarea id="richText" name="content"  rows="30" cols="40"></textarea>
		<button id="changeBtn" class="btn btn-default">点我将值显示到下面文本域</button>
		<button id="changeBackBtn" class="btn btn-info">点我将值返回显示到上面文本域</button>
		<textarea id="reShowText" style="width: 700px;height: 500px;border: solid 1px lightcyan;"></textarea>

		<!-- 正文区域 /-->
		<script type="text/javascript">
			var editor;
			KindEditor.ready(function(K) {
				editor = K.create('textarea[name="content"]', {
					allowFileManager: true
				});
			});
			
			$(function(){
				$("#changeBtn").click(function(){
					var txt = editor.html();
					editor.sync();//同步后可以直接从页面稳文本域中获取值
					//var txt2 = $("#richText").val();
					//console.log(txt2);
					new Promise(function(res,rej){
						$("#reShowText").val(txt);//将值赋给另一个文本域
						res("");
					}).then(function(res){
						editor.html("");
					});
				});
				$("#changeBackBtn").click(function(){
					var txt2 = $("#reShowText").val();
					//console.log(txt2);
					new Promise(function(res,rej){
						editor.html(txt2);//回显
						res("");
					}).then(function(res){
						$("#reShowText").val(res);
					});
				});
			});
		</script>
	</body>

</html>
```



##### 2.jq异步表单校验

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>表单的异步提交</title>
<script type="text/javascript" src="../js/jquery-3.4.0.js" ></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript">
	function s(){
		$("#form1").ajaxSubmit(function(data){
			alert(data);
		});
		return false;
	}
</script>
</head>
<body>
	<form id="form1" method="post" action="../date.do" onsubmit="return s()">
		用户名:<input name="username" type="text" />
		密码:<input name="password" type="password" />
		<input type="submit" />
	</form>
</body>
</html>
```

##### 3.Jackson&ajax实例

异步校验用户名

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>表单的异步提交</title>
<script type="text/javascript" src="../js/jquery-3.4.0.js" ></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript">
	$(function(){
	    $("#username").blur(function(){
	        var username = $("#username").val();
	        //把用户名异步传送服务器,进行重复校验
	        $.ajax({
	            data:{
	                username:username
	            },
	            url:"../ckun.do",
	            type:"POST",
	            success:function(data){
	                console.log(data);
	                var jsonObj = JSON.parse(data);//若后端未声明文本类型,需要转换
	                console.log(jsonObj);
	            }
	        });
	    });
	});
	
	function s(){
		$("#form1").ajaxSubmit(function(data){
			alert(data);
		});
		return false;
	}
</script>
</head>
<body>
	<form id="form1" method="post" action="../date.do" onsubmit="return s()">
		用户名:<input id="username" name="username" type="text" />
		密码:<input name="password" type="password" />
		<input type="submit" />
	</form>
</body>
</html>
```

servlet

```java
public class CheckUsernameAjaxServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		System.out.println(username);
		response.setContentType("text/html");
		JsonMsg jsonMsg = JsonMsg.makeSuccess("你传过来的用户名", username);
		//序列化jsonMsg
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
        response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jsonStr);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

```



##### 4.跨域问题

>  [跨域问题的解决办法](<https://www.cnblogs.com/beidan/p/5269817.html>) 
>
>  [ajax跨域问题](<https://blog.csdn.net/a241903820/article/details/80602309>) 

* spring跨域解决方案

  > 在服务端(被请求的) 类上或方法上使用注解 `@CrossOrigin` 

* servlet解决方案

  ```java
  /* 允许跨域的主机地址 */
  response.setHeader("Access-Control-Allow-Origin", "*");  
  /* 允许跨域的请求方法GET, POST, HEAD 等 */
  response.setHeader("Access-Control-Allow-Methods", "*");  
  /* 重新预检验跨域的缓存时间 (s) */
  response.setHeader("Access-Control-Max-Age", "3600");  
  /* 允许跨域的请求头 */
  response.setHeader("Access-Control-Allow-Headers", "*");  
  /* 是否携带cookie */
  response.setHeader("Access-Control-Allow-Credentials", "true");  
  ```

  过滤器版本

  ```java
  package filter;  
  
  import java.io.IOException;  
  
  import javax.servlet.Filter;  
  import javax.servlet.FilterChain;  
  import javax.servlet.FilterConfig;  
  import javax.servlet.ServletException;  
  import javax.servlet.ServletRequest;  
  import javax.servlet.ServletResponse;  
  import javax.servlet.http.HttpServletResponse;  
  
  
  public class CORSFilter implements Filter {  
  
      public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {  
          HttpServletResponse response = (HttpServletResponse) res;  
          response.setHeader("Access-Control-Allow-Origin", "<请求方域名如:http://www.sohu.com>");  
          response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");  
          response.setHeader("Access-Control-Max-Age", "3600");  
          response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");  
          response.setHeader("Access-Control-Allow-Credentials", "true");  
          chain.doFilter(req, res);  
      }  
  
      public void init(FilterConfig filterConfig) {}  
  
      public void destroy() {}  
  
  }
  ```

  



##### 5.封装Ajax请求，简化页面

1. 在外面新建独立的 `admin-common.js` 

   ```js
   //通用ajax请求函数,需要用promise接收
   function getPostAjaxPromise(url,data){
   	
   	return new Promise(function(res,rej){
   		$.ajax({
   			url: url,
   			data: data,
   			type: "POST",
   			success: function(data) {
   				res(data);
   			}
   		});
   	});
   }
   //通用ajax请求函数,数组版本,需要用promise接收
   function getPostAjaxPromiseForArray(url,data){
   	
   	return new Promise(function(res,rej){
   		$.ajax({
   			url: url,
   			data: data,
   			type: "POST",
   			dataType: "json",
   			traditional: true,
   			success: function(data) {
   				res(data);
   			}
   		});		
   	});
   }
   //通用ajax请求函数,文件上传版本,需要用promise接收
   function getPostAjaxPromiseForForm(url,form){
   	
   	return new Promise(function(res,rej){
   		$.ajax({
   			url: url,
   			data: form,
   			type: "POST",
   			dataType: "json",
   			contentType : false,
   			processData : false,
   			success: function(data) {
   				res(data);
   			}
   		});
   	});
   }
   ```

2. 页面请求方式

   1. 传统请求

      ```js
      //保存方法(save和update通用)
      function novelSave(novelObj, modalId) {
          var promiseObj = getPostAjaxPromise("${pageContext.request.contextPath}/adminNovel.do?method=addNovel", novelObj);
          promiseObj.then(function(res){
              console.log(res);
              if(res.status == 200) {
                  showGritter('成功', res.msg);
                  $(modalId).modal("hide");
                  setTimeout(function() {
                      window.location.reload();
                  }, 500);
              } else {
                  showGritter('失败', res.msg);
              }
          });
      }
      ```

   2. 数据含有数组对象

      ```js
      //删除函数
      function delNovels(ids, modalId) {
          var promiseObj = getPostAjaxPromiseForArray("${pageContext.request.contextPath}/adminNovel.do?method=delNovel",{novelIds:ids});
          promiseObj.then(function(res){
              console.log(res);
              if(res.status == 200) {
                  showGritter('成功', res.msg);
                  $(modalId).modal("hide");
                  setTimeout(function() {
                      window.location.reload();
                  }, 1000);
              } else {
                  showGritter('失败', res.msg);
                  $(modalId).modal("hide");
              }
          });
      }
      ```

   3. 数据为表单(文件上传必须)

      ```html
      <!-- 小说上传js -->
      <script type="text/javascript">
          var dragImgUpload = new DragImgUpload("#novelUpload", {
              callback : function(files) {
                  //回调函数，可以传递给后台等等
                  var file = files[0];
                  console.log(file.name);
                  var form = new FormData();
                  form.append("file", file);
                  if(/\.(txt|TXT|epub|EPUB|pdf|PDF|rar|RAR|zip|ZIP|7z|7Z)$/.test(file.name)){
                      showStikyGritter('消息', "正在上传文件······");
                      var formPromiseObj = getPostAjaxPromiseForForm("${pageContext.request.contextPath}/adminNovel.do?method=ajaxFileUpload",form);
                      formPromiseObj.then(function(res){
                          removeAllGritters()
                          setTimeout(function() {showGritter('消息', "文件上传成功！");}, 500);
                          console.log(res);
                          $("#novelDownloadurlId").val(res.data)
                          $("#reShowFileNamelId").val(file.name);
                          $("#reShowFileNamelId").attr("type","text");
                      });
                  }else{
                      showGritter('错误',"不支持的文件格式!支持以下格式:txt、epub、pdf、rar、zip、7z")
                      return false;
                  }
              //格式判断完毕
              }
              //回调函数完毕
          })
      </script>
      <!-- 封面上传js -->
      <script type="text/javascript">
          var dragImgUpload = new DragImgUpload("#picUpload", {
              callback : function(files) {
                  //回调函数，可以传递给后台等等
                  var file = files[0];
                  console.log(file.name);
                  var form = new FormData();
                  form.append("file", file);
                  if(/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(file.name)){
                      showStikyGritter('消息',"正在上传图片······");
                      var formPromiseObj = getPostAjaxPromiseForForm("${pageContext.request.contextPath}/adminNovel.do?method=ajaxFileUpload",form);
                      formPromiseObj.then(function(res){
                          removeAllGritters();
                          setTimeout(function() {showGritter('消息',"图片上传成功！");}, 500);
                          console.log(res);
                          $("#novelCoverId").val(res.data)
                      });
                  }else{
                      showGritter('错误',"不支持的图片格式!支持以下格式:gif、jpg、jpeg、png");
                      return false;
                  }
              //格式判断完毕
              }
              //回调函数完毕
          })
      </script>
      ```



##### 6.使用轻提示gritter

1. 导入js

   ```html
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/admin/assets/js/gritter/css/jquery.gritter.css" />
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/assets/js/gritter/js/jquery.gritter.js"></script>
   ```

2. 在 `admin-common.js` 中添加

   ```js
   //gritter抽取
   //显示自动隐藏的gritter
   function showGritter(title,text){
   	$.gritter.add({
   		title: title,
   		text: text,
   		sticky: false,
   		time: 3000
   	});
   	return false;
   }
   //不会隐藏的gritter
   function showStikyGritter(title,text){
   	$.gritter.add({
   		title: title,
   		text: text,
   		sticky: false,
   		time: 3000
   	});
   	return false;
   }
   //隐藏所有gritter
   function removeAllGritters(){
   	$.gritter.removeAll();
   	return false;
   }
   ```

3. 页面使用 参考前面代码

   



### 数据库相关

##### 1.配置Tomcat集成的dbcp连接池

1. WebRoot/META-INF/context.xml文件中添加配置

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <Context>
   	<Environment name="tjndi" value="hello JNDI" type="java.lang.String" />
   	<Resource name="mysqlDataSource" type="javax.sql.DataSource"
   		driverClassName="com.mysql.jdbc.Driver"
   		url="jdbc:mysql://localhost:3306/sharenovel?useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=false"
   		username="root" password="roots" maxActive="50" maxIdle="10" maxWait="60000"
   		removeAbandoned="true" removeAbandonedTimeOut="100000" />
   </Context>
   ```

##### 2.DbUtil

```java
package cn.dmdream.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class DBUtil {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public Connection getConnection() {

		Connection connection = null;
		try {
			//采用连接池技术
			Context context = new InitialContext();
			DataSource datasource = (DataSource) context.lookup("java:comp/env/mysqlDataSource");
			connection = datasource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 释放所有连接
	 */
	public void closeAll() {

		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新数据库的方法->insert/update/delete
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql, Object... params) {

		int r = 0;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			// 设置参数
			if (params != null) {
				// 不定参数是被当成数组使用
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			// r为受该sql语句影响的总记录数
			r = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return r;

	}

	/**
	 * 查询数据库的方法
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public CachedRowSet query(String sql, Object... params) {

		CachedRowSet cachedRowSet = null;

		try {
			conn = getConnection();

			// 使用cachedRowSet优化
			RowSetFactory factory = RowSetProvider.newFactory();
			cachedRowSet = factory.createCachedRowSet();

			// 通过sql语句生成预编译的操作语句
			ps = conn.prepareStatement(sql);
			// 设置参数(参数位置,参数值)
			if (params != null) {
				// 不定参数是被当成数组使用
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			// 查询得到结果集
			rs = ps.executeQuery();
			cachedRowSet.populate(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return cachedRowSet;

	}

	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param pageSize
	 *            页面记录数
	 * @param page
	 *            第几页
	 * @param params
	 *            参数
	 * @return
	 */
	public CachedRowSet queryByPage(String sql, int pageSize, int page, Object... params) {

		CachedRowSet cachedRowSet = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			// RowSet进行分页
			RowSetFactory factory = RowSetProvider.newFactory();
			cachedRowSet = factory.createCachedRowSet();
			// 参数判断
			if (pageSize < 1)
				pageSize = 1;
			if (page < 1)
				page = 1;
			// 设置一页数量
			cachedRowSet.setPageSize(pageSize);
			// 生成cachedRowSet,然后指定的条数开始
			cachedRowSet.populate(rs, (page - 1) * pageSize + 1);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}

		return cachedRowSet;
	}

}

```

##### 3.使用tomcat自带连接池测试代码出错

换用传统的连接方式

```java
package cn.dmdream.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class DbUtil {

	private static String driverName;
	private static String url;
	private static String username;
	private static String password;

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	static {
		// src目录
		ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
		driverName = resourceBundle.getString("drivername");
		url = resourceBundle.getString("url");
		username = resourceBundle.getString("username");
		password = resourceBundle.getString("password");

		// Properties properties = new Properties();
		// try {
		// properties.load(
		// new FileInputStream(new File("db.properties")));
		// // properties.load(new FileInputStream(new
		// // File("db.properties")));//必须在同级目录
		// driverName = properties.getProperty("drivername");
		// url = properties.getProperty("url");
		// username = properties.getProperty("username");
		// password = properties.getProperty("password");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public Connection getConnection() {

		Connection connection = null;
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 释放所有连接
	 */
	public void closeAll() {

		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新数据库的方法->insert/update/delete
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql, Object... params) {

		int r = 0;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			// 设置参数
			if (params != null) {
				// 不定参数是被当成数组使用
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			// r为受该sql语句影响的总记录数
			r = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return r;

	}

	/**
	 * 查询数据库的方法
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public CachedRowSet query(String sql, Object... params) {

		CachedRowSet cachedRowSet = null;

		try {
			conn = getConnection();

			// 使用cachedRowSet优化
			RowSetFactory factory = RowSetProvider.newFactory();
			cachedRowSet = factory.createCachedRowSet();

			// 通过sql语句生成预编译的操作语句
			ps = conn.prepareStatement(sql);
			// 设置参数(参数位置,参数值)
			if (params != null) {
				// 不定参数是被当成数组使用
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			// 查询得到结果集
			rs = ps.executeQuery();
			cachedRowSet.populate(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return cachedRowSet;

	}

	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param pageSize
	 *            页面记录数
	 * @param page
	 *            第几页
	 * @param params
	 *            参数
	 * @return
	 */
	public CachedRowSet queryByPage(String sql, int pageSize, int page, Object... params) {

		CachedRowSet cachedRowSet = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			// RowSet进行分页
			RowSetFactory factory = RowSetProvider.newFactory();
			cachedRowSet = factory.createCachedRowSet();
			// 参数判断
			if (pageSize < 1)
				pageSize = 1;
			if (page < 1)
				page = 1;
			// 设置一页数量
			cachedRowSet.setPageSize(pageSize);
			// 生成cachedRowSet,然后指定的条数开始
			cachedRowSet.populate(rs, (page - 1) * pageSize + 1);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}

		return cachedRowSet;
	}

}

```



##### 4.orm对象关系映射

* 在有外键的表中,实体类中尽量封装对应外键的对象属性而不是单个外键值

* 为了效率起见

  * 在SnChapterDaoImpl.java中仅封装了novelid

    ```java
    	/**
    	 * 封装数据
    	 * 
    	 * @param rs
    	 * @param list
    	 */
    	private void handleData(ResultSet rs, List<SnChapter> list) {
    		try {
    			while (rs.next()) {
    				SnChapter chapter = new SnChapter();
    				Integer chapterId = rs.getInt("chapter_id");
    				// 获取外键id查询小说主体并封装,由于效率较低,因此不查询实体封装了
    				Integer novelId = rs.getInt("chapter_novelid");
    				//SnNovel chapterNovel = snNovelDao.findById(novelId);
    				SnNovel chapterNovel = new SnNovel();
    				chapterNovel.setNovelId(novelId);
    				String chapterTitle = rs.getString("chapter_title");
    				String chapterContent = rs.getString("chapter_content");
    				// 获取章节最近的更近时间
    				String chapterUpdatetime = rs.getString("chapter_updatetime");
    				// 封装数据
    				chapter.setChapterId(chapterId);
    				chapter.setSnNovel(chapterNovel);
    				chapter.setChapterTitle(chapterTitle);
    				chapter.setChapterContent(chapterContent);
    				chapter.setChapterUpdatetime(chapterUpdatetime);
    				list.add(chapter);
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    ```



##### 5.sql语句优化*

```java
/**
 * 给定小说时,根据优先级如下
 * @param snNovel
 * @param idOrder 1.主键的升序asc/降序desc 可以为null,代表不设置
 * @param titleOrder 2.章节标题的升序asc/降序desc 可以为null,代表不设置
 * @param updatetimeOrder 3.日期的升序asc/降序desc 可以为null,代表不设置
 * @param pageSize
 * @param page
 * @return 分页查询
 */
public List<SnChapter> findByNovelByOrdersByPage(SnNovel snNovel, String idOrder,String titleOrder,String updatetimeOrder, int pageSize, int page) {
    //语句优化
    String sql = "SELECT " +
    "sn_chapter.chapter_id,"+
    "sn_chapter.chapter_novelid,"+
    "sn_chapter.chapter_title,"+
    "sn_chapter.chapter_content,"+
    "sn_chapter.chapter_updatetime "+
    "FROM "+
    "sn_chapter "+
    "WHERE "+
    "sn_chapter.chapter_novelid = ? "+
    (idOrder!=null || titleOrder !=null || updatetimeOrder != null ? "ORDER BY " : "")
    +
    (idOrder!=null ? "sn_chapter.chapter_id "+idOrder : "")
    +	
    (idOrder!=null && (titleOrder !=null||updatetimeOrder!=null) ? "," : "")
    +
    (titleOrder!=null ? "sn_chapter.chapter_title "+titleOrder : "")
    +
    (idOrder!=null && titleOrder !=null && updatetimeOrder!=null ? "," : "")
    +
    (updatetimeOrder!=null ? "sn_chapter.chapter_updatetime "+updatetimeOrder+" " : " ")
    +
    "LIMIT ?, ?";
    System.out.println(sql);
    ResultSet rs = dbUtil.query(sql, snNovel.getNovelId(), (page - 1) * pageSize, pageSize);
    List<SnChapter> list = new ArrayList<SnChapter>();
    handleData(rs, list);
    return list.size() > 0 ? list : null;
};
```



##### 6.c3p0数据库连接池版本的DBUtil

```java
package cn.dmdream.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbUtil {

	private static String driverName;
	private static String url;
	private static String username;
	private static String password;
	private static ComboPooledDataSource dataSource = null;

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	static {
		// src目录
		ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
		driverName = resourceBundle.getString("drivername");
		url = resourceBundle.getString("url");
		username = resourceBundle.getString("username");
		password = resourceBundle.getString("password");
		
	    //1. 创建datasource
	    //ComboPooledDataSource dataSource = new ComboPooledDataSource("oracle");
	    dataSource = new ComboPooledDataSource();

	    //2. 设置连接数据的信息
	    try {
			dataSource.setDriverClass(driverName);
		    //忘记了---> 去以前的代码 ---> jdbc的文档
		    dataSource.setJdbcUrl(url);
		    dataSource.setUser(username);
		    dataSource.setPassword(password);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public Connection getConnection() {

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 释放所有连接
	 */
	public void closeAll() {

		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新数据库的方法->insert/update/delete
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql, Object... params) {

		int r = 0;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			// 设置参数
			if (params != null) {
				// 不定参数是被当成数组使用
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			// r为受该sql语句影响的总记录数
			r = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return r;

	}

	/**
	 * 查询数据库的方法
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public CachedRowSet query(String sql, Object... params) {

		CachedRowSet cachedRowSet = null;

		try {
			conn = getConnection();

			// 使用cachedRowSet优化
			RowSetFactory factory = RowSetProvider.newFactory();
			cachedRowSet = factory.createCachedRowSet();

			// 通过sql语句生成预编译的操作语句
			ps = conn.prepareStatement(sql);
			// 设置参数(参数位置,参数值)
			if (params != null) {
				// 不定参数是被当成数组使用
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			// 查询得到结果集
			rs = ps.executeQuery();
			cachedRowSet.populate(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return cachedRowSet;

	}

	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param pageSize
	 *            页面记录数
	 * @param page
	 *            第几页
	 * @param params
	 *            参数
	 * @return
	 */
	public CachedRowSet queryByPage(String sql, int pageSize, int page, Object... params) {

		CachedRowSet cachedRowSet = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			// RowSet进行分页
			RowSetFactory factory = RowSetProvider.newFactory();
			cachedRowSet = factory.createCachedRowSet();
			// 参数判断
			if (pageSize < 1)
				pageSize = 1;
			if (page < 1)
				page = 1;
			// 设置一页数量
			cachedRowSet.setPageSize(pageSize);
			// 生成cachedRowSet,然后指定的条数开始
			cachedRowSet.populate(rs, (page - 1) * pageSize + 1);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}

		return cachedRowSet;
	}

}

```



### 实体类相关

##### 1.使用JsonMsg对象包装Json数据

```java
package cn.dmdream.vo;

public class JsonMsg {

	public final static int STATUS_SUCCESS = 200;//成功标志
	public final static int STATUS_FAIL = 400;//失败标志
	public final static int STATUS_ERROR = 500;//服务出错
	public final static int STATUS_AUTH = 100;//未授权服务
	
	private int status;//标志位,代表处理结果
	private String msg;//提示信息
	private Object data;//数据
	//Get&Set
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/*
	 * 成功方法
	 */
	public static JsonMsg makeSuccess(String msg, Object data){
		JsonMsg jsonMsg = new JsonMsg();
		jsonMsg.setStatus(STATUS_SUCCESS);
		jsonMsg.setData(data);
		jsonMsg.setMsg(msg);
		return jsonMsg;
	}
	/*
	 * 失败方法
	 */
	public static JsonMsg makeFail (String msg, Object data){
		JsonMsg jsonMsg = new JsonMsg();
		jsonMsg.setStatus(STATUS_FAIL);
		jsonMsg.setData(data);
		jsonMsg.setMsg(msg);
		return jsonMsg;
	}
	/*
	 * 系统错误
	 */
	public static JsonMsg makeError (String msg, Object data){
		JsonMsg jsonMsg = new JsonMsg();
		jsonMsg.setStatus(STATUS_ERROR);
		jsonMsg.setData(data);
		jsonMsg.setMsg(msg);
		return jsonMsg;
	}
	/*
	 * 无权限
	 */
	public static JsonMsg makeAuth (String msg, Object data){
		JsonMsg jsonMsg = new JsonMsg();
		jsonMsg.setStatus(STATUS_AUTH);
		jsonMsg.setData(data);
		jsonMsg.setMsg(msg);
		return jsonMsg;
	}
}

```

##### 2.使用PageModel封装分页数据

```java
package cn.dmdream.vo;

import java.util.List;

/*
 * 页面模型,用于封装页面的数据
 * 包含了数据对象
 * 包含了页面的基础信息 比如说 页面大小 当前页面 总页面 总记录条数
 */
public class PageModel<E> {

	//结果
	private List<E> list;
	//记录总条数
	private int totalCount;
	//当前页
	private int currPage;
	//页面大小
	private int pageSize;
	//总页数
	private int totalPage;
	public List<E> getList() {
		return list;
	}
	public void setList(List<E> list) {
		this.list = list;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getCurrPage() {
		if (currPage < 1)
			currPage = 1;
		if (currPage > totalPage)
			currPage = totalPage;
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		if (pageSize < 1)
			pageSize = 1;
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 自动包装计算PageModel
	 * @param currPage 设定默认的page首页/开始页信息
	 * @param pageSize 设定默认的pageSize信息
	 * @param totalCount 设定总记录数
	 * @param list 数据集
	 * @param pageModel 要包装的PageModel
	 */
	public static void wrapPageModel(int currPage , int pageSize,int totalCount,List list,PageModel<?> pageModel){
		//计算总页数
		int totalPage = totalCount%pageSize > 0 ? totalCount/pageSize + 1: totalCount/pageSize;
		//封装分页参数
		pageModel.setCurrPage(currPage);
		pageModel.setPageSize(pageSize);
		pageModel.setTotalCount(totalCount);
		pageModel.setTotalPage(totalPage);
		pageModel.setList(list);
	}
	@Override
	public String toString() {
		return "PageModel [list=" + list + ", totalCount=" + totalCount + ", currPage=" + currPage + ", pageSize="
				+ pageSize + ", totalPage=" + totalPage + "]";
	}
	
}

```

###### 前台参考代码

```html
总共有${pageModel.totalCount }条
<a href="studentbypage.do?currPage=1&pageSize=${pageModel.pageSize}">第一页</a>
<c:if test="${pageModel.currPage > 1 }">
    <a href="studentbypage.do?currPage=${pageModel.currPage - 1 }&pageSize=${pageModel.pageSize}">上一页</a>
</c:if>
当前在第${pageModel.currPage }页
<c:if test="${pageModel.currPage < pageModel.totalPage }">
    <a href="studentbypage.do?currPage=${pageModel.currPage + 1 }&pageSize=${pageModel.pageSize}">下一页</a>
</c:if>
<a href="studentbypage.do?currPage=${pageModel.totalPage }&pageSize=${pageModel.pageSize}">最后一页</a>

```



### Filter

##### 1.字符编码过滤器&JsonParse过滤

```java
package cn.dmdream.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CharacterEncodingFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("过滤器开始销毁");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request = (HttpServletRequest)request;
		response = (HttpServletResponse)response;
		//执行过滤
		System.out.println("将所有请求设置UTF8编码后放行");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//通过过滤器将请求继续分发
		chain.doFilter(request, response);
		//过滤完返回
		response.setContentType("application/json;charset=utf-8");
		System.out.println("已将返回文本类型设置成json");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("过滤器开始初始化");
	}
}

```

web.xml配置

```xml
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>cn.dmdream.controller.filter.CharacterEncodingFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>*.do</url-pattern>
</filter-mapping>
```





##### 2.权限过滤器

###### 管理员权限过滤器

```java
package cn.dmdream.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dmdream.entity.SnAdmin;

public class AdminAuthFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("管理员权限认证销毁");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {
			SnAdmin admin = (SnAdmin) httpRequest.getSession().getAttribute("admin");
			if(admin == null || admin.equals("")) throw new Exception();
			else chain.doFilter(httpRequest, httpResponse);
		} catch (Exception e) {
			httpRequest.getRequestDispatcher("/admin.do").forward(httpRequest, httpResponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("管理员权限认证启动");
	}

}

```





### 邮件发送

> 导入mail.jar

##### 工具类

```java
package cn.dmdream.utils;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;


public class MailUtils {

	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException, GeneralSecurityException {
		/*// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
		// 设置发送的协议
		// props.setProperty("mail.transport.protocol", "SMTP");

		// 设置发送邮件的服务器
		// props.setProperty("mail.host", "smtp.126.com");
		// props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				// 设置发送人的帐号和密码
				return new PasswordAuthentication("kuluseky@qq.com", "ntjpvjfdnsxjbbib");
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		// 设置发送者
		message.setFrom(new InternetAddress("kuluseky@qq.com"));

		// 设置发送方式与接收者
		message.setRecipient(RecipientType.TO, new InternetAddress(email));

		// 设置邮件主题
		message.setSubject("用户激活");
		// message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

		String url = "http://localhost:8080/store_v5/UserServlet?method=active&code=" + emailMsg;
		String content = "<h1>来自购物天堂的激活邮件!激活请点击以下链接!</h1><h3><a href='" + url + "'>" + url + "</a></h3>";
		// 设置邮件内容
		message.setContent(content, "text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发送
		Transport.send(message);*/

		/*
		 * 使用QQ邮箱的设置
		 */
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.qq.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");

		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);
		
		Session session = Session.getInstance(props);
        
        Message msg = new MimeMessage(session);
        msg.setSubject("星象小说的注册验证邮件");
		String url = "http://localhost:8080/ShareNovel/UserServlet?method=active&code=" + emailMsg;
		String content = "<h1>来自星象小说的激活邮件!激活请点击以下链接!</h1><h3><a href='" + url + "'>" + url + "</a></h3>";
		// 设置邮件内容
		msg.setContent(content, "text/html;charset=utf-8");
        
        msg.setFrom(new InternetAddress("kuluseky@qq.com"));
     
        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "kuluseky@qq.com", "ntjpvjfdnsxjbbib");
     
        transport.sendMessage(msg, new Address[] { new InternetAddress(email) });
        transport.close();

	}

	public static void main(String[] args) throws AddressException, MessagingException, GeneralSecurityException {
		MailUtils.sendMail("kuluseky@icloud.com", "123456789");
	}
}

```





### Redis

>  [使用Jedis操作redis](https://www.cnblogs.com/relucent/p/4203190.html) 
>
>  [Redis常用命令](<https://blog.csdn.net/ithomer/article/details/9213185>) 
>
>  [java中使用Jedis操作Redis实例](<https://blog.csdn.net/lovelichao12/article/details/75333035/>)
>
>  [Jedis存放对象和读取对象--Java序列化与反序列化](https://www.cnblogs.com/BigJunOba/p/9127414.html) 

##### 1.服务器搭建

* 服务器地址 `cloud.dmdream.cn` 端口号 6379

##### 2.导入依赖jar

* commons-pool2-2.3.jar
* jedis-2.7.0.jar

##### 3.工具类

```java
package cn.dmdream.utils;


import java.util.List;

import cn.dmdream.entity.SnCategory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
	//创建连接池
	private static JedisPoolConfig config;
	private static JedisPool pool;
	
	static{
		config=new JedisPoolConfig();
		config.setMaxTotal(30);
		config.setMaxIdle(2);
		
		
		pool=new JedisPool(config, "cloud.dmdream.cn", 6379);
	}
	
	
	//获取连接的方法
	public static Jedis getJedis(){
		Jedis jedis = pool.getResource();
		jedis.auth("123123");
		return jedis;
	}
	
	
	//释放连接
	public static void closeJedis(Jedis j){
		j.close();
	}
	
	//获取Redis分页数据
	public static <T> List<T> getRedisListByPage(int pageSize,int page , List<T> dataList){
		
		if(dataList==null) return null;
		
		int start = (page - 1) * pageSize;
		int end = page * pageSize;
		if (end > dataList.size()) end = dataList.size();
		if (start <= dataList.size()) {
			return dataList.subList(start, end);
		}
		
		return null;
	}
}

```



##### 4.常用命令

###### 4.1基础命令

```java
package cn.dmdream.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.entity.SnCategory;
import redis.clients.jedis.Jedis;

public class TestJedis {

	// 测试Jedis的使用
	public static void main(String[] args) {

		// 连接,初始化操作已经交给了工具类

		// 1.从工具类获取Jedis对象
		Jedis jedis = JedisUtils.getJedis();
		// 2.用Jedis存值--String类型的json字符串
		// String string = jedis.set("allCats", "测试目录");
		// System.out.println(string);//返回状态--OK

		// 3.用Jedis取值
		String value = jedis.get("allCats");
		System.out.println(value);
		System.out.println("------");

		// 4.查询所有的key
		// Set<String> keys = jedis.keys("*");
		// keys.forEach(System.out::println);
		// System.out.println("------");

		// 5.删除key
		// 移除给定的一个或多个key,如果key不存在,则忽略该命令.
		// jedis.del("allCats");
		// jedis.del("key1","key2","key3","key4","key5");

		// 6.使用Jedis存取对象
		ObjectMapper objectMapper = new ObjectMapper();
		// 6.1从数据库读取对象数组->转换Json->存入Redis
		// SnCategoryService categoryService = new SnCategoryServiceImpl();
		// List<SnCategory> list = categoryService.findByParentId(0);
		// try {
		// String jsonStr = objectMapper.writeValueAsString(list);
		// System.out.println(jsonStr);
		// //存入redis
		// jedis.getSet("allCats", jsonStr);
		//
		// } catch (JsonProcessingException e) {
		// e.printStackTrace();
		// }
		//6.2从Redis读取Json字符串->转换成list对象(反序列化)
		try {
			List<SnCategory> readValue = objectMapper.readValue(value,new TypeReference<List<SnCategory>>() { });
			readValue.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 7.使用完记得关闭jedis
		jedis.close();
	}

}

```



###### 4.2HashSet

```java
package cn.dmdream.utils;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.entity.SnCategory;
import cn.dmdream.service.SnCategoryService;
import cn.dmdream.service.impl.SnCategoryServiceImpl;
import redis.clients.jedis.Jedis;

public class TestJedis {

	// 测试Jedis的使用
	public static void main(String[] args) {

		// 连接,初始化操作已经交给了工具类

		// 1.从工具类获取Jedis对象
		Jedis jedis = JedisUtils.getJedis();
		ObjectMapper objectMapper = new ObjectMapper();
		// 2.Redis分页版查询
		try {
			// 先查询redis 若有,返回
			String value = jedis.hget("key1", "0");
			if (value != null) {
				System.out.println("数据是从Redis中查询的");
				List<SnCategory> readValue = objectMapper.readValue(value, new TypeReference<List<SnCategory>>() {
				});
				int page = 1;
				int pageSize = 10;
				int start = (page - 1) * pageSize;
				int end = page * pageSize;
				if (end > readValue.size())
					end = readValue.size();
				if (start < readValue.size()) {
					List<SnCategory> subList = readValue.subList(start, end);
					subList.forEach(System.out::println);
				}
			} else {
				// 若没有,从数据库中查询并赋值Redis再返回
				System.out.println("数据是从数据库查询的");
				SnCategoryService categoryService = new SnCategoryServiceImpl();
				List<SnCategory> list = categoryService.findByParentId(1);
				String jsonStr = objectMapper.writeValueAsString(list);
				// 存入redis
				jedis.hset("key1", "1", jsonStr);
				list.forEach(System.out::println);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3.使用完记得关闭jedis
		jedis.close();
	}

}

```



##### 5.使用场合

* 小说查询
* 目录查询
* 评论查询等

**注意**

1. 在对数据库进行新增/修改/删除的时候,为了确保从Redis中查询到的数据时最新的,一定要先**删除**原先存在的key

2. 在使用Redis查询的时候,先从Redis中查询是否有当前key的值,若没有,从数据库中查询然后再保存到Redis中,后续若Redis中有值,直接从Redis中查询返回

3. hashset的使用

   ```java
   //取值,若没有返回null 第一个为hash表的key,第二个为表中的第一个域
   String value = jedis.hget("key1", "0");
   //存值 第一个为hash表的key,第二个为表中的第一个域,第三个为该域的值
   jedis.hset("key1", "1", jsonStr);
   ```

   



##### 6.前台用户界面查询分类目录的Redis参考代码

```java
//findAllCats
public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    //在redis中获取全部分类信息
    Jedis jedis=JedisUtils.getJedis();
    String jsonStr=jedis.get("allCats");
    if(null==jsonStr||"".equals(jsonStr)){
        //调用业务层获取全部分类
        CategoryService CategoryService=new CategoryServiceImp();
        List<Category> list = CategoryService.getAllCats();
        //将全部分类转换为JSON格式的数据
        jsonStr=JSONArray.fromObject(list).toString();
        System.out.println(jsonStr);
        //将获取到的JSON格式的数据存入redis
        jedis.set("allCats", jsonStr);
        System.out.println("redis缓存中没有数据");
        //将全部分类信息响应到客户端
        //告诉浏览器本次响应的数据是JSON格式的字符串
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(jsonStr);			
    }else{
        System.out.println("redis缓存中有数据");

        //将全部分类信息响应到客户端
        //告诉浏览器本次响应的数据是JSON格式的字符串
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(jsonStr);
    }

    JedisUtils.closeJedis(jedis);

    return null;
}
```





### SpringDataSolr

>  [SpringBoot整合Spring Data Solr](<http://www.mamicode.com/info-detail-2220093.html>) 
>
>  [solr(四) : springboot 整合 solr](https://www.cnblogs.com/elvinle/p/8149256.html) 
>
>  [Spring-Boot 集成Solr客户端](<https://www.jianshu.com/p/e21fe5f3bd8c>) 

##### 1.Solr服务器的搭建

* solr搜索程序安装

* solr索引库命名->novel

* ik分词器安装

  * 创建索引域类型--此类型必须使用ik分词器（schema.xml）

    ```shell
    #定位
    vim solr/item/conf/schema.xml
    #到底部添加
    <!-- 以下都为学习项目的域配置 -->
    
    <!--注意field中的name必须和fieldType中的name保持一致-->
    <field name="username_ik" type="text_ik" indexed="true" stored="true" /><!--测试域-->
    <fieldType name="text_ik" class="solr.TextField">
    	<analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
    </fieldType>
    ```

* solr服务访问网址 `http://cloud.dmdream.cn:8088/solr/` 



##### 2.solr域字段配置

> 索引库中没有数据，不能使用索引库进行搜索：

1. 问题1：索引库怎么导入数据？
   1）dataimport（程序员测试使用）
   2）程序实现索引数据导入（√）
2. 问题2：索引库需要导入什么索引数据？是否需要把所有数据都放入索引库？
   1）索引库需要的是参数搜索的数据.一那么数据参数搜索一>小说数据-—->因此索引库需要的小说数据.
   2）只需要导入小说数据即可，不需要导入所有数据，其他数据不参与搜索.
3. 问题3：索引库来自那？
   站内搜索，数据来自于本地数据库。
4. 问题4：需要把数据库中那些表字段数据导入索引库？
   字段：
   novel_id、novel_title、novel_author.....…
5. 问题5：索引库必须做什么配置？
   索引库必须配置索引域字段，这些**域字段必须和数据库需要导入字段一一对应**。

- 域相当于数据库的表字段，用户存放数据，因此用户根据业务需要去定义相关的 Field
  （域），一般来说，每一种对应着一种数据，用户对同一种数据进行相同的操作。

  > 域的常用属性：

  - name：指定域的名称
  - type：指定域的类型
  - indexed：是否索引
  - stored：是否存储
  - required：是否必须
  - multiValued：是否多值

- 配置普通域

  ```xml
  <!--域类型-->
  <fieldType name="text_ik" class="solr.TextField">
  	<analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
  </fieldType>
  <!--普通域-->
  <field name="novel_id" type="long" indexed="true" stored="true"/>
  <field name="novel_title" type="text_ik" indexed="true" stored="true"/>
  <field name="novel_Author" type="text_ik" indexed="true" stored="true"/>
  <field name="novel_category" type="string" indexed="true" stored="true" />
  <field name="novel_is_end" type="long" indexed="true" stored="true" />
  <field name="novel_summary" type="text_ik" indexed="true" stored="true" />
  <field name="novel_cover" type="string" indexed="false" stored="true" />
  ```

- 配置复制域

  > 复制域的作用在于将某一个 Field 中的数据复制到另一个域中

  ```xml
  <!--复制域-->
  <field name="novel_keywords" type="text_ik" indexed="true" stored="false" multiValued="true"/>
  <copyField source="novel_title" dest="novel_keywords"/>
  <copyField source="novel_Author" dest="novel_keywords"/>
  <copyField source="novel_summary" dest="novel_keywords"/>
  ```

- 重启Tomcat-solr

- BUG

  - **注意**

    使用了注解@Field("id"),在schema.xml中的默认域类型是string的,因此一直报错!!!!

  - 修改如下

    ```xml
    <field name="id" type="int" indexed="true" stored="true" required="true" multiValued="false" />
    ```

  - PS: 以上修改无效 -> 正确的方式 -> 将SnNovel.java实体类中的novelId改成String类型

- solrTemplate设置多高亮字段

  >  [参考](<https://bbs.csdn.net/topics/392263532>) 

  ```java
  //创建高亮对象,添加高亮操作
  HighlightOptions highlightOptions = new HighlightOptions();
  highlightOptions.addField("novel_title");
  highlightOptions.addField("novel_Author");
  highlightOptions.addField("novel_summary");
  highlightOptions.setSimplePrefix("<font color='red'>");
  highlightOptions.setSimplePostfix("</font>");
  //启用多字段高亮
  highlightOptions.addHighlightParameter("hl.preserveMulti", "true");
  ```

  

##### 3.使用springboot搭建服务

* 配置文件application.properties

  ```properties
  #Tomcat地址
  server.port=80
  #
  spring.thymeleaf.cache=false        
  spring.devtools.restart.enabled=true   
  spring.devtools.restart.additional-paths=src/main/java  
  # 数据库配置
  spring.datasource.url=jdbc:mysql://localhost:3306/sharenovel?useUnicode=true&characterEncoding=utf8&useSSL=false
  spring.datasource.driver-class-name=com.mysql.jdbc.Driver
  spring.datasource.username=root
  spring.datasource.password=root
  # mybatis 配置
  mybatis.type-aliases-package=cn.dmdream.entity
  mybatis.mapper-locations=classpath:cn/dmdream/dao/*.xml
  # solr配置
  spring.data.solr.host=http://cloud.dmdream.cn:8088/solr
  spring.data.solr.core=novel
  ```

* service关键代码

  ```java
  @Service
  public class SnNovelServiceImpl implements SnNovelService {
  
  	@Autowired
  	private SnNovelMapper novelMapper;
  	@Autowired
  	private SolrTemplate solrTemplate;
  	
  	
  	public List<SnNovel> findAll() {
  		return novelMapper.findAll();
  	}
  
  
  	@Override
  	public List<SnNovel> findByCheck(Integer checkId) {
  		return novelMapper.findByCheck(checkId);
  	}
  
  
  	@Override
  	public void insertSolrAll() {
  		
  		List<SnNovel> list = novelMapper.findAll();
  		solrTemplate.saveBeans("novel", list);
  		solrTemplate.commit("novel");
  	}
  
  
  	@Override
  	public void deleteSolrAll() {
  	    // 删除所有
  	    Query query = new SimpleQuery("*:*");
  	    solrTemplate.delete("novel", query);
  	    // 根据id删除索引
  	    //solrTemplate.deleteById(100000000000L + "");
  	    solrTemplate.commit("novel");
  		
  	}
  
  
  	/**
  	 * 分页查找所有,并且高亮显示
  	 */
  	public PageModel<SnNovel> findByKeywordByPage(String keyword, int pageSize, int page) {
  
  		if(keyword == null || keyword.equals("")) return null;
  	    // 创建solrQuery对象,封装条件
  	    SimpleHighlightQuery query = new SimpleHighlightQuery();
  	    //条件查询
  	    Criteria criteria = new Criteria("novel_keywords").is(keyword);
  	    //将条件添加到查询对象
  	    query.addCriteria(criteria);
  	    
  	    //创建高亮对象,添加高亮操作
  	    HighlightOptions highlightOptions = new HighlightOptions();
  	    highlightOptions.addField("novel_title");
  	    highlightOptions.addField("novel_Author");
  	    highlightOptions.addField("novel_summary");
  	    highlightOptions.setSimplePrefix("<font color='red'>");
  	    highlightOptions.setSimplePostfix("</font>");
  	    //启用多字段高亮
  	    highlightOptions.addHighlightParameter("hl.preserveMulti", "true");
  	    
  	    //设置高亮查询
  	    query.setHighlightOptions(highlightOptions);
  	    
  	    // 设置分页条件
  	    // 设置分页查询起始位置
  	    query.setOffset((long) ((page-1) * pageSize));
  	    // 设置分页查询每页显示条数
  	    query.setRows(pageSize);
  
  	    // 执行查询
  	   HighlightPage<SnNovel> highlightPage = solrTemplate.queryForHighlightPage("novel", query, SnNovel.class);
  
  	    // 获取高亮分页记录
  	    List<SnNovel> list = highlightPage.getContent();
  	    
  	    // 获取总记录数
  	    long totalElements = highlightPage.getTotalElements();
  	    
  	    //循环搜索小说集合,获取高亮
  	    for (SnNovel novel : list) {
  	    	//获取高亮
  			List<Highlight> highlights = highlightPage.getHighlights(novel);
  			
  			//判断高亮是否存在
  			if(highlights != null  && highlights.size() > 0){
  				//多个域字段,开始遍历
  				for (Highlight highlight : highlights) {
  					//获取高亮的域字段名字
  					String fieldName = highlight.getField().getName();
  					//获取高亮值[{}]
  					List<String> snipplets = highlight.getSnipplets();
  					//设置高亮字段
  					if(fieldName.equals("novel_title")){
  						novel.setNovelTitle(snipplets.get(0));
  					}else if(fieldName.equals("novel_Author")){
  						novel.setNovelAuthor(snipplets.get(0));
  					}else if(fieldName.equals("novel_summary")){
  						novel.setNovelSummary(snipplets.get(0));
  					}
  				}
  			}
  		}
  		
  	    PageModel<SnNovel> pageModel = new PageModel<SnNovel>();
  	    PageModel.wrapPageModel(page, pageSize, (int) totalElements, highlightPage.getContent(), pageModel);
  	    
  		return pageModel;
  		
  	}
  	
  }
  
  ```




##### 5.Linux部署&请求列表

> <https://blog.csdn.net/qq_22638399/article/details/81506448>

* 端口为8880

* 请求列表

  |                           请求地址                           |                             说明                             |
  | :----------------------------------------------------------: | :----------------------------------------------------------: |
  |             <http://cloud.dmdream.cn:8880/hello>             |                         连接测试网址                         |
  |            <http://cloud.dmdream.cn:8880/findAll>            |                       从数据库查询所有                       |
  |     <http://cloud.dmdream.cn:8880/findByCheck/{checkId}>     |              从数据库根据状态查询,checkId为0~2               |
  |         <http://cloud.dmdream.cn:8880/insertSolrAll>         |                  将所有数据库数据插入索引库                  |
  | <http://cloud.dmdream.cn:8880/insertSolrAllByCheck/{checkId}> |                  将不同状态的数据插入索引库                  |
  |         <http://cloud.dmdream.cn:8880/deleteSolrAll>         |                          清空索引库                          |
  |      <http://cloud.dmdream.cn:8880/findByKeywordByPage>      | String keyword,String pageSize,String page<br />三个参数：关键字、页面显示数、当前页。返回搜索结果的Json对象 |

  

##### 6.修改Linux多JDK版本

> <https://www.cnblogs.com/lukefan/archive/2019/02/19/10400427.html>

* 强制结束进程
  * 查询 ps -aux | grep XXX  或 ps all | grep XXX
  * 结束kill pid 
  * 强制结束kill -9 pid



---

### 问题

##### 1.servlet接收ajax传递的数组

> <https://blog.csdn.net/QQ1035661353/article/details/72955888>

```js
var ids = ["1", "2", "3"];
$.ajax({
        url: url,
        dataType: "json",
        traditional: true,
        type: "POST",
        data: {
            ids: ids
        },
})
```

重点在于：

```java
traditional: true,
//它可以阻止jQuery对数组的深度序列化，在servlet下使用
String ids[] = request.getParameterValues("ids")
```


##### 2.Jq获取checkbox的选中值

```html
<html>
    <head>
        <meta charset="gbk">
        <!-- 引入JQuery -->
        <script src="jquery-1.3.1.js" type="text/javascript"></script>
    </head>

    <body>
        <input type="checkbox" value="橘子" name="check">橘子1</input>
        <input type="checkbox" value="香蕉" name="check">香蕉1</input>
        <input type="checkbox" value="西瓜" name="check">西瓜1</input>
        <input type="checkbox" value="芒果" name="check">芒果1</input>
        <input type="checkbox" value="葡萄" name="check">葡萄1</input>
        
        <input type="button" value="方法1" id="b1">
        <input type="button" value="方法2" id="b2">


    </body>
    
    <script>
        //方法1
        $("#b1").click(function(){
            //$('input:checkbox:checked') 等同于 $('input[type=checkbox]:checked')
            //意思是选择被选中的checkbox
            $.each($('input:checkbox:checked'),function(){
                window.alert("你选了："+
                    $('input[type=checkbox]:checked').length+"个，其中有："+$(this).val());
            });
        });
        
        //方法2
        $("#b2").click(function(){
            $.each($('input:checkbox'),function(){
                if(this.checked){
                    window.alert("你选了："+
                        $('input[type=checkbox]:checked').length+"个，其中有："+$(this).val());
                }
            });
        });
    </script>
</html>
```



##### 3.jqCheckBox全选反选

使用.prop("checked",true)



##### 4.ajax图片上传

###### yoko版本

```html
<!-- 封面上传 -->
<div class="form-group has-success form-download">
    <label
        class="control-label col-sm-offset-3 col-md-offset-3 col-lg-offset-3"
        for="novelCover">选择图片上传</label>
    <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3" style="height:auto;"
        id="picUpload">  <!--这个div是插件的页面展示区域-->
        <!-- 图片上传完后通过ajax将地址绑定到hidden域 -->
        <input type="hidden" name="novelCover" id="novelCoverId">
    </div>
    <!-- 封面上传js -->
    <script type="text/javascript">
        var dragImgUpload = new DragImgUpload("#picUpload", {//初始化
            callback : function(files) {
                //回调函数，可以传递给后台等等
                var file = files[0];//得到文件流对象
                console.log(file.name);
                var form = new FormData();
                form.append("file", file);//添加到表单对象
                if(/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(file.name)){//判断格式
                    showStikyGritter('消息',"正在上传图片······");
                    var formPromiseObj = getPostAjaxPromiseForForm("${pageContext.request.contextPath}/adminNovel.do?method=ajaxFileUpload",form);//通过ajax异步请求(这里是我封装的函数,具体查看'前端相关'的第5点内容)
                    formPromiseObj.then(function(res){//请求成功的回调
                        removeAllGritters();//清除所有轻提示
                        setTimeout(function() {showGritter('消息',"图片上传成功！");}, 500);
                        console.log(res);
                        $("#novelCoverId").val(res.data)//将返回的图片地址赋值给表单的val
                    });
                }else{
                    showGritter('错误',"不支持的图片格式!支持以下格式:gif、jpg、jpeg、png");
                    return false;
                }
            //格式判断完毕
            }
            //回调函数完毕
        })
    </script>
</div>
<!-- 下载地址div/ -->
```

其他版本

```js
 //给你举个例子
var dragImgUpload = new DragImgUpload("#_area",{
      callback:function (files) {
          //回调函数，可以传递给后台等等
   var file = files[0];
   var formData = new FormData();
   formData.append("importFilePath",file );
   formData.append("folderId",file.name);
   formData.append("softType",file..type);
   if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(file.name))
   {
       toastr["error"]($.i18n.prop("pictureType"))
       return false;
   }else{
       $.ajax({
           url: g_url_updateUserImg,
           type:'POST',
           contentType: false,
           processData: false,
           data: formData,
           success:function(data,textstatus){
               if(data.success==true){
                    //回台返回上传成功操作
               }else{
                   toastr["error"]($.i18n.prop("modifiedFailure"));
               }
           }
       });
   }
      }
})
```



##### 5.JQ事件操作

*  `$(selector).trigger(event, [param1,param2,…])`可手动触发事件，如click、change等事件

  ```html
  <select>
      <option value="1">1</option>
      <option value="2">2</option>
  </select>
  
  $('select').val('2').trigger('change'); // 手动改变select的值，并触发相应事件
  ```

  

##### 6.JQ元素节点操作

>  [jQuery操作元素节点的方法（创建、选择、插入节点）](https://www.cnblogs.com/youbiao/p/9060639.html) 

* 删除所有子节点 `$(selector).empty()` 

  > jQuery中的empty()方法：些方法可以清空/删除指定元素下的所以子节点或是内容

  *  [jQuery中删除节点方法remove()、detach()、empty()分析](https://www.cnblogs.com/always-chang/p/5295142.html) 
    - **remove()**：删除所有后代节点及自身，与该节点绑定的事件删除后也将解除绑定
    - **detach()**：删除所有后代节点及自身，但与该节点绑定的事件删除后依然保留

* 创建节点

  ```js
  注意：Jquery创建元素节点、属性节点、文本节点都使用$(html)
  
  1.创建元素节点：$("<li></li>");
  2.创建属性节点：$("<li id='test'></li>");
  3.创建文本节点：$("hello world !");
  4.下面是创建三者的结合：$("<li id='test'>hello world !</li>");
  
   解释：4是创建一个id属性为test，文本节点为hello world !的li节
  ```

* 获得节点

  ```js
  主要是jQuery的选择器：下面说下最基础的几个
  
        选择器           格式              举例
      1.id选择器     $("#id的属性值")     $("#name");
      2.class选择器  $(".class的属性值")  $(".name")
      3.标签选择器    $("html标签")       $("p")
      4.*选择器      $("*")             $("*")
      5.群组选择器    $("选择器1,选择器二,...")  $("p,#name")
  ```

* 插入节点

  ```bat
  内部插入（当做子节点插入）：
      append(content|fn)   向每个匹配的元素内部末尾追加内容。
      appendTo(content)    把所有匹配的元素追加到另一个指定的元素元素集合末尾。
      prepend(content|fn)  向每个匹配的元素内部头部内容。
      prependTo(content)   把所有匹配的元素追加到另一个指定的元素元素集合头部
  外部插入（当做兄弟节点插入）：
      after(content|fn)    向每个匹配元素的后面添加内容
      before(content|fn)   向每个匹配元素的前面添加内容
      insertAfter(content) 把所有匹配的元素插入到另一个、指定的元素元素集合的后
      面。
      insertBefore(content) 把所有匹配的元素插入到另一个、指定的元素元素集合的
      前面。
  ```



##### 7.JQ属性样式操作

* [Jquery 获取Checkbox值，prop 和 attr 函数区别](https://www.cnblogs.com/68681395/p/3181165.html) 、 [jquery中attr和prop的区别](https://www.cnblogs.com/Showshare/p/different-between-attr-and-prop.html) 

  **例子**

  ```html
  <input id="chk1" type="checkbox" />是否可见
  <input id="chk2" type="checkbox" checked="checked" />是否可见
  ```

  像checkbox，radio和select这样的元素，选中属性对应“checked”和“selected”，这些也属于固有属性，因此需要使用prop方法去操作才能获得正确的结果

  ```js
  $("#chk1").prop("checked") == false
  $("#chk2").prop("checked") == true
  //如果上面使用attr方法，则会出现：
  $("#chk1").attr("checked") == undefined
  $("#chk2").attr("checked") == "checked"
  ```

*  设置css

   >  [使用jquery操作元素的css样式（获取、修改等等）](https://www.cnblogs.com/liangliangjiang/p/6185449.html) 

   ```js
   $("div").css("color") 设置color属性值. $(element).css(style)
   
   //设置单个样式
   
   $("div").css("color","red")
   
   //设置多个样式
   
   $("div").css({fontSize:"30px",color:"red"})
   
   $("div").css("height","30px")==$("div").height("30px")
   
   $("div").css("width","30px")==$("div").height("30px")
   ```

   



##### 8.富文本不显示问题

* 尝试使用id选择器初始化

* 一定要给textarea设置宽和高属性

* 为避免在模态框中使用时炸开样式,应限制拖动改变的属性

  [resizeType](<http://kindeditor.net/view.php?bbsid=5&postid=4108>)

  2或1或0，2时可以拖动改变宽度和高度，1时只能改变高度，0时不能拖动。

  - 数据类型: Int
  - 默认值: 2

  **modal窗口**

  ```html
  <!-- 小说简介Modal-富文本 -->
  <div class="modal fade" id="modalNovelSummary" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal"
                      aria-hidden="true">&times;</button>
                  <h4 class="modal-title" id="modalNovelSummaryTitle">小说简介编辑</h4>
              </div>
              <div class="modal-body" style="padding:5px;" id="modalNovelSummaryBody">
                  <textarea id="editor_id" name="content" style="width:100%;height:400px;">
                      &lt;strong&gt;请在这里添加你的小说简介&lt;/strong&gt;
                  </textarea>
              </div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                  <input type="button" id="modalNovelSummaryBtn" class="btn btn-primary"
                      value="确认" />
              </div>
          </div>
      </div>
  </div>
  ```

  **在页面最底部添加初始化**

  ```js
  <!-- 富文本正文区域-->
  <script type="text/javascript">
      var editor;
      KindEditor.ready(function(K) {
          editor = K.create('#editor_id', {
              allowFileManager: true,
              resizeType: 1
          });
      });
  </script>
  <!-- 富文本正文区域 /-->
  ```

  

##### 9.JSTL常见问题

* 判断空值

  ```jsp
  <c:if test="${empty str}">  str为空</c:if>
  <c:if test="${not empty str}">  str不为空</c:if>
  ```

*  [jsp 中 foreach 获取元素下标](https://www.cnblogs.com/lishuaiqi/p/9765095.html) 

  ```jsp
  <c:forEach items="${signBusList}" var="sign" varStatus="xh"> 
         ${xh.count}序号从1开始 
        ${xh.index}序号从0开始 
        ${xh.last}最后一个序号 
       ${xh.first}第一个序号 
  </cforEach>
  ```

  



##### 10.jsp的动态导入问题

* 动态include的方式

  ```jsp
  <jsp:include page="include.jsp" flush="true"/>
  ```

* `<@ include file="">`方式

  ```jsp
  <%@ include file="/footer.jsp"%>
  ```



##### 11.去除页面滚动条

> <https://blog.csdn.net/qq_36082908/article/details/78925775>

* 方式一

  ```html
  <style>
     html {
     -ms-overflow-style:none;
     overflow:-moz-scrollbars-none;
     }
     html::-webkit-scrollbar{width:0px}
  
  </style>
  ```

* 方式二

  ```css
  #body{}
  -webkit-scrollbar{
     width:0px;
     height:0px;
  }
  ```

  

##### 12.使用promise顺序处理多个异步任务

> <https://www.cnblogs.com/lvdabao/p/es6-promise-1.html>

* 返回promise对象举例

  ```js
  var p1 = new Promise(function(res,rej){
      setTimeout(function(){
          console.log(1);
          res("1finish");
      });
  })
  var p2 = new Promise(function(res,rej){
      setTimeout(function(){
          console.log(2);
          res("2finish");
      });
  })
  var p3 = new Promise(function(res,rej){
      setTimeout(function(){
          console.log(3);
          res("3finish");
      });
  })
  //顺序执行
  p1.then(function(res){
      console.log(res)
      return p2;
  }).then(function(res){
      console.log(res)
      return p3;
  })
  ```

* 也可以返回数据



##### 13.多行文本溢出时省略号表示

>  [多行文本溢出显示省略号(…)](<https://www.jianshu.com/p/a488b6f255e5>) 
>
>  [CSS实现单行、多行文本溢出显示省略号（…）](https://www.cnblogs.com/gopark/p/8875655.html) 



**举例**

```html
<!-- WebKit浏览器或移动端的页面
在WebKit浏览器或移动端（绝大部分是WebKit内核的浏览器）的页面实现比较简单，可以直接使用WebKit的CSS扩展属性(WebKit是私有属性)-webkit-line-clamp ；注意：这是一个 不规范的属性（unsupported WebKit property），它没有出现在 CSS 规范草案中。
-webkit-line-clamp用来限制在一个块元素显示的文本的行数。 为了实现该效果，它需要组合其他的WebKit属性。
常见结合属性：
display: -webkit-box; 必须结合的属性 ，将对象作为弹性伸缩盒子模型显示 。
-webkit-box-orient 必须结合的属性 ，设置或检索伸缩盒对象的子元素的排列方式 。
text-overflow: ellipsis;，可以用来多行文本的情况下，用省略号“…”隐藏超出范围的文本 。

overflow : hidden;
text-overflow: ellipsis;
display: -webkit-box;
-webkit-line-clamp: 2;
-webkit-box-orient: vertical;
这个属性比较合适WebKit浏览器或移动端（绝大部分是WebKit内核的）浏览器。


DEMO解释:

em{
            width:200px;——宽度必须加
            overflow: hidden;——溢出隐藏
            text-overflow: ellipsis;——可以用来多行文本的情况下，用省略号“…”隐藏超出范围的文本
            white-space: nowrap;——不换行
            display: block;
        }
        p{
            width:200px;——宽度必须加
            overflow : hidden;——将多余的隐藏
            text-overflow: ellipsis;——可以用来多行文本的情况下，用省略号“…”隐藏超出范围的文本
            display: -webkit-box;——必须结合的属性 ，将对象作为弹性伸缩盒子模型显示 。
            -webkit-line-clamp: 2;——显示的行数
            -webkit-box-orient: vertical;——必须结合的属性 ，设置或检索伸缩盒对象的子元素的排列方式
            word-break:break-all;——如果一行中带有数字、英文、文字须加此行,起到强制换行的作用

        }

具体看如下DEMO： -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>单行，多行文本溢出显示省略号</title>
    <style>
        em{
            width:200px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            display: block;
        }
        p{
            width:200px;
            overflow : hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            word-break:break-all;

        }
    </style>
</head>
<body>
<!-- 单行文本溢出显示省略号 -->
<em>多行文本溢出显示省略号多行文本溢出显示省略号多行文本溢出显示省略号多行文本溢出显示省略号多行文本溢出显示省略号多行文本溢出显示省略号多行文本溢出显示省略号</em>
<em>eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee</em>


<!-- 多行文本溢出显示省略号 -->
<p>多行文本溢出显示省略号多行文本溢出显示省略号多行文本溢出显示省略号多行文本溢出显示省略号多行文本溢出显示省略号多行文本溢出显示省略号多行文本溢出显示省略号</p>
<p>eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee</p>
</body>
</html>
```



##### 14.a标签的几种跳转方式

```html
<a href="http://www.baidu.com" target="_Blank">百度</a>
_Blank是新窗口
_Self是自身
_Parent是父窗口
_Top是顶层窗口
当然也可以是自己定义的一个frame 的名字
比如
<a href="http://www.baidu.com" target="frame1">百度</a>
```



##### 15.页面设置标签滚动

```js
function goTop() {
$('html, body').animate({scrollTop:0}, 'slow'); 
}
function goDiv(div) {
var a = $("#"+div).offset().top;
$("html,body").animate({scrollTop:a}, 'slow'); 
}
function goBottom() {
window.scrollTo(0, document.documentElement.scrollHeight-document.documentElement.clientHeight); 
}
```

