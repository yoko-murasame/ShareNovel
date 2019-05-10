package cn.dmdream.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnAdmin;
import cn.dmdream.service.SnAdminService;
import cn.dmdream.service.impl.SnAdminServiceImpl;
import cn.dmdream.vo.JsonMsg;

public class AdminServlet extends BaseServlet {

	private SnAdminService adminService = new SnAdminServiceImpl();
	private ObjectMapper objectMapper = new ObjectMapper();

	//默认的跳转方法
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "/admin/login.jsp";
	}

	// 管理员登录
	public String adminLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		SnAdmin adminFind = adminService.findByUsername(username);
		JsonMsg jsonMsg = null;
		if (adminFind == null) {
			jsonMsg = JsonMsg.makeFail("账号不存在!", null);
		} else if (adminFind.getAdminPassword().equals(password)) {
			adminFind.setAdminPassword(null);// 清空密码
			jsonMsg = JsonMsg.makeSuccess("登录成功!", adminFind);
			// 放入session
			HttpSession session = req.getSession();
			session.setAttribute("admin", adminFind);
		} else {
			jsonMsg = JsonMsg.makeFail("密码错误!", null);
		}
		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}

	//管理员注销
	public String adminLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 删除session
		HttpSession session = req.getSession();
		session.removeAttribute("admin");
		return "/admin/login.jsp";
	}
}
