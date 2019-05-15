package cn.dmdream.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.service.SnChapterService;
import cn.dmdream.service.SnNovelService;
import cn.dmdream.service.impl.SnChapterServiceImpl;
import cn.dmdream.service.impl.SnNovelServiceImpl;

public class UserNovelServlet extends BaseServlet {

	private SnNovelService novelService = new SnNovelServiceImpl();
	private SnChapterService chapterService = new SnChapterServiceImpl();
	
	//根据主键查询跳转小说详情页
	public String toNovelDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			//获取novel id查询novel 详情
			String id = req.getParameter("nid");
			int totalnum = 0;
			SnNovel novel = null;
			if ("".equals(id) || id == null) {
				return "/mainpage.do?method=toIndex";
			} else {
				int nid = Integer.parseInt(id);
				novel = novelService.findById(nid);
				totalnum = chapterService.findNovelChapterTotalCount(novel);
				//放入req
				req.setAttribute("novel", novel);
				req.setAttribute("totalnum", totalnum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/novelinfo.jsp";
	}

	
	
}
