//package helloservlet.Filter;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import helloservlet.entity.RoleEntity;
//import helloservlet.entity.UserEntity;
//import helloservlet.repository.RoleRepository;
//import helloservlet.repository.UserRepository;
//
////role_manager
//@WebFilter(filterName = "UserAuthurFilter", urlPatterns = {
//		
////		//ROLE_ADMIN
////		"/role-add", "/role-table", "/role-delete",
////		"/user-add","/user-update", "/user-delete",
////		"/jobs-table","/jobs-add","/jobs-update", "/jobs-delete",
////		
////		//ROLE_MANAGER
////		"/user-table",
////		"/task-table","/task-add","/task-update", "/task-delete",		
////		"/jobs-details", "/task-details",
//		
//		//ROLE_USER
//		"/profile","/profile-edit",
//		"/index", "/", 
//		// ~~~~~~]/groupwork -> /jobs[~~~~~~~
//		
//})
//public class UserAuthurFilter implements Filter {
//	
//	UserRepository userRepository = new UserRepository();
//	RoleRepository roleRepository = new RoleRepository();
//	
//	@Override	
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		// TODO Auto-generated method stub
//
//				System.out.println("da kich hoat User filter");
//				HttpServletRequest req = (HttpServletRequest)request;
//				HttpServletResponse resp = (HttpServletResponse) response;
//				
//				String email = "";
//				String password = "";
//				String contextPath = req.getContextPath();
//
//				//lay danh sach cookie nguoi dung gui len thong qua request
//				Cookie[] cookies = req.getCookies();
//				for(Cookie cookie : cookies) {
//					if(cookie.getName().equals("email")) {
//						email = cookie.getValue();
//					}
//					if(cookie.getName().equals("password")) {
//						password = cookie.getValue();
//					}
//				}
//				if (email.trim().length() > 0 && password.trim().length() > 0) {
//					
//					List<UserEntity> userList = userRepository.findByEmailAndPassword(email, password);
//					
//					if(userList.size() > 0) {
//						chain.doFilter(request, response);
//
//					}
//					else {
//						// dang nhap that bai
//						System.out.println("dang nhap that bai");
//						resp.sendRedirect(contextPath + "/login");
//					}
//					
//				}
//				else {
//					//ko tim thay trong cookie email + password
//					System.out.println("ko tim thay trong cookie email + password");
//					resp.sendRedirect(contextPath + "/login");
//				}
//				
//				
//				
//	}
//}
