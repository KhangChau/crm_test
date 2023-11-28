//package helloservlet.Filter;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
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
//import helloservlet.config.MysqlConfig;
//import helloservlet.entity.UserEntity;
//												//Link se kich hoat filter
//@WebFilter(filterName = "authenFilter", urlPatterns = {
//		"/index"
////		",/role-table", "/role-add", "/role-delete", "/role-update",
////		"/user-table", "/user-add", "/user-update", "/user-delete", "/user-details",
////		"/task-table","/task-add","/task-update", "/task-delete",
////		"/profile","/profile-edit",
////		// ~~~~~~]/groupwork -> /jobs[~~~~~~~
////		"/jobs-table", "/jobs-add", "/jobs-update", "/jobs-delete", "/jobs-details"
//		}) 
//		
//
//public class AuthenticationFilter implements Filter {
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		// TODO Auto-generated method stub
//		System.out.println("da kich hoat filter");
//		HttpServletRequest req = (HttpServletRequest)request;
//		HttpServletResponse resp = (HttpServletResponse) response;
//		
//		String email = "";
//		String password = "";
//		String contextPath = req.getContextPath();
//		
//		//lay danh sach cookie nguoi dung gui len thong qua request
//		Cookie[] cookies = req.getCookies();
//		for(Cookie cookie : cookies) {
//			if(cookie.getName().equals("email")) {
//				email = cookie.getValue();
//			}
//			if(cookie.getName().equals("password")) {
//				password = cookie.getValue();
//			}
//		}
//		
//		//trim() : ""      abc    " -> "abc"
//		// loai bo khoang trong trong gia tri cua chuoi
//		if (email.trim().length() > 0 && password.trim().length() > 0) {
//			//da dang nhap roi
//			//cho phep di vap link ma nguoi dung dang goi ma kich hoat filter
//			
//			//Buoc 2: Chuẩn bị câu query (truy vấn)
////			String query = "SELECT *\r\n"
////					+ " FROM users u \r\n"
////					+ " WHERE u.email = '"+email+"' AND u.password = '"+password+"'";
//			String query = "SELECT *\r\n"
//					+ " FROM users u \r\n"
//					+ " WHERE u.email = '" +email+"' AND u.password = '" + password + "';";
//			
//			//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
//			Connection connection = MysqlConfig.getConnection();
//			
//			try {
//				//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
//				PreparedStatement preparedStatement = connection.prepareStatement(query);
//				//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
////				preparedStatement.setString(1, email);
////				preparedStatement.setString(2, password);
//				
//				//Buoc 5: thông báo cho CSDL biết và thực thi câu query
//				//có 2 cách thực thi:
//				//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
//				//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
//				ResultSet resultSet = preparedStatement.executeQuery();
//				List<UserEntity> listUser = new ArrayList<UserEntity>();
//				
//				//Buoc 6: Duyệt từng dòng dữ liệu query được và gán vào trong List<UserEntitty>
//				while(resultSet.next()) {
//					UserEntity entity = new UserEntity();
//					entity.setId(resultSet.getInt("id"));
//					entity.setFullname(resultSet.getString("fullname"));
//					
//					listUser.add(entity);
//				} 
//				
//				if(listUser.size() > 0) {
//					//da dang nhap tu truoc roi
//					System.out.println("cookie: email("+email+") + password("+password+") co trong valid trong dbs\n");
////					System.out.println(listUser.get(0).getPassword());
//					System.out.println(listUser.get(0).getPassword());
//					chain.doFilter(request, response);							
//				}
//				else {
//					//chua dang nhap
//					System.out.println("cookie fail kiem tra cookie cho email("+email+") va password("+password+")\n"); 
//					resp.sendRedirect(contextPath + "/login");
//				}
//			}catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////			System.out.println("zzzzz\n"); 
////			chain.doFilter(request, response);
//		}
//		else {
//			System.out.println("chua co login nen ko the vao add-on-> redirect den logn page"); 
//			resp.sendRedirect(contextPath + "/login");
//		}
//		
//		
//	}
//	
//}
