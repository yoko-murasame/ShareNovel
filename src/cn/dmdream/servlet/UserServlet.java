package cn.dmdream.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnUserService;
import cn.dmdream.service.impl.SnUserServiceImpl;
import cn.dmdream.utils.MailUtils;
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
		// 查询重复
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

		JsonMsg jsonMsg = null;
		
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
		snUser.setUserEmailActive(0);
		snUser.setUserPhoneActive(0);
		//测试随机校验码
		UUID uuid = UUID.randomUUID();
		String avticeCode = uuid.toString().replaceAll("-", "");
		snUser.setUserCode(avticeCode);
		System.out.println(snUser);
		boolean isok = snUserService.save(snUser);
		//boolean isok = true;
		String surl="fail.jsp";
		if(isok){
			String url = "novel.dmdream.cn:8070/user.do?method=activeCode"
					+ "&username=" + username + "&activeCode=" + avticeCode;
			//发送邮件
			MailUtils.AuthUserEmail(email, url);
			//转发提示页面
			surl="/userEmailWatingPage.html";
			jsonMsg = JsonMsg.makeSuccess("注册成功!", surl);
		}else{
			surl = "/userEmailFailPage.html";
			jsonMsg = JsonMsg.makeFail("注册失败!", surl);
		}
		
		// 序列化jsonMsg
		ObjectMapper objectMapper = new ObjectMapper();
		String writeValueAsString = objectMapper.writeValueAsString(jsonMsg);
		response.getWriter().write(writeValueAsString);
		return null;
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

	/*
	 * 用户注册邮件激活
	 */
	public String activeCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String activeCode = req.getParameter("activeCode");
		String username = req.getParameter("username");
		SnUserService snUserService = new SnUserServiceImpl();
		try {
			SnUser toActiveUser = snUserService.findByUsername(username);
			if(toActiveUser.getUserCode().equals(activeCode)){
				toActiveUser.setUserCode(null);
				toActiveUser.setUserEmailActive(1);
				//更新用户信息
				boolean isok = snUserService.update(toActiveUser);
				if(isok){
					//跳转激活成功页面
					return "/userEmailSuccessPage.html";
				}else{
					throw new Exception("激活失败,用户信息更新失败,可能是数据库连接问题");
				}
			}else{
				throw new Exception("激活失败,激活码不匹配,请联系管理员");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//跳转激活失败页面
			return "/userEmailFailPage.html";
		}
		
	}

	
}
