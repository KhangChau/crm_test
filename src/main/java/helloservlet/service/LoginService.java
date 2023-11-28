package helloservlet.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import helloservlet.entity.UserEntity;
import helloservlet.repository.UserRepository;

public class LoginService {
	private UserRepository userRepository = new UserRepository();
	
	public boolean autoLogin(HttpServletRequest req) {
		Cookie[] listCookie = req.getCookies();
		if(listCookie == null) {
			return false;
		}
		String email = "";
		String password = "";
		
		//duyet qua tung cookie ben trong List
		for(Cookie cookie : listCookie) {
			//Kiem tra ten cookie co phai la username khong
			if(cookie.getName().equals("email")) {
				//lay gia tri cua cookie username
				email = cookie.getValue();
				System.out.println("Gia tri " + cookie.getValue());
			}
			if(cookie.getName().equals("password")) {
				password = cookie.getValue();
				System.out.println("Gia tri " + cookie.getValue());
			}
		}
		
		req.setAttribute("email", email);
		req.setAttribute("password", password);
		return true;
	}
	
	public boolean checkLogin(String email, String password, String remember, HttpServletResponse resp) {
		
		List<UserEntity> list = userRepository.findByEmailAndPassword(email, password);
		boolean isSuccess = list.size() > 0;
		
		//nếu remember != null thì người dùng coc check vào lưu tài khoản
		if(isSuccess == true && remember != null) {// nguoi dung co tick vao checkbox luu tai khoan
//			String email = req.getParameter("email");
//			String password = req.getParameter("password");
			
			Cookie cookieEmail = new Cookie("email", email);
			cookieEmail.setMaxAge(5*60*60); // ko set age thi` cookie bat tu
			
			Cookie cookiePassword = new Cookie("password", password);
			cookiePassword.setMaxAge(5*60*60); // ko set age thi` cookie bat tu
			
			resp.addCookie(cookieEmail);
			resp.addCookie(cookiePassword);
		}
		
		return isSuccess;
	}
}
