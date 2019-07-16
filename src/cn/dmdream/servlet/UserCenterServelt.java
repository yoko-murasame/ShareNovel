package cn.dmdream.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
import cn.dmdream.service.SnChapterService;
import cn.dmdream.service.SnCollectionService;
import cn.dmdream.service.SnCommentService;
import cn.dmdream.service.SnNovelService;
import cn.dmdream.service.SnUserService;
import cn.dmdream.service.impl.SnChapterServiceImpl;
import cn.dmdream.service.impl.SnCollectionServiceImpl;
import cn.dmdream.service.impl.SnCommentServiceImpl;
import cn.dmdream.service.impl.SnNovelServiceImpl;
import cn.dmdream.service.impl.SnUserServiceImpl;
import cn.dmdream.utils.EmailUitl;
import cn.dmdream.utils.FastDFSClient;
import cn.dmdream.vo.JsonMsg;
import cn.dmdream.vo.PageModel;

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
		JsonMsg jsonMsg = null;
		try {
			String sid=req.getParameter("novelId");
			Integer id=-1;
			id=Integer.parseInt(sid);
			String chapterTitle=req.getParameter("chapterTitle");
			String chapterContent=req.getParameter("chapterContent");
			
			SnNovel snNovel= new SnNovel();
			snNovel.setNovelId(id);
			SnChapterService snChapterService=new SnChapterServiceImpl();
			SnChapter snChapter=new SnChapter();
			snChapter.setChapterTitle(chapterTitle);
			snChapter.setChapterContent(chapterContent);
			snChapter.setSnNovel(snNovel);
			
			boolean isok=snChapterService.save(snChapter);
			if(isok){
				jsonMsg=JsonMsg.makeSuccess("新增章节成功", null);
			}else{
				throw new Exception("数据库插入失败！");
			}

		} catch (Exception e) {
			jsonMsg=JsonMsg.makeFail("新增章节失败！！！错误如下:"+e.getMessage(), null);
		}
		ObjectMapper mapper=new ObjectMapper();
		String jsonstr = mapper.writeValueAsString(jsonMsg);
		resp.getWriter().print(jsonstr);
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
				collection.setCollectId(retcoll.getCollectId());
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
	//加载上传小说页面
	public String toUploadNovelJsp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*	    SnCategoryService snCategoryService=new SnCategoryServiceImpl();
	    List<SnCategory> parentList=snCategoryService.findAllParent();
	    List<List<SnCategory>> childLists=new ArrayList<List<SnCategory>>();
	    List<String> sParentList=new ArrayList<String>();
	    List<List<String>> sChildLists=new ArrayList<List<String>>();
	    for(int i=0;i<parentList.size();i++){
	    	sParentList.add(parentList.get(i).getCatName());
	    	List<SnCategory> childList=new ArrayList<SnCategory>();
	    	childList=snCategoryService.findByParentId(parentList.get(i).getCatId());
	    	childLists.add(childList);
	    	List<String> sChildList = new ArrayList<String>();
	    	for(int j=0;j<childList.size();j++){
	    		sChildList.add(childList.get(i).getCatName());
	    	}
	    	sChildLists.add(sChildList);
	    }
	    req.setAttribute("sParentList", sParentList);
	    req.setAttribute("sChildLists", sChildLists);*/
		return "usercenterjsp/uploadnovel.jsp";
	}	
	
	//加载我的分享页面
	public String toMyUploadJsp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			//1.获取分页参数
			//获取当前页和每页数量
			String currPageStr = req.getParameter("curPage");
			String pageSizeStr = req.getParameter("pageSize");
			int defaultCurrPage = 0;
			int defaultPageSize = 0;
			if(currPageStr!=null &&pageSizeStr!=null){
				try {
					defaultCurrPage = Integer.parseInt(currPageStr);
					defaultPageSize = Integer.parseInt(pageSizeStr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(defaultCurrPage < 1)defaultCurrPage = 1;
			if(defaultPageSize < 1)defaultPageSize = 15;
		
		    SnNovelService snNovelService=new SnNovelServiceImpl();
			//获取登录的用户
			SnUser user=(SnUser)req.getSession().getAttribute("user");
			int userId=user.getUserId();
		    /*int userId=1;//测试用
*/		    List<SnNovel> list=snNovelService.findByShareUserIdByPage(userId, defaultPageSize, defaultCurrPage);
		    int totalCount=snNovelService.findCountByShareUserId(userId);	
		    PageModel<SnNovel> myUploadPageModel=new PageModel<SnNovel>();
		    PageModel.wrapPageModel(defaultCurrPage, defaultPageSize, totalCount, list, myUploadPageModel);
		    req.setAttribute("myUploadPageModel", myUploadPageModel);
		    return "usercenterjsp/myupload.jsp";
	}
	//加载我的上传-章节修改页面
	public String toUpdateNovelJsp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.获取分页参数
		//获取当前页和每页数量
		String currPageStr = req.getParameter("curPage");
		String pageSizeStr = req.getParameter("pageSize");
		int defaultCurrPage = 0;
		int defaultPageSize = 0;
		if(currPageStr!=null &&pageSizeStr!=null){
			try {
				defaultCurrPage = Integer.parseInt(currPageStr);
				defaultPageSize = Integer.parseInt(pageSizeStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(defaultCurrPage < 1)defaultCurrPage = 1;
		if(defaultPageSize < 1)defaultPageSize = 20;
		
		SnChapterService snChapterService=new SnChapterServiceImpl();
		//PageModel<SnNovel> myUploadPageModel=(PageModel<SnNovel>)req.getAttribute("myUploadPageModel");
		SnNovelService snNovelService=new SnNovelServiceImpl();
		//SnNovel snNovel=myUploadPageModel.getList().get(index)
		String sid=req.getParameter("novelId");
		Integer id=Integer.parseInt(sid);
		SnNovel snNovel=snNovelService.findById(id);
		List<SnChapter> list = snChapterService.findByNovelByPage(snNovel, defaultPageSize, defaultCurrPage);
		
		int totalCount=snChapterService.findNovelChapterTotalCount(snNovel);
		PageModel<SnChapter> chapPageModel=new PageModel<SnChapter>();
		PageModel.wrapPageModel(defaultCurrPage, defaultPageSize, totalCount, list, chapPageModel);
		req.setAttribute("chapPageModel", chapPageModel);
		
		String chapterTitle=req.getParameter("chapterTitle");
		String chapterContent=req.getParameter("chapterContent");
		if(chapterTitle==null){
			chapterTitle=list.get(0).getChapterTitle();
		}
		if(chapterContent==null){
			chapterContent=list.get(0).getChapterContent();
		}
		req.setAttribute("chapterTitle", chapterTitle);
		req.setAttribute("chapterContent", chapterContent);
		return "usercenterjsp/myuploadjsp/updatenovel.jsp";
	}
	
	//加载我的上传-新增章节页面
	public String toUpdateChapJsp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String novelId=req.getParameter("novelId");
		req.setAttribute("novelId", novelId);
		return "usercenterjsp/updatechap.jsp";
	}
	
	//加载我的书架页面
	public String toMyBookShelfJsp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			//1.获取分页参数
			//获取当前页和每页数量
			String currPageStr = req.getParameter("curPage");
			String pageSizeStr = req.getParameter("pageSize");
			int defaultCurrPage = 0;
			int defaultPageSize = 0;
			if(currPageStr!=null &&pageSizeStr!=null){
				try {
					defaultCurrPage = Integer.parseInt(currPageStr);
					defaultPageSize = Integer.parseInt(pageSizeStr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(defaultCurrPage < 1)defaultCurrPage = 1;
			if(defaultPageSize < 1)defaultPageSize = 15;
		
			SnCollectionService snCollectionService=new SnCollectionServiceImpl();
			//获取登录的用户
			SnUser user=(SnUser)req.getSession().getAttribute("user");
			/*SnUser user=snUserService.findById(1);//测试用*/
			List<SnCollection> list=snCollectionService.wxwfindByUserByPage(user, defaultPageSize, defaultCurrPage);
		    int totalCount;	
		    if(list == null){
		    	totalCount = 0;
		    }else{
		    	totalCount=list.size();
		    }
		    PageModel<SnCollection> myBookShelfPageModel=new PageModel<SnCollection>();
		    PageModel.wrapPageModel(defaultCurrPage, defaultPageSize, totalCount, list, myBookShelfPageModel);
		    req.setAttribute("myBookShelfPageModel", myBookShelfPageModel);
		    return "/usercenterjsp/mybookshelf.jsp";
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
	public String changeHeadPic(HttpServletRequest req, HttpServletResponse resp)  {
		System.out.println("上传头像");
		String imgvalue=req.getParameter("img");
		imgvalue = imgvalue.substring(imgvalue.indexOf(",")+1);//去掉base64字符串的开头部分
		try {
			
			byte[] decode2 = Base64.getDecoder().decode(imgvalue);
			String conf = "classpath:client.conf";	
			FastDFSClient fds = new FastDFSClient(conf);
			String url=fds.uploadFile(decode2,"jpeg");
			ResourceBundle resourceBundle = ResourceBundle.getBundle("resources");
			String head = resourceBundle.getString("IMAGE_SERVER_URL");
			url = head + url;
			SnUser user=(SnUser)req.getSession().getAttribute("user");
			user.setUserNickpic(url);
			SnUserService sss=new SnUserServiceImpl();
			boolean update = sss.update(user);
			JsonMsg msg=null;
			if(update) {
				msg=JsonMsg.makeSuccess("头像修改成功",null);
				
			}else {
				msg=JsonMsg.makeFail("头像修改失败", null);
				
			}
			ObjectMapper mapper=new ObjectMapper();
			String str = mapper.writeValueAsString(msg);
			resp.getWriter().write(str);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
}
