package helloservlet.Filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.entity.RoleEntity;
import helloservlet.entity.UserEntity;
import helloservlet.repository.RoleRepository;
import helloservlet.repository.UserRepository;

//role_manager
@WebFilter(filterName = "UserAuthurFilter", urlPatterns = {
		
		//ROLE_ADMIN
		"/role-add", "/role-table", "/role-delete",
		"/user-add","/user-update", "/user-delete",
		"/jobs-add","/jobs-update", "/jobs-delete",
		
		//ROLE_MANAGER
		"/user-table", "/user-details",
		"/task-table","/task-add","/task-update", "/task-delete",
		"/jobs-table", "/jobs-details", 
		
		//ROLE_USER
		"/profile","/profile-edit",
		"/index", "/" 
		// ~~~~~~]/groupwork -> /jobs[~~~~~~~
		
})
public class AdminAuthurFilter implements Filter {
	UserRepository userRepository = new UserRepository();
	RoleRepository roleRepository = new RoleRepository();
	String[] listLinkManager = {
			//ROLE_MANAGER
			"/user-table",  "/user-details",
			"/task-table","/task-add","/task-update", "/task-delete",	 	
			"/jobs-table", "/jobs-details", 
			
			//ROLE_USER
			"/profile","/profile-edit",
			"/index", "/"
	};
	String[] listLinkUser = {
			//ROLE_USER
			"/profile","/profile-edit",
			"/index", "/"
	};
	
//	@Override	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
				System.out.println("da kich hoat filter");
				HttpServletRequest req = (HttpServletRequest)request;
				HttpServletResponse resp = (HttpServletResponse) response;
				System.out.println("link just accessed: " + req.getContextPath() +  req.getServletPath());
				
				String email = "";
				String password = "";
				String contextPath = req.getContextPath();

				//lay danh sach cookie nguoi dung gui len thong qua request
				Cookie[] cookies = req.getCookies();
				for(Cookie cookie : cookies) {
					if(cookie.getName().equals("email")) {
						email = cookie.getValue();
					}
					if(cookie.getName().equals("password")) {
						password = cookie.getValue();
					}
				}
				if (email.trim().length() > 0 && password.trim().length() > 0) {
					
					List<UserEntity> userList = userRepository.findByEmailAndPassword(email, password);
					
					if(userList.size() > 0) {
						UserEntity user = userList.get(0);

						int defaultAdminId = 1;
						int defaultManagerId = 2;
						int defaultUserId = 3;
						List<RoleEntity> listQueryRoleId = roleRepository.findRoleById(user.getRole_id());
						
						if(listQueryRoleId.isEmpty()) {
							System.out.println("ko tim thay role id");
							resp.sendRedirect(contextPath + "/index");
						}
						else {
							int queryId = listQueryRoleId.get(0).getId();
//							if(listQueryRoleId.get(0).getId() != defaultAdminId) {
//								System.out.println("ko phair role Admin");
//								resp.sendRedirect(contextPath + "/index");
//							}
							if( queryId == defaultAdminId){
								chain.doFilter(request, response);
							}
							else if(queryId == defaultManagerId){
								int size = listLinkManager.length;
								Boolean isSuccess = false;
								String requestPath = req.getServletPath();
								for(int i = 0; i < size; i++) {
									if(requestPath.equals(listLinkManager[i])) {
										chain.doFilter(request, response);
										isSuccess = true;
										break;
									}
								}
								if(!isSuccess) {
									System.out.println("ban khong duoc cap quyen!");
									resp.sendRedirect(contextPath + "/index");
								}
							}
							else if(queryId == defaultUserId){
								int size = listLinkUser.length;
								String requestPath = req.getServletPath();
								Boolean isSuccess = false;
								for(int i = 0; i < size; i++) {
									if(requestPath.equals(listLinkUser[i])) {
										chain.doFilter(request, response);
										isSuccess = true;
										break;
									}									
								}	
								if(!isSuccess) {
									System.out.println("ban khong duoc cap quyen!");
									resp.sendRedirect(contextPath + "/index");
								}
							}
						}
					}
					else {
						// dang nhap that bai
						System.out.println("dang nhap that bai");
						resp.sendRedirect(contextPath + "/login");
					}
					
				}
				else {
					//ko tim thay trong cookie email + password
					System.out.println("ko tim thay trong cookie email + password");
					resp.sendRedirect(contextPath + "/login");
				}
				
				
				
	}

}
