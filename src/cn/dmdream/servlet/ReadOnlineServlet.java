package cn.dmdream.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnCollection;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnChapterService;
import cn.dmdream.service.SnCollectionService;
import cn.dmdream.service.SnNovelService;
import cn.dmdream.service.impl.SnChapterServiceImpl;
import cn.dmdream.service.impl.SnCollectionServiceImpl;
import cn.dmdream.service.impl.SnNovelServiceImpl;

public class ReadOnlineServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "/login.jsp";
	}

	public String readOnline(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SnChapterService cs = new SnChapterServiceImpl();
		String cid = req.getParameter("cid");
		// String nid=req.getParameter("nid");
		if (cid != null) {
			// int nid1=Integer.parseInt(nid);
			int c1 = Integer.parseInt(cid);
			if (c1 < 0) {
				return "/mainpage.jsp";
			}
			SnChapter chapter = cs.findById(c1);
			int totalnum = cs.findNovelChapterTotalCount(chapter.getSnNovel());
			req.setAttribute("novel", chapter.getSnNovel());
			req.setAttribute("chapter", chapter);
			req.setAttribute("totalnum", totalnum);

			// 判断是否有登录用户,更新最新阅读记录
			SnUser user = (SnUser) req.getSession().getAttribute("user");
			if (user != null) {
				System.out.println("当前的在线阅读用户是:" + user.toString());
				// 获取小说(创建小说对象)
				// SnNovelService novelService = new SnNovelServiceImpl();
				// SnNovel novel =
				// novelService.findById(chapter.getSnNovel().getNovelId());
				SnNovel novel = new SnNovel();
				novel.setNovelId(chapter.getSnNovel().getNovelId());
				System.out.println("当前的在线阅读书籍是:" + novel.toString());

				SnCollectionService collectionService = new SnCollectionServiceImpl();
				SnCollection collection = collectionService.findDistinctCollection(user, novel);
				System.out.println("当前的在线阅读记录-before:" + collection);
				if (collection != null) {
					// 已经收藏过,更新收藏信息
					collectionService.updateChapterHistoryByNovel(user, novel, chapter);
					System.out.println("小说阅读记录已更新");
				} else {
					// 若没有收藏,不做任何事情
				}
			}

			return "/readonline.jsp";

		} else {
			return "/mainpage.jsp";

		}
	}
}
