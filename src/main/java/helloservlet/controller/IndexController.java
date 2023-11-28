package helloservlet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.service.LoginService;
import helloservlet.service.UserService;

@WebServlet(name = "IndexController", urlPatterns = {"/index", "/"})
public class IndexController extends HttpServlet {
	
	private LoginService loginService = new LoginService();
//	private UserService userService =new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
//		resp.sendRedirect("/helloservlet/login");
		
//		System.out.println(req.getRealPath(getServletInfo()));
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doPost(req, resp);
		
		// Buoc 1: Nhận tham số người dùng truyền lên
				String email = req.getParameter("email");
				String password = req.getParameter("password");
				String remember = req.getParameter("remember");
				
				boolean isSuccess = loginService.checkLogin(email, password, remember, resp);
				
				
				if(isSuccess) {
					req.getRequestDispatcher("index.jsp").forward(req, resp);
					
				}
				else
				{
					System.out.println("chua dang nhap ma doi log in!");
					resp.sendRedirect("/helloservlet/login");
					
				}
				
				System.out.println("kiem tra login " + isSuccess);

	}
}
