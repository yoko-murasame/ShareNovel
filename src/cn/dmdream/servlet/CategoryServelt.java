package cn.dmdream.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnCategory;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.service.SnCategoryService;
import cn.dmdream.service.SnNovelService;
import cn.dmdream.service.impl.SnCategoryServiceImpl;
import cn.dmdream.service.impl.SnNovelServiceImpl;
import cn.dmdream.vo.JsonMsg;

public class CategoryServelt extends BaseServlet{
	
	private SnCategoryService categoryService = new SnCategoryServiceImpl();
	ObjectMapper objectMapper = new ObjectMapper();
	JsonMsg jsonMsg = null;
	//默认方法
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	/**
	 * @return 返回json数据被客户端
	 */
	public String categoryQuery(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String category=req.getParameter("category");
		String cid=req.getParameter("cid");
		ObjectMapper mapper=new ObjectMapper();
		JsonMsg msg=null;
		if(cid!=null) {
			try {
				int categoryid=Integer.parseInt(cid);
				SnNovelService novelservice=new SnNovelServiceImpl();
				List<SnNovel> list = novelservice.queryByCategory(categoryid);
				msg = JsonMsg.makeSuccess("1",list);			
			}catch (NumberFormatException e) {
				msg = JsonMsg.makeFail("查询错误",null);
			}
			String jsonstr=mapper.writeValueAsString(msg);
			resp.getWriter().print(jsonstr);
		}
		return null;
	}
	public String getCategorys(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println(111111);
		SnCategoryService cateservice=new SnCategoryServiceImpl();
		List<SnCategory> list = cateservice.queryAll();
		ObjectMapper mapper=new ObjectMapper();
		JsonMsg msg=JsonMsg.makeSuccess("1", list);
		String jsonstr=mapper.writeValueAsString(msg);
		resp.getWriter().print(jsonstr);
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
