package cn.dmdream.controller.novelCheck;

import java.io.IOException;
import java.io.PrintWriter;
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
import cn.dmdream.vo.PageModel;

public class novCk extends BaseServlet {
	
	public String init(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		SnNovelService snNovelService=new SnNovelServiceImpl();
		
		List<SnNovel> list;
		
		JsonMsg jsonMsg=null;
		
		System.out.println("1");
		try {
			System.out.println("2");
			list = snNovelService.findAll();
			
			PageModel<SnNovel> pageModel=new PageModel<SnNovel>();
			
			pageModel.setList(list);
			
			list.forEach(System.out::println);
			
			jsonMsg=JsonMsg.makeSuccess("已将小说数据打包", pageModel);
		} catch (Exception e) {
			System.out.println("3");
			e.printStackTrace();
			jsonMsg=JsonMsg.makeFail("小说数据获取失败", "");
		}
		
		System.out.println("4");
		
		//序列化jsonMsg
		ObjectMapper objectMapper = new ObjectMapper();
		String writeValueAsString = objectMapper.writeValueAsString(jsonMsg);
		
		response.setContentType("text/html;charset=utf-8");  
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(writeValueAsString);
		out.flush();
		out.close();
		
		return null;/*"/mana_h5/basic_tabel.html";*/
	}
	
}
