package helloservlet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.service.LoginService;

@WebServlet (name = "loginController", urlPatterns = {"/login"})
public class LoginCotroller extends HttpServlet{
	
	private LoginService loginService = new LoginService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		loginService.autoLogin(req);
		System.out.println("Kiem tra");
		req.getRequestDispatcher("login.jsp").forward(req, resp);
				
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
		doPostLogin(req, resp);
	}
	
	@SuppressWarnings("unused")
	private void doPostLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		
		// Buoc 1: Nhận tham số người dùng truyền lên
		 String email = req.getParameter("email");
		 String password = req.getParameter("password");
		String remember = req.getParameter("remember");
		
		System.out.println("kiem tra remember : " + remember);
		
		String contextPath = req.getContextPath();
		
		boolean isSuccess = loginService.checkLogin(email, password, remember, resp);
		
		
		if(isSuccess) {
			resp.sendRedirect(contextPath + "/index");
//			req.getRequestDispatcher("index.jsp").forward(req, resp);
		}
		else
		{
//			req.getRequestDispatcher("login.jsp").forward(req, resp);
			resp.sendRedirect(contextPath + "/login");
		}
		
		//System.out.println("kiem tra login " + isSuccess);
	}
}
