package cn.dmdream.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dmdream.entity.SnAdmin;

public class AdminAuthFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("管理员权限认证销毁");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {
			SnAdmin admin = (SnAdmin) httpRequest.getSession().getAttribute("admin");
			if(admin == null || admin.equals("")) throw new Exception();
			else chain.doFilter(httpRequest, httpResponse);
		} catch (Exception e) {
			httpRequest.getRequestDispatcher("/admin.do").forward(httpRequest, httpResponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("管理员权限认证启动");
	}

}
