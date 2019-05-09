package cn.dmdream.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.service.SnChapterService;
import cn.dmdream.service.impl.SnChapterServiceImpl;

public class ChapterServlet extends BaseServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "/login.jsp";
	}

	public String readOnline(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SnChapterService cs=new SnChapterServiceImpl();
		String cid=req.getParameter("cid");
		if(cid!=null) {
			SnChapter chapter = cs.findById(Integer.parseInt(cid));
			req.setAttribute("chapter", chapter);
			return "/readonline.jsp";
			
		}else {
			return "/mainpage.jsp";
			
		}
	}
}
