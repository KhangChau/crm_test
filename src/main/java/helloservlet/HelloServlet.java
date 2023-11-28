package helloservlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

//import javax.servlet.http.HttpServlet;



@WebServlet(name = "helloServlet", urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet{ 
	//GET, POST => Methods cách thức clients gọi đường dẫn
	/**
	 * Nhận tham số thừ client gửi lên
	 * 	- Giữa GET và POST cách nhận tham số sẽ giống nhau
	 * 	- GET : Tham số sẽ truyền trực tiếp trên URL --> ?tenThamSo1=giaTri&tenThamSo2=giaTri...
	 * 	- POST : Tham số sẽ truyền thông qua thẻ FORM của HTML hoặc các ứng dụng, 
	 * 	  code gọi link với phương thức POST (Tham số sẽ trường truyền ngầm)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// sẽ thực hiện khi người dùng gọi đường dẫn với phương thức GET
		//super.doGet(req, resp); -> xoa di!
		
//		PrintWriter printWriter = resp.getWriter();
//		printWriter.write("Hello Servlet");
//		printWriter.close();
		
		//lấy giá trị của tham số có thên là username và age
		String username = req.getParameter("username");
//		int age = Integer.parseInt(req.getParameter("age"));
		
//		System.out.printf("Kiem tra %s - %s", username, age);
		System.out.printf("Kiem tra %s -", username);
		
		HttpSession session = req.getSession();
		session.setAttribute("Cybersoft", "Hello Session");
		
		req.getRequestDispatcher("hello.jsp").forward(req, resp);
	}
	
	
}
