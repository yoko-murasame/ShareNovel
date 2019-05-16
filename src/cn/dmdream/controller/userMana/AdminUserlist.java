package cn.dmdream.controller.userMana;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnAdmin;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnUserService;
import cn.dmdream.service.impl.SnUserServiceImpl;
import cn.dmdream.vo.JsonMsg;
import cn.dmdream.vo.PageModel;

public class AdminUserlist extends BaseServlet {

	SnUserService snUserService=new SnUserServiceImpl();
	ObjectMapper objectMapper = new ObjectMapper();
	JsonMsg jsonMsg=null;
	
	public String getSession(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		try {
			SnAdmin snAdmin=(SnAdmin) session.getAttribute("admin");
			if(snAdmin!=null){
				jsonMsg=JsonMsg.makeSuccess("获取session成功", snAdmin);
			}else{
				jsonMsg=JsonMsg.makeFail("session为空", null);
			}
		} catch (Exception e) {
			jsonMsg=JsonMsg.makeError("获取session异常", null);
			e.printStackTrace();
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
	
	public String newAdd(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		String nickPic=request.getParameter("nickPic");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String nickName=request.getParameter("nickName");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		Integer emailActive=Integer.parseInt(request.getParameter("emailActive"));
		Integer phoneActive=Integer.parseInt(request.getParameter("phoneActive"));
		
		//检查用户名是否重复
		SnUser snUser2=snUserService.findByUsername(username);
		System.out.println("开始检查");
		if(snUser2!=null){
			jsonMsg=JsonMsg.makeFail("该用户名已经存在", 0);
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
		
		SnUser snUser=new SnUser(null, username, password, nickName, nickPic, email, phone, emailActive, phoneActive, null);
		System.out.println(snUser);
		
		boolean isok=snUserService.save(snUser);
		System.out.println("完成保存操作");
		System.out.println(isok);
		
		try {
			if(isok){
				jsonMsg=JsonMsg.makeSuccess("新增成功", null);
			}else{
				jsonMsg=JsonMsg.makeFail("新增失败", null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jsonMsg=JsonMsg.makeError("新增出现异常", null);
			e.printStackTrace();
		}
		
		System.out.println("信息打包完成");
		
		//序列化json
		String writeValueAsString = objectMapper.writeValueAsString(jsonMsg);
		//返回
		response.setContentType("text/html;charset=utf-8");  
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(writeValueAsString);
		out.flush();
		out.close();
		
		System.out.println("完成序列化");
		
		return null;
	}
	
	public String update(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		String username=request.getParameter("username");
		
		try {
			SnUser snUser=snUserService.findByUsername(username);
			System.out.println(snUser);
			jsonMsg=JsonMsg.makeSuccess("用户信息查找成功", snUser);
		} catch (Exception e) {
			jsonMsg=JsonMsg.makeError("用户信息查找异常", null);
			e.printStackTrace();
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
		
		System.out.println("完成序列化");
		
		return null;
	}
	
	public String newUpdate(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		
		Integer id=Integer.parseInt(request.getParameter("id").trim());
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String nickName=request.getParameter("nickName");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		String registTime=request.getParameter("registTime");
		
		SnUser snUser=snUserService.findById(id);
		snUser.setUserUsername(username);
		snUser.setUserPassword(password);
		snUser.setUserNickname(nickName);
		snUser.setUserEmail(email);
		snUser.setUserPhone(phone);
		snUser.setUserRegisttime(registTime);
		
		boolean isok=snUserService.update(snUser);
		
		try {
			if(isok){
				jsonMsg=JsonMsg.makeSuccess("更新成功", null);
			}else{
				jsonMsg=JsonMsg.makeFail("更新失败", null);
			}
		} catch (Exception e) {
			jsonMsg=JsonMsg.makeError("更新异常", null);
			e.printStackTrace();
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
	
	public String active(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		Integer active=Integer.parseInt(request.getParameter("active"));
		String obj=request.getParameter("obj");
		String username=request.getParameter("username");
		
		SnUser snUser=snUserService.findByUsername(username);
		
		try {
			if(obj.equals("email")){
				snUser.setUserEmailActive(Math.abs((int)(active-1)));
				snUserService.update(snUser);
				jsonMsg=JsonMsg.makeSuccess("邮箱激活状态修改成功", Math.abs((int)(active-1)));
			}else if(obj.equals("phone")){
				snUser.setUserPhoneActive(Math.abs((int)(active-1)));
				snUserService.update(snUser);
				jsonMsg=JsonMsg.makeSuccess("电话激活状态修改成功", Math.abs((int)(active-1)));
			}else{
				jsonMsg=JsonMsg.makeFail("激活类型出错", null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			jsonMsg=JsonMsg.makeError("激活状态出现异常", null);
			e.printStackTrace();
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
	
	public String ussear(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
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
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(defaultCurrPage < 1)defaultCurrPage = 1;
		if(defaultPageSize < 1)defaultPageSize = 10;
		System.out.println(defaultCurrPage+":"+defaultPageSize);
		//获取用户总数量
		int userAmount=1;
		
		try {
			String username=request.getParameter("username");
			SnUser snUser=snUserService.findByUsername(username);
			List<SnUser> list=new ArrayList<SnUser>();
			list.add(snUser);
			
			PageModel<SnUser> pageModel=new PageModel<SnUser>();
			PageModel.wrapPageModel(defaultCurrPage, defaultPageSize, userAmount, list, pageModel);
			 jsonMsg=JsonMsg.makeSuccess("用户查询成功", pageModel);
		} catch (Exception e) {
			jsonMsg=JsonMsg.makeError("用户查询异常", null);
			e.printStackTrace();
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
	
	public String delete(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		String username=request.getParameter("username");
		
		try {
			SnUser snUser=snUserService.findByUsername(username);
			
			boolean isok=snUserService.deleteById(snUser.getUserId());
			
			if(isok){
				jsonMsg=JsonMsg.makeSuccess("删除成功", null);
			}else{
				jsonMsg=JsonMsg.makeFail("删除失败", null);
			}
		} catch (Exception e) {
			jsonMsg=JsonMsg.makeError("删除异常", null);
			e.printStackTrace();
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
	
}
