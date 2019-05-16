package cn.dmdream.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnUserService;
import cn.dmdream.service.impl.SnUserServiceImpl;
import cn.dmdream.utils.EmailUitl;
import cn.dmdream.utils.MailUtils;
import cn.dmdream.vo.JsonMsg;

public class FindPassword extends BaseServlet {
	private SnUserService snUserService=new SnUserServiceImpl();
	ObjectMapper objectMapper = new ObjectMapper();
	JsonMsg jsonMsg = null;
	
	public String ckEx(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		System.out.println("开始检查");
		
		String inputInner=request.getParameter("inputInner");
		System.out.println("inputInner:"+inputInner);
		List<SnUser> list=snUserService.findAll();
		
		SnUser user=null;
		
		try {
			for(SnUser snUser:list){
				if(inputInner.equals(snUser.getUserUsername())||
					inputInner.equals(snUser.getUserEmail())||
					inputInner.equals(snUser.getUserPhone())){
					
					user=snUser;
					break;
				}
			}
			
			if(user!=null){
				//发送激活邮件
				UUID uuid = UUID.randomUUID();
				String avticeCode = uuid.toString().replaceAll("-", "");
				//保存激活码到数据库
				user.setUserCode(avticeCode);
				//更新用户信息
				snUserService.update(user);
				String url = "http://localhost:8080/ShareNovel/findPassword.do?method=verifyCode";
				url = url + "&userId=" + user.getUserId() + "&userCode="+avticeCode;
				//发送邮件
				int isok = EmailUitl.sendFindPasswordEmail(user.getUserUsername(), user.getUserEmail(), url);
				if(isok == 1){//发送成功
					jsonMsg = JsonMsg.makeSuccess("用户信息已找到,密码重置邮件已发送!", null);
				}else{
					jsonMsg=JsonMsg.makeSuccess("用户信息已找到,邮件发送失败!", null);
				}
			}else{
				jsonMsg=JsonMsg.makeFail("用户不存在", null);
			}
		} catch (Exception e) {
			jsonMsg=JsonMsg.makeError("用户信息检查异常", null);
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
	
	public String setInit(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		
		HttpSession session=request.getSession();
		SnUser snUser=(SnUser) session.getAttribute("userInfo");
		
		try {
			if(snUser!=null){
				jsonMsg=JsonMsg.makeSuccess("获取用户信息成功", snUser);
				session.invalidate();
			}else{
				jsonMsg=JsonMsg.makeFail("用户会话已过期", null);
			}
		} catch (Exception e) {
			jsonMsg=JsonMsg.makeError("用户信息获取异常", null);
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
	
    
    public String confirm(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
    	
    	try {
    		String username=request.getParameter("username");
    		String password=request.getParameter("password");
    		System.out.println("新的用户名是:"+username);
    		System.out.println("新的密码是:"+password);
			
    		SnUser snUser=snUserService.findByUsername(username);
			System.out.println(snUser);
			snUser.setUserPassword(password);
			boolean isok=snUserService.update(snUser);
			if(isok){
				jsonMsg=JsonMsg.makeSuccess("密码修改成功", null);
			}else{
				jsonMsg=JsonMsg.makeFail("密码修改失败", null);
			}
    		
		} catch (Exception e) {
			jsonMsg=JsonMsg.makeError("密码修改异常", null);
			e.printStackTrace();
		}
		//序列化json
		String writeValueAsString = objectMapper.writeValueAsString(jsonMsg);
    	response.getWriter().write(writeValueAsString);
    	return null;
    }
    
    public String conInit(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
    	HttpSession session=request.getSession();
    	String result=(String) session.getAttribute("result");
    	System.out.println("session->result:"+result);
    	//返回
		response.setContentType("text/html;charset=utf-8");  
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		
		if(result!=null){
			out.print(result);
		}else {
			out.print("error");
		}
		session.invalidate();
		
		out.flush();
		out.close();
    	
    	return null;
    }

    /**
     * 找回密码邮件的校验
     */
	public String verifyCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		String userId = req.getParameter("userId");
		String userCode = req.getParameter("userCode");
		
		try {
			SnUser userToRePass = snUserService.findById(Integer.parseInt(userId));
			String code = userToRePass.getUserCode();
			System.out.println("来自数据库的code："+code);
			System.out.println("来自邮件的code："+ userCode);
			if(code.equals(userCode)){
				//校验成功
				//放到session中
				userToRePass.setUserPassword(null);
				req.getSession().setAttribute("userInfo", userToRePass);

				return "/findPassword/reset.html";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/findPassword/failPage.html";
	
	}
    
	
}
