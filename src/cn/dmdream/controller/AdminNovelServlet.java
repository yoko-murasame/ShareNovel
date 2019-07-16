package cn.dmdream.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnCategory;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnNovelService;
import cn.dmdream.service.impl.SnNovelServiceImpl;
import cn.dmdream.utils.FastDFSClient;
import cn.dmdream.vo.JsonMsg;
import cn.dmdream.vo.PageModel;

public class AdminNovelServlet extends BaseServlet {

	// 指定客户端配置文件绝对路径,配置文件上传对象
	private static String conf = "classpath:client.conf";
	protected static FastDFSClient fds;
	static {
		try {
			fds = new FastDFSClient(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private SnNovelService novelService = new SnNovelServiceImpl();
	ObjectMapper objectMapper = new ObjectMapper();
	JsonMsg jsonMsg = null;

	// 跳转到小说列表界面
	public String toNovelList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 1.获取分页参数
			// 获取当前页和每页数量
			String currPageStr = req.getParameter("curPage");
			String pageSizeStr = req.getParameter("pageSize");
			String keyword = req.getParameter("keyword");
			String checkType = req.getParameter("checkType");
			int defaultCurrPage = 0;
			int defaultPageSize = 0;
			if (currPageStr != null && pageSizeStr != null) {
				try {
					defaultCurrPage = Integer.parseInt(currPageStr);
					defaultPageSize = Integer.parseInt(pageSizeStr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (defaultCurrPage < 1)
				defaultCurrPage = 1;
			if (defaultPageSize < 1)
				defaultPageSize = 10;

			PageModel<SnCategory> pageModel = new PageModel<SnCategory>();
			// 判断是否是根据关键词查询
			if (checkType != null && !checkType.equals("")) {
				Integer checkNum = Integer.parseInt(checkType);
				List<SnNovel> novelChechList = novelService.findByCheckByPage(checkNum, defaultPageSize, defaultCurrPage);
				Integer count = novelService.findCountByStatus(checkNum);// 总记录数
				PageModel.wrapPageModel(defaultCurrPage, defaultPageSize, count, novelChechList, pageModel);
				//放入req 需要1.pageModel2.状态值
				req.setAttribute("pageModel", pageModel);
				req.setAttribute("checkType", checkType);
				req.setAttribute("requestUrl", "adminNovel.do?method=toNovelList&checkType="+checkType);// 设置请求链接
				
			} else if (keyword == null || keyword.equals("")) {
				// 2.查询所有类别
				List<SnNovel> novelList = novelService.findAllByPage(defaultPageSize, defaultCurrPage);
				Integer count = novelService.findCount();// 总记录数

				PageModel.wrapPageModel(defaultCurrPage, defaultPageSize, count, novelList, pageModel);
				// 3.放入req
				req.setAttribute("pageModel", pageModel);
				req.setAttribute("requestUrl", "adminNovel.do?method=toNovelList");// 设置请求链接
			} else {
				// 关键字过滤
				List<SnNovel> novelList = novelService.findByTitleByPage(keyword, defaultPageSize, defaultCurrPage);
				List<SnNovel> countList = novelService.findByTitle(keyword);
				Integer count;// 总记录数
				if (countList == null) {
					count = 0;
				} else {
					count = countList.size();
				}
				PageModel.wrapPageModel(defaultCurrPage, defaultPageSize, count, novelList, pageModel);
				// 3.放入req
				req.setAttribute("pageModel", pageModel);
				req.setAttribute("keyword", keyword);
				req.setAttribute("requestUrl", "adminNovel.do?method=toNovelList&keyword=" + keyword);// 设置请求链接
			}
			return "/admin/admin_novellist.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin";
	}

	// 小说的新增/保存
	public String addNovel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			String novelIdStr = req.getParameter("novelId");// 先获取id判断是修改还是新增
			String novelTitle = req.getParameter("novelTitle");
			String novelAuthor = req.getParameter("novelAuthor");
			String novelSummary = req.getParameter("novelSummary");
			String novelCategory = req.getParameter("novelCategory");
			String novelIsEnd = req.getParameter("novelIsEnd");
			String novelCheck = req.getParameter("novelCheck");
			String novelDownloadurl = req.getParameter("novelDownloadurl");
			String novelCover = req.getParameter("novelCover");
			// 走的是修改方法
			if (novelIdStr != null && !"".equals(novelIdStr)) {
				SnNovel novelFindById = novelService.findById(Integer.parseInt(novelIdStr));
				novelFindById.setNovelTitle(novelTitle);
				novelFindById.setNovelAuthor(novelAuthor);
				novelFindById.setNovelSummary(novelSummary);
				// category
				SnCategory category = new SnCategory();
				category.setCatId(Integer.parseInt(novelCategory));
				novelFindById.setSnCategory(category);
				novelFindById.setNovelIsEnd(Integer.parseInt(novelIsEnd));
				novelFindById.setNovelCheck(Integer.parseInt(novelCheck));
				// download
				Map<String, String> urlMap = new HashMap<String, String>();
				urlMap.put("url", novelDownloadurl);
				novelFindById.setNovelDownloadurl(urlMap);
				novelFindById.setNovelCover(novelCover);

				boolean isok = novelService.update(novelFindById);
				if (isok) {
					jsonMsg = JsonMsg.makeSuccess("修改成功!", null);
				} else {
					throw new Exception("数据库插入失败!");
				}
			} else {
				// 走新增方法 注意新增方法需要判断是否有用户
				// 查找是否有当前名称的小说
				List<SnNovel> list = novelService.findByTitleStrict(novelTitle);
				if (list == null) {
					// 新建novel对象
					SnNovel novel = new SnNovel();
					novel.setNovelTitle(novelTitle);
					novel.setNovelAuthor(novelAuthor);
					novel.setNovelSummary(novelSummary);
					// category
					SnCategory category = new SnCategory();
					category.setCatId(Integer.parseInt(novelCategory));
					novel.setSnCategory(category);
					novel.setNovelIsEnd(Integer.parseInt(novelIsEnd));
					//设置状态,判断是用户进来的还是管理员进来的
					if(novelCheck == null){
						//用户进来的
						novel.setNovelCheck(0);
					}else{
						novel.setNovelCheck(Integer.parseInt(novelCheck));
					}
					// download
					Map<String, String> urlMap = new HashMap<String, String>();
					urlMap.put("url", "" + novelDownloadurl);
					novel.setNovelDownloadurl(urlMap);
					novel.setNovelCover(novelCover);
					SnUser user = (SnUser) req.getSession().getAttribute("user");
					// 判断用户
					if (user == null) {
						user = new SnUser();
						user.setUserId(1);
					}
					// 设置用户
					novel.setNovelShareUser(user);
					// 保存
					boolean isok = novelService.save(novel);
					if (isok) {
						jsonMsg = JsonMsg.makeSuccess("保存成功!", null);
					} else {
						throw new Exception("数据库插入失败!");
					}
				} else {
					throw new Exception("小说《" + list.get(0).getNovelTitle() + "》已存在!");
				}
			}

		} catch (Exception e) {
			jsonMsg = JsonMsg.makeFail("保存失败,原因如下: " + e.getMessage(), null);
		}

		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}

	// 文件的异步上传,返回图片地址
	public String ajaxFileUpload(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("进入了文件上传方法");
		String path = "";
		// 1.存储表单中数据
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 2.利用req.getInputStream();获取到请求体中全部数据,进行拆分和封装
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			List<FileItem> list = upload.parseRequest(req);

			// 3.遍历集合
			for (FileItem item : list) {
				if (item.isFormField()) {
					// 4.如果当前的FileItem对象是普通项
					// 将普通项上name属性的值作为键,将获取到的内容作为值,放入MAP中
					// {username<==>tom,password<==>1234}
					map.put(item.getFieldName(), item.getString("utf-8"));
				} else {
					// 5.如果当前的FileItem对象是上传项
					// 通过FileItem获取到输入流对象,通过输入流可以获取到图片二进制数据
					InputStream is = item.getInputStream();
					// 获取后缀
					String fullName = item.getName();
					String extName = fullName.substring(fullName.lastIndexOf(".") + 1);
					// 调用fastDFS上传文件
					path = fds.uploadFile(is, extName);
					// 从配置文件获取拼接的前串http://193.112.41.124/
					ResourceBundle resourceBundle = ResourceBundle.getBundle("resources");
					String head = resourceBundle.getString("IMAGE_SERVER_URL");
					// 最终图片路径
					path = head + path;
					// path =
					// "http://193.112.41.124/group1/M00/00/00/rBAABVzVPXuAb0s0AAAouX6YDHQ53.jpeg";测试用地址
					// System.out.println(path);
					jsonMsg = JsonMsg.makeSuccess("上传成功", path);
				}
			}
		} catch (Exception e) {
			jsonMsg = JsonMsg.makeFail("上传失败: " + e.getMessage(), null);
		}
		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		// 6.将图片地址返回
		// http://193.112.41.124/group1/M00/00/00/rBAABVzNloqAZyZpAAebYAJE1TA251.jpg
		resp.getWriter().write(jsonStr);
		return null;
	}

	// 查询单个Novel
	public String findById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String novelId = req.getParameter("novelId");
		try {
			SnNovel novelFind = novelService.findById(Integer.parseInt(novelId));
			if (novelFind != null) {
				jsonMsg = JsonMsg.makeSuccess("查询成功", novelFind);
			} else {
				throw new Exception("查询失败!");
			}
		} catch (Exception e) {
			jsonMsg = JsonMsg.makeFail("错误: ", null);
		}
		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}

	// 删除
	public String delNovel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// ids在前端判断一次,后端一次
		String[] ids = req.getParameterValues("novelIds");
		if (ids == null) {
			jsonMsg = JsonMsg.makeFail("删除失败,没有选择id", null);
		} else {
			try {
				for (String id : ids) {
					SnNovel novel = new SnNovel();
					novel.setNovelId(Integer.parseInt(id));
					novelService.delete(novel);
				}
				jsonMsg = JsonMsg.makeSuccess("成功删除" + ids.length + "条数据!", null);
			} catch (Exception e) {
				jsonMsg = JsonMsg.makeFail("删除失败!错误信息: " + e.getMessage(), null);
			}
		}

		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}
}
