package cn.dmdream.controller;

import java.io.IOException;
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

public class FindPassword extends BaseServlet {
	private SnUserService snUserService=new SnUserServiceImpl();
	ObjectMapper objectMapper = new ObjectMapper();
	JsonMsg jsonMsg = null;
	
	public String verify(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		String url="set.html";
		String inputInner=request.getParameter("inputInner");
		SnUser user=null;
		
		List<SnUser> list=snUserService.findAll();
		for(SnUser snUser:list){
			if(snUser.getUserUsername().equals(inputInner)||
				snUser.getUserEmail().equals(inputInner)||
				snUser.getUserPhone().equals(inputInner)){
				user=snUser;
			}
		}
		
		if(user!=null){
			
		}
			
		
		return url;
	}
	
	
}
