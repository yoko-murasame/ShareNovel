package cn.dmdream.servlet;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.PasswordAuthentication;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnUserService;
import cn.dmdream.service.impl.SnUserServiceImpl;
import cn.dmdream.vo.JsonMsg;

public class UserServlet extends BaseServlet {

	// 用户注册方法
	public String userCheckName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// 读取参数
		String username = request.getParameter("username");

		// 实例化SnUserService
		SnUserService snUserService = new SnUserServiceImpl();
		// 执行注册（插入）
		boolean isok = snUserService.countByUsername(username);
		System.out.println(isok);
		JsonMsg jsonMsg = null;
		if (isok) {
			jsonMsg = JsonMsg.makeSuccess("用户名可用", null);
		} else {
			jsonMsg = JsonMsg.makeFail("该用户名已被注册!", null);
		}
		// 序列化jsonMsg
		ObjectMapper objectMapper = new ObjectMapper();
		String writeValueAsString = objectMapper.writeValueAsString(jsonMsg);
		out.println(writeValueAsString);
		out.flush();
		out.close();

		return null;
	}

	public String userSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		SnUserService snUserService = new SnUserServiceImpl();
		SnUser snUser = new SnUser();
		snUser.setUserUsername(username);
		snUser.setUserPassword(password);
		snUser.setUserNickname(nickname);
		snUser.setUserEmail(email);
		snUser.setUserPhone(phone);
		boolean isok = snUserService.save(snUser);
		
		String surl="fail.jsp";
		if(isok){
			
			//发送邮件
			surl="mainpage.jsp";
		}
		return surl;
	}
	public String userLogin(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String name=request.getParameter("name");
		String pwd=request.getParameter("password");
		System.out.println("请求登录 帐号:"+name+"密码:"+pwd);
		SnUserService ss=new SnUserServiceImpl();
		SnUser user = ss.login(name, pwd);
		if(user!=null) {
			request.getSession().setAttribute("user", user);
			JsonMsg msg = JsonMsg.makeSuccess("1",null);
			ObjectMapper mapper=new ObjectMapper();
			String jsonstr = mapper.writeValueAsString(msg);
			response.getWriter().print(jsonstr);
		}else {
			JsonMsg msg = JsonMsg.makeFail("用户名或密码错误", null);
			ObjectMapper mapper=new ObjectMapper();
			String jsonstr = mapper.writeValueAsString(msg);
			response.getWriter().print(jsonstr);
		}
		return null;
	}

}
