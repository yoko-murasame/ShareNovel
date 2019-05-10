package cn.dmdream.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.service.SnNovelService;
import cn.dmdream.service.impl.SnNovelServiceImpl;
import cn.dmdream.vo.JsonMsg;

public class CategoryServelt extends BaseServlet{
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
				msg = JsonMsg.makeFail("",null);
			}
			String jsonstr=mapper.writeValueAsString(msg);
			resp.getWriter().print(jsonstr);
		}
		return null;
	}

}
