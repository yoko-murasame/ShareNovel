package cn.dmdream.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnAdmin;
import cn.dmdream.entity.SnCategory;
import cn.dmdream.service.SnCategoryService;
import cn.dmdream.service.impl.SnCategoryServiceImpl;
import cn.dmdream.vo.JsonMsg;
import cn.dmdream.vo.PageModel;

public class AdminCategoryServlet extends BaseServlet {

	private SnCategoryService categoryService = new SnCategoryServiceImpl();
	ObjectMapper objectMapper = new ObjectMapper();
	
	//访问小说顶级分类的跳转
	public String toCategoryPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//1.判断是session中是否有管理员,若没有返回管理员登录页
			HttpSession session = req.getSession();
			SnAdmin admin = (SnAdmin)session.getAttribute("admin");
			if(admin==null) return "/admin";
			//2.获取分页参数
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
			if(defaultPageSize < 1)defaultPageSize = 10;
			//2.查询小说分类
			List<SnCategory> parentCategory = categoryService.findByParentIdByPage(0,defaultPageSize,defaultCurrPage);
			Integer count = categoryService.findCount(0);
			PageModel<SnCategory> pageModel = new PageModel<SnCategory>();
			PageModel.wrapPageModel(defaultCurrPage, defaultPageSize, count, parentCategory, pageModel);
			//3.放入req
			req.setAttribute("pageModel", pageModel);
			System.out.println(pageModel.toString());
			return "/admin/admin_topcategory.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin";
	}

	//category新增
	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String topCatName = req.getParameter("topCatName");
		String topCatGender = req.getParameter("topCatGender");
		JsonMsg jsonMsg = null;
		//查找是否有当前分类信息
		List<SnCategory> list = categoryService.findByCatName(topCatName);
		if(list == null){
			//保存
			SnCategory category = new SnCategory();
			category.setCatName(topCatName);
			category.setCatGender(Integer.parseInt(topCatGender));
			category.setCatParentid(0);//设置父id 0
			
			boolean isok = categoryService.save(category);
			if(isok){
				jsonMsg = JsonMsg.makeSuccess("保存成功!", null);
			}else{
				jsonMsg = JsonMsg.makeFail("保存失败,原因未知!", null);
			}
		}else{
			jsonMsg = JsonMsg.makeFail("保存失败,当前分类已存在!", null);
		}
		
		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}

	//删除
	public String delCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		JsonMsg jsonMsg = null;
		//ids在前端判断一次,后端一次
		String[] ids = req.getParameterValues("catIds");
		if(ids == null){
			jsonMsg = JsonMsg.makeFail("删除失败,没有选择id", null);
		}else{
			try {
				for (String id : ids) {
					SnCategory category = new SnCategory();
					category.setCatId(Integer.parseInt(id));
					categoryService.delete(category);
				}
				jsonMsg = JsonMsg.makeSuccess("成功删除"+ ids.length +"条数据!", null);
			} catch (Exception e) {
				jsonMsg = JsonMsg.makeFail("删除失败!错误信息: "+e.toString(), null);
			}
		}
		
		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}
	
	
	
	
}
