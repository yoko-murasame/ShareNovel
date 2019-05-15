package cn.dmdream.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dmdream.controller.base.BaseServlet;

public class UserSearchServlet extends BaseServlet {


	//首页搜索按钮 跳转到搜索结果页面
	public String toSearchPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String keyword = req.getParameter("keyword");
		if(keyword == null || keyword.equals("")){
			resp.sendRedirect("/ShareNovel");
		}else{
			req.setAttribute("keyword", keyword);
			return "/search_result.jsp";
		}
		return null;
	}

	
}
