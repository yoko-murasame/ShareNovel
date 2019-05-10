package cn.dmdream.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnCollection;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.impl.SnCollectionServiceImpl;
import cn.dmdream.vo.JsonMsg;

public class NovelshelfServlet extends BaseServlet{
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	public String CollectNoevl(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nid=req.getParameter("nid");
		if(nid!=null) {
			int id=Integer.parseInt(nid);
			SnNovel novel=new SnNovel();
			novel.setNovelId(id);
			//获取登录的用户
			SnUser user=(SnUser)req.getSession().getAttribute("user");
			SnCollectionServiceImpl collectionservice=new SnCollectionServiceImpl();
			SnCollection retcoll = collectionservice.findDistinctCollection(user, novel);
			JsonMsg msg=null;
			if(retcoll==null) {//查询是否已经收藏  
				//为空则未收藏
				SnCollection collection=new SnCollection();
				collection.setCollectUser(user);
				collection.setCollectNovel(novel);
				collectionservice.save(collection);
				JsonMsg.makeSuccess("收藏成功", null);
			}else {
				//已经收藏过了 提示已收藏
				JsonMsg.makeFail("请不要重复收藏", null);
			}
			ObjectMapper mapper=new ObjectMapper();
			String jsonstr = mapper.writeValueAsString(msg);
			resp.getWriter().print(jsonstr);
		}
		return null;
	}	
	public String deleteNoevl(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nid=req.getParameter("nid");
		if(nid!=null) {
			int id=Integer.parseInt(nid);
			SnNovel novel=new SnNovel();
			novel.setNovelId(id);
			//获取登录的用户
			SnUser user=(SnUser)req.getSession().getAttribute("user");
			SnCollectionServiceImpl collectionservice=new SnCollectionServiceImpl();
			SnCollection retcoll = collectionservice.findDistinctCollection(user, novel);
			JsonMsg msg=null;
			if(retcoll!=null) {//查询是否已经收藏  
				//为空则未收藏
				SnCollection collection=new SnCollection();
				collection.setCollectUser(user);
				collection.setCollectNovel(novel);
				collectionservice.delete(collection);
				JsonMsg.makeSuccess("取消收藏成功", null);
			}else {
				//已经收藏过了 提示已收藏
				JsonMsg.makeError("信息错误", null);
			}
			ObjectMapper mapper=new ObjectMapper();
			String jsonstr = mapper.writeValueAsString(msg);
			resp.getWriter().print(jsonstr);
		}
		return null;
	}
}
