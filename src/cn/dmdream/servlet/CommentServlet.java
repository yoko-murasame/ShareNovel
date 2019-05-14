package cn.dmdream.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnComment;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnCommentService;
import cn.dmdream.service.impl.SnCommentServiceImpl;
import cn.dmdream.vo.JsonMsg;

public class CommentServlet extends BaseServlet{
	//
	public String getHotCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	public String getComments(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nid=req.getParameter("nid");
		int id=Integer.parseInt(nid);
		SnCommentService commentservice=new SnCommentServiceImpl();
		SnNovel novel=new SnNovel();
		novel.setNovelId(id);
		List<SnComment> commentlist = commentservice.findByNovelByPage(novel,15, 1);
		JsonMsg msg = JsonMsg.makeSuccess("", commentlist);
		ObjectMapper mapper=new ObjectMapper();
		String jstr=mapper.writeValueAsString(msg);
		resp.getWriter().print(jstr);
		return null;
	}
	public String addcomment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content=req.getParameter("content");
		String nid=req.getParameter("nid");
		SnNovel novel=new SnNovel();
		SnUser user=(SnUser)req.getSession().getAttribute("user");
		if(user==null) {
			JsonMsg msg = JsonMsg.makeError("用户未登录", null);
			ObjectMapper mapper=new ObjectMapper();
			String jstr=mapper.writeValueAsString(msg);
			resp.getWriter().print(jstr);
			return null;
		}
		novel.setNovelId(Integer.parseInt(nid));
		SnComment comment=new SnComment();
		comment.setCommContent(content);
		comment.setCommNovel(novel);
		comment.setCommUser(user);
		comment.setCommHitnum(0);
		SnComment p=new SnComment();
		p.setCommId(0);
		comment.setCommParent(p);
		System.out.println(comment);
		SnCommentService commentservice=new SnCommentServiceImpl();
		boolean ret = commentservice.save(comment);
		JsonMsg msg;
		if(ret) {
			msg=JsonMsg.makeSuccess("评论成功",null);
		}else {
			msg=JsonMsg.makeFail("评论失败",null);
		}
		ObjectMapper mapper=new ObjectMapper();
		String jstr=mapper.writeValueAsString(msg);
		resp.getWriter().print(jstr);
		return null;
	}

}
