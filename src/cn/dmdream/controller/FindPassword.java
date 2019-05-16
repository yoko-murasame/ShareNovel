package cn.dmdream.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.List;
import java.util.Random;

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
				jsonMsg=JsonMsg.makeSuccess("用户信息已找到", user);
				HttpSession session = request.getSession();
				session.setAttribute("userInfo", user);
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
	
	public String sendEmail(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		System.out.println("sendEmail->username:"+username);
		
		String emailMsg=getFixLenthString(10);
		System.out.println(emailMsg);
		
		try {
			MailUtils.sendConfirmMail(email, emailMsg);
			HttpSession session=request.getSession();
			session.setAttribute("password", password);
			session.setAttribute("username", username);
			session.setAttribute("code", emailMsg);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	
    /* 
     * 返回长度为【strLength】的随机数，在前面补0 
     */  
    private static String getFixLenthString(int strLength) {  
          
        Random rm = new Random();  
          
        // 获得随机数  
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
      
        // 将获得的获得随机数转化为字符串  
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数  
        return fixLenthString.substring(2, strLength + 2);  
    }  
    
    
    public String confirm(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
    	HttpSession session=request.getSession();
    	try {
			String username=(String) session.getAttribute("username");
			String password=(String) session.getAttribute("password");
			String codeOri=(String) session.getAttribute("code");
			
			System.out.println("username:"+username+", password:"+password+", codeOri"+codeOri);
			
			String codeRec=request.getParameter("code");
			System.out.println("codeRec:"+codeRec);
			
			if(codeOri.equals(codeRec)){
				SnUser snUser=snUserService.findByUsername(username);
				System.out.println(snUser);
				snUser.setUserPassword(password);
				boolean isok=snUserService.update(snUser);
				if(isok){
					jsonMsg=JsonMsg.makeSuccess("密码修改成功", null);
					session.invalidate();
				}else{
					jsonMsg=JsonMsg.makeFail("密码修改失败", null);
				}
			}
		} catch (Exception e) {
			jsonMsg=JsonMsg.makeError("密码修改异常", null);
			e.printStackTrace();
		}
    	
    	//序列化json
		String writeValueAsString = objectMapper.writeValueAsString(jsonMsg);
		//保存讯息到新的session
		HttpSession session2=request.getSession();
		session2.setAttribute("result", writeValueAsString);
		
    	
    	return "findPassword/confirm.html";
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
	
}
