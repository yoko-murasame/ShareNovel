
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
	JsonMsg jsonMsg = null;
	
	//访问小说顶级分类的跳转
	public String toCategoryPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
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
			if(defaultPageSize < 1)defaultPageSize = 10;
			//2.查询小说分类
			List<SnCategory> parentCategory = categoryService.findByParentIdByPage(0,defaultPageSize,defaultCurrPage);
			Integer count = categoryService.findCount(0);//总记录数
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

	//category新增/修改
	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		String topCatId = req.getParameter("catId");
		String topCatName = req.getParameter("catName");
		String topCatGender = req.getParameter("catGender");
		//走的是修改方法
		if(topCatId != null && !"".equals(topCatId)){
			try {
				SnCategory catFindById = categoryService.findById(Integer.parseInt(topCatId));
				catFindById.setCatName(topCatName);
				catFindById.setCatGender(Integer.parseInt(topCatGender));
				boolean isok = categoryService.update(catFindById);
				if(isok){
					jsonMsg = JsonMsg.makeSuccess("修改成功!", null);
				}else{
					throw new Exception("数据库插入失败!");
				}
			} catch (Exception e) {
				jsonMsg = JsonMsg.makeFail("修改失败,错误原因: " + e.getMessage(), null);
			}
		}else{
			//走新增方法
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
		}
		
		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}

	//删除
	public String delCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
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
				jsonMsg = JsonMsg.makeFail("删除失败!错误信息: "+e.getMessage(), null);
			}
		}
		
		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}

	//查询单个category
	public String findById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String catId = req.getParameter("id");
		SnCategory category = categoryService.findById(Integer.parseInt(catId));
		if(category!=null){
			jsonMsg = JsonMsg.makeSuccess("查询成功", category);
		}else{
			jsonMsg = JsonMsg.makeFail("查询失败失败!", null);
		}
		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}


	//访问子分类页面的跳转
	public String toSubCategoryPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
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
			if(defaultPageSize < 1)defaultPageSize = 10;
			//2.获取父分类id
			Integer topCatId = Integer.parseInt(req.getParameter("topCatId"));
			//将查询父分类放到request，方便页面显示
			SnCategory topCat = categoryService.findById(topCatId);
			req.setAttribute("topCat", topCat);
			//2.查询小说分类
			List<SnCategory> subCategory = categoryService.findByParentIdByPage(topCatId,defaultPageSize,defaultCurrPage);
			Integer count = categoryService.findCount(topCatId);//总记录数
			PageModel<SnCategory> pageModel = new PageModel<SnCategory>();
			PageModel.wrapPageModel(defaultCurrPage, defaultPageSize, count, subCategory, pageModel);
			//3.放入req
			req.setAttribute("pageModel", pageModel);
			System.out.println(pageModel.toString());
			return "/admin/admin_subcategory.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin";
	}
	
	//子分类category新增/修改
	public String addSubCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		String subCatId = req.getParameter("catId");
		String subCatName = req.getParameter("catName");
		String subCatParentid = req.getParameter("catParentid");
		
		//走的是修改方法
		if(subCatId != null && !"".equals(subCatId)){
			try {
				SnCategory catFindById = categoryService.findById(Integer.parseInt(subCatId));
				catFindById.setCatName(subCatName);
				boolean isok = categoryService.update(catFindById);
				if(isok){
					jsonMsg = JsonMsg.makeSuccess("修改成功!", null);
				}else{
					throw new Exception("数据库插入失败!");
				}
			} catch (Exception e) {
				jsonMsg = JsonMsg.makeFail("修改失败,错误原因: " + e.getMessage(), null);
			}
		}else{
			try {
				//走新增方法
				//查找是否有当前分类信息
				List<SnCategory> list = categoryService.findByCatName(subCatName);
				if(list == null){
					//保存
					SnCategory category = new SnCategory();
					category.setCatName(subCatName);
					category.setCatParentid(Integer.parseInt(subCatParentid));//设置父id
					boolean isok = categoryService.save(category);
					if(isok){
						jsonMsg = JsonMsg.makeSuccess("保存成功!", null);
					}else{
						throw new Exception("数据库插入失败!");
					}
				}else{
					throw new Exception("当前分类已存在!");
				}
			} catch (Exception e) {
				jsonMsg = JsonMsg.makeFail("保存失败,原因如下: "+e.getMessage(), null);
			}

		}
		
		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}
	

	//adminNovellist页面根据id异步获取所有Category
	public String findAllCatById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			String catId = req.getParameter("catId");
			List<SnCategory> allCats = categoryService.findByParentId(Integer.parseInt(catId));
			if(allCats != null){
				jsonMsg = JsonMsg.makeSuccess("查询成功!", allCats);
			}else{
				throw new Exception("无子分类!");
			}
		} catch (Exception e) {
			jsonMsg = JsonMsg.makeFail("查询失败! "+e.getMessage(), null);
		}

		String jsonStr = objectMapper.writeValueAsString(jsonMsg);
		resp.getWriter().write(jsonStr);
		return null;
	}
	
	
	
}
