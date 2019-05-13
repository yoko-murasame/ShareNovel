package cn.dmdream.controller.userMana;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnUserService;
import cn.dmdream.service.impl.SnUserServiceImpl;
import cn.dmdream.vo.JsonMsg;
import cn.dmdream.vo.PageModel;

public class AdminUserlist extends BaseServlet {

	SnUserService snUserService=new SnUserServiceImpl();
	ObjectMapper objectMapper = new ObjectMapper();
	JsonMsg jsonMsg=null;
	
	public String toPage(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		//1.获取分页参数
		//获取当前页和每页数量
		String currPageStr = request.getParameter("curPage");
		String pageSizeStr = request.getParameter("pageSize");
		int defaultCurrPage = 0;
		int defaultPageSize = 0;
		if(currPageStr!=null &&pageSizeStr!=null){
			try {
				defaultCurrPage = Integer.parseInt(currPageStr);
				defaultPageSize = Integer.parseInt(pageSizeStr);
				System.out.println("收到分页参数");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(defaultCurrPage < 1)defaultCurrPage = 1;
		if(defaultPageSize < 1)defaultPageSize = 10;
		System.out.println(defaultCurrPage+":"+defaultPageSize);
		//获取用户总数量
		int userAmount=snUserService.count();
		
		//2.准备打包、返回数据
		//尝试从数据库获取用户数据，若成功，将数据打包；若失败，返回错误提醒
		List<SnUser> userList ;
		try {
			//获取用户数据
			userList = snUserService.findByPage(defaultPageSize, defaultCurrPage);
			userList.forEach(System.out::println);
			//计算分页数据
			PageModel<SnUser> pageModel=new PageModel<SnUser>();
			PageModel.wrapPageModel(defaultCurrPage, defaultPageSize, userAmount, userList, pageModel);
			//打包
			jsonMsg=JsonMsg.makeSuccess("用户数据已打包", pageModel);
		} catch (Exception e) {
			jsonMsg=JsonMsg.makeFail("用户数据获取失败", "");
		}
		//序列化json
		String writeValueAsString = objectMapper.writeValueAsString(jsonMsg);
		//返回
		response.setContentType("text/html;charset=utf-8");  
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(writeValueAsString);
		out.flush();
		out.close();
		
		
		return null;
	}
	
	public String ckun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		String username=request.getParameter("username").trim();
		
		SnUser snUser=snUserService.findByUsername(username);
		System.out.println("开始检查");
		try {
			if(snUser!=null){
				jsonMsg=JsonMsg.makeError("该用户名已经存在", 0);
				System.out.println("0");
			}else{
				jsonMsg=JsonMsg.makeSuccess(null, 1);
				System.out.println("1");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jsonMsg=JsonMsg.makeFail("获取用户数据失败", null);
			System.out.println("2");
			e.printStackTrace();
		}
		System.out.println("检查结束");
		//序列化json
		String writeValueAsString = objectMapper.writeValueAsString(jsonMsg);
		//返回
		response.setContentType("text/html;charset=utf-8");  
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(writeValueAsString);
		out.flush();
		out.close();
		
		
		return null;
	}
	
}
