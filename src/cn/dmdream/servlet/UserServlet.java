package cn.dmdream.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.service.SnUserService;
import cn.dmdream.service.impl.SnUserServiceImpl;

public class UserServlet extends BaseServlet{
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		SnUserService sus=new SnUserServiceImpl();
		return null;
	}
	
}
