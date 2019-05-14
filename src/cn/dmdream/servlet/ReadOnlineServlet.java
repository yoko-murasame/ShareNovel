package cn.dmdream.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.service.SnChapterService;
import cn.dmdream.service.SnNovelService;
import cn.dmdream.service.impl.SnChapterServiceImpl;
import cn.dmdream.service.impl.SnNovelServiceImpl;

public class ReadOnlineServlet extends BaseServlet{
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
		//String nid=req.getParameter("nid");
		SnNovelService novelservice=new SnNovelServiceImpl();
		if(cid!=null) {
			//int nid1=Integer.parseInt(nid);
			int c1=Integer.parseInt(cid);
			if(c1<0) {
				return "/mainpage.jsp";
			}
			SnChapter chapter = cs.findById(c1);
			//SnNovel novel = novelservice.findById(chapter.getSnNovel().getNovelId());
			int totalnum = cs.findNovelChapterTotalCount(chapter.getSnNovel());
			req.setAttribute("novel", chapter.getSnNovel());
			req.setAttribute("chapter",chapter);
			req.setAttribute("totalnum", totalnum);
			return "/readonline.jsp";
			
		}else {
			return "/mainpage.jsp";
			
		}
	}
}
