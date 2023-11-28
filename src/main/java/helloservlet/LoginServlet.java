//package helloservlet;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.swing.text.html.parser.Entity;
//
//import com.mysql.cj.protocol.Resultset;
//
//import helloservlet.config.MysqlConfig;
//import helloservlet.entity.UserEntity;
//
//@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
//public class LoginServlet extends HttpServlet{
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		
////		HttpSession session = req.getSession();
////		String cysoft = (String) session.getAttribute("Cybersoft");
////		System.out.println("Session " + cysoft);
//		
////		Cookie cookie = new Cookie("username", "nguyenvana");
////		cookie.setMaxAge(5*60*60); // ko set age thi` cookie bat tu
////		
////		Cookie cookiePassword = new Cookie("password", "123456");
////		
////		resp.addCookie(cookie);
////		resp.addCookie(cookiePassword);
//		
//		//lay danh sach cookie tu reques cua nguoi dung
////		Cookie[] listCookie = req.getCookies();
////		//duyet qua tung cookie ben trong List
////		for(Cookie cookie : listCookie) {
////			//Kiem tra ten cookie co phai la username khong
////			if(cookie.getName().equals("username")) {
////				//lay gia tri cua cookie username
////				System.out.println("Gia tri " + cookie.getValue());
////			}
////			if(cookie.getName().equals("password")) {
////				System.out.println("Gia tri " + cookie.getValue());
////			}
////		}
//		//lay danh sach cookie tu reques cua nguoi dung
//		Cookie[] listCookie = req.getCookies();
//		String email = "";
//		String password = "";
//		
//		//duyet qua tung cookie ben trong List
//		for(Cookie cookie : listCookie) {
//			//Kiem tra ten cookie co phai la username khong
//			if(cookie.getName().equals("email")) {
//				//lay gia tri cua cookie username
//				email = cookie.getValue();
////				System.out.println("Gia tri " + cookie.getValue());
//			}
//			if(cookie.getName().equals("password")) {
//				password = cookie.getValue();
////				System.out.println("Gia tri " + cookie.getValue());
//			}
//		}
//		
//		req.setAttribute("email", email);
//		req.setAttribute("password", password);
//
//		
//		
//		
////		super.doGet(req, resp);
//		//Hiển thị nọi dug file html khi người dùng gọi link/login
//		req.getRequestDispatcher("login.jsp").forward(req, resp);
//	}
//	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		// TODO Auto-generated method stub
////		super.doPost(req, resp);	
//		// Buoc 1: Nhận tham số người dùng truyền lên
//		String email = req.getParameter("email");
//		String password = req.getParameter("password");
//		String remember = req.getParameter("remember");
//		System.out.println("kiem tra checkbox " + remember);
////		System.out.printf("Kie tra POST %s %s\n", email, password);
//		
//		//Buoc 2: Chuẩn bị câu query (truy vấn)
////		String query = "SELECT *\r\n"
////				+ " FROM users u \r\n"
////				+ " WHERE u.email = '"+email+"' AND u.password = '"+password+"'";
//		String query = "SELECT *\r\n"
//				+ " FROM users u \r\n"
//				+ " WHERE u.email = ? AND u.password = ?";
//		
//		//Buoc 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PrepareStatement
//		Connection connection = MysqlConfig.getConnection();
//		
//		try {
//			//Buoc 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PrepareStatement
//			PreparedStatement preparedStatement = connection.prepareStatement(query);
//			//Gán giá trị tham số dấu chấm hỏi (?) bên trong câu query
//			preparedStatement.setString(1, email);
//			preparedStatement.setString(2, password);
//			
//			//Buoc 5: thông báo cho CSDL biết và thực thi câu query
//			//có 2 cách thực thi:
//			//-executeQuery: Giành cho câu truy vấn SELECT -> luôn trả ra ResultSet
//			//-executeUpdate: Giành cho các câu truy vân còn lại (INSERT, UPDATE, DELETE), trừ SELECT
//			ResultSet resultSet = preparedStatement.executeQuery();
//			List<UserEntity> listUser = new ArrayList<UserEntity>();
//			
//			//Buoc 6: Duyệt từng dòng dữ liệu query được và gán vào trong List<UserEntitty>
//			while(resultSet.next()) {
//				UserEntity entity = new UserEntity();
//				entity.setId(resultSet.getInt("id"));
//				entity.setFullname(resultSet.getString("fullname"));
//				
//				listUser.add(entity);
//			} 
//			
//			//Buoc 7: Kiểm tra đăng nhập bằng cách kiểm tra xem listUser có giá trị hay không
//			if(listUser.size() > 0) {
//				if(remember != null) {// nguoi dung co tick vao checkbox luu tai khoan
////					String email = req.getParameter("email");
////					String password = req.getParameter("password");
//					
//					Cookie cookieEmail = new Cookie("email", email);
//					cookieEmail.setMaxAge(5*60*60); // ko set age thi` cookie bat tu
//					
//					Cookie cookiePassword = new Cookie("password", password);
//					cookiePassword.setMaxAge(5*60*60); // ko set age thi` cookie bat tu
//					
//					resp.addCookie(cookieEmail);
//					resp.addCookie(cookiePassword);
//				}
//				
//				System.out.println("dang nhap thanh cong");
//				req.getRequestDispatcher("profile.html").forward(req, resp);
//			} else {
//				System.out.println("dang nhap that bai");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
