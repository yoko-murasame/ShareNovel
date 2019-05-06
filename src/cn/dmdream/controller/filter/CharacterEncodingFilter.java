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

public class CharacterEncodingFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("过滤器开始销毁");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request = (HttpServletRequest)request;
		response = (HttpServletResponse)response;
		//执行过滤
		System.out.println("将所有请求设置UTF8编码后放行");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//通过过滤器将请求继续分发
		chain.doFilter(request, response);
		//过滤完返回
		response.setContentType("application/json;charset=utf-8");
		System.out.println("已将返回文本类型设置成json");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("过滤器开始初始化");
	}
}
