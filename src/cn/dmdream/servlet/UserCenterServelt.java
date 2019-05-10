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

public class UserCenterServelt extends BaseServlet{
	//默认方法
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	//修改密码
	public String changePWD(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	//验证邮箱
	public String authEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	//上传小说
	public String uploadNovel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	//上传章节
	public String updateChapter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	//收藏小说
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
	//取消收藏
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
	//查询个人信息
	public String queryUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
}
