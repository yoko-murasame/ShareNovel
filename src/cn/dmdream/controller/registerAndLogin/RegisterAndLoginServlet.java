package cn.dmdream.controller.registerAndLogin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnUserService;
import cn.dmdream.service.impl.SnUserServiceImpl;

public class RegisterAndLoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public RegisterAndLoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOExceptionphone
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		//读取参数
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String nickname=request.getParameter("nickname");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		//实例化SnUser
		SnUser snUser = new SnUser();
		snUser.setUserUsername(username);
		snUser.setUserPassword(password);
		snUser.setUserNickname(nickname);
		snUser.setUserEmail(email);
		snUser.setUserPhone(phone);
		//实例化SnUserService
		SnUserService snUserService=new SnUserServiceImpl();
		//执行注册（插入）
		boolean isok = snUserService.save(snUser);
		
		String msg="";
		String url="";
		if(isok){
			msg="恭喜你，注册成功！";
			//跳转地址
			String jumpUrl="login.jsp";
			request.setAttribute("jumpUrl", jumpUrl);
			url="success.jsp";
		}else {
			msg="对不起，注册失败，请重试！";
			url="fail.jsp";
		}
		//把消息存入到msg，放到request中。
		request.setAttribute("msg", msg);
		//进行跳转
		request.getRequestDispatcher(url).forward(request, response);
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
