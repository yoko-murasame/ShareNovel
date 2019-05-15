package cn.dmdream.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnCollection;
import cn.dmdream.entity.SnComment;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnCommentService;
import cn.dmdream.service.SnUserService;
import cn.dmdream.service.impl.SnCollectionServiceImpl;
import cn.dmdream.service.impl.SnCommentServiceImpl;
import cn.dmdream.service.impl.SnUserServiceImpl;
import cn.dmdream.utils.EmailUitl;
import cn.dmdream.vo.JsonMsg;

public class UserCenterServelt extends BaseServlet{
	//修改密码
	public String changePWD(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oldpwd=req.getParameter("oldpwd");
		String newpwd=req.getParameter("newpwd");
		SnUser user=(SnUser)req.getSession().getAttribute("user");
		if(oldpwd.equals(user.getUserPassword())) {
			user.setUserPassword(newpwd);
			SnUserService sss=new SnUserServiceImpl();
			boolean ret = sss.update(user);
			JsonMsg msg;
			if(ret) {
				msg = JsonMsg.makeSuccess("密码修改成功",null);
			}else {
				msg=JsonMsg.makeFail("密码修改失败", null);
			}
			ObjectMapper mapper=new ObjectMapper();
			String jstr=mapper.writeValueAsString(msg);
			resp.getWriter().print(jstr);
		}else {
			JsonMsg msg = JsonMsg.makeError("访问不合法", null);
			ObjectMapper mapper=new ObjectMapper();
			String jstr=mapper.writeValueAsString(msg);
			resp.getWriter().print(jstr);
			
		}
		return null;
	}
	//验证邮箱
	public String authEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SnUser user=(SnUser)req.getSession().getAttribute("user");
		if(user.getUserEmailActive()==0) {//判断为 未激活
			Date date = new Date();
			byte[] bar=date.toString().getBytes();
			Encoder encode=Base64.getEncoder();
			String str=encode.encodeToString(bar);
			String ip = InetAddress.getLocalHost().getHostAddress();   
			String url="https://"+ip+":8080"+req.getContextPath()+"/usercenter.do?method=confirmEmail&authcode="+str;
			System.out.println(url);
			EmailUitl.AuthUserEmail(user.getUserEmail(), url);
			req.getSession().setAttribute("authcode", str);
			JsonMsg msg = JsonMsg.makeAuth("验证信息已发生",null);
			ObjectMapper mapper=new ObjectMapper();
			String jstr=mapper.writeValueAsString(msg);
			resp.getWriter().print(jstr);
		}else {
			JsonMsg msg = JsonMsg.makeError("用户信息错误",null);
			ObjectMapper mapper=new ObjectMapper();
			String jstr=mapper.writeValueAsString(msg);
			resp.getWriter().print(jstr);
			
		}
		return null;

	}
	//确认验证邮箱
	public String confirmEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code1=(String)req.getParameter("authcode");
		String code2=(String)req.getSession().getAttribute("authcode");
		System.out.println("code1="+code1+" code2="+code2);
		if(code1!=null&&code2!=null&&code1.equals(code2)) {
			SnUser user=(SnUser)req.getSession().getAttribute("user");
			user.setUserEmailActive(1);
			SnUserService sss=new SnUserServiceImpl();
			boolean update = sss.update(user);
			if(update) {
				req.setAttribute("authemailok",true);
			}else {
				req.setAttribute("authemailok", false);
			}
		}
		return "/authemail.jsp";
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
	public String collectNovel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nid=req.getParameter("nid");
		System.out.println(nid);
		if(nid!=null) {
			int id=Integer.parseInt(nid);
			SnNovel novel=new SnNovel();
			novel.setNovelId(id);
			//获取登录的用户
			SnUser user=(SnUser)req.getSession().getAttribute("user");
			if(user==null) {
				JsonMsg msg = JsonMsg.makeAuth("请先登录", null);
				ObjectMapper mapper=new ObjectMapper();
				String jsonstr = mapper.writeValueAsString(msg);
				resp.getWriter().print(jsonstr);
				return null;
			}else {
				SnCollectionServiceImpl collectionservice=new SnCollectionServiceImpl();
				SnCollection retcoll = collectionservice.findDistinctCollection(user, novel);
				JsonMsg msg;
				if(retcoll==null) {//查询是否已经收藏  
					//为空则未收藏
					SnCollection collection=new SnCollection();
					collection.setCollectUser(user);
					collection.setCollectNovel(novel);
					SnChapter chapter=new SnChapter();
					chapter.setChapterId(-1);
					collection.setCollectChapterHistory(chapter);
					boolean save = collectionservice.save(collection);
					if(save)
						msg=JsonMsg.makeSuccess("收藏成功", null);
					else {
						msg=JsonMsg.makeSuccess("收藏失败", null);
					}
				}else {
					//已经收藏过了 提示已收藏
					msg=JsonMsg.makeFail("请不要重复收藏", null);
				}
				ObjectMapper mapper=new ObjectMapper();
				String jsonstr = mapper.writeValueAsString(msg);
				System.out.println(jsonstr);
				resp.getWriter().print(jsonstr);		
			}
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
				//不为空则收藏
				SnCollection collection=new SnCollection();
				collection.setCollectUser(user);
				collection.setCollectNovel(novel);
				collectionservice.delete(collection);
				JsonMsg.makeSuccess("取消收藏成功", null);
			}else {
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
	public String queryComments(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page=req.getParameter("page");
		SnCommentService commentservice =new SnCommentServiceImpl();
		SnUser user=(SnUser)req.getSession().getAttribute("user");
		System.out.println("username"+user.getUserNickname()+"page="+page);
		List<SnComment> list = commentservice.findByUserByPage(user, 100,Integer.parseInt(page));
		//List<SnComment> list = commentservice.queryByUser(user);
		req.setAttribute("comments",list );
		return "/usercenterjsp/mycomments.jsp";
	}
}
