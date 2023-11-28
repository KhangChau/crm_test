package helloservlet.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.entity.RoleEntity;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;
import helloservlet.entity.UserTableEntity;
import helloservlet.service.RoleService;
import helloservlet.service.TaskService;
import helloservlet.service.UserService;

@WebServlet (name = "userController", urlPatterns = {"/user-table", "/user-add", "/user-update", "/user-delete", "/user-details"})
public class UserController extends HttpServlet {
	
	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();
	
	private int currId = -1; //for user_id (update)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		
		String path = req.getServletPath();
		System.out.printf("kiem tra path: %s\n", path);
		
		if(path.equals("/user-table")) {
//			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			doGetUserTable(req, resp);
		}
		else if(path.equals("/user-add")) {
//			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			doGetUserAdd(req, resp);
		}
		else if(path.equals("/user-update")) {
//			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			doGetUserUpdate(req, resp);
		}
		else if(path.equals("/user-delete")) {
//			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			try {
				doGetUserDelete(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(path.equals("/user-details")) {
//			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			doGetUserDetails(req, resp);
		}

		
//		req.getRequestDispatcher("user-table.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		
		String path = req.getServletPath();
		
		if(path.equals("/user-add")) {
			doPostUserAdd(req, resp);
		}
		else if(path.equals("/user-update")) {
			doPostUserUpdate(req, resp);
		}
		
	}

	private void doGetUserTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("getRoleTable controller check 1");
		List<UserTableEntity> list = userService.getAllIdAndEmailAndFullnameWithRole();
		
		req.setAttribute("listUser", list);
		
		//jstl library -> ho~ tro nhung doan lenh loop/if-else -> tra ve html
		
		
		req.getRequestDispatcher("user-table.jsp").forward(req, resp);
		System.out.println("getUserTable controller check 2");
	}
	private void doGetUserAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<RoleEntity> list = roleService.getAllRole();
		//select-option for role
		req.setAttribute("listRole", list);
		
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}
	private void doGetUserUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {				
		List<RoleEntity> list = roleService.getAllRole();
		//select-option for role
		req.setAttribute("listRole", list);
		
		int id_Update = Integer.parseInt(req.getParameter("id"));
		System.out.println("kiem tra id_update gui tu phuong thuc GET: " +id_Update);
		
		UserTableEntity userQuery = userService.getUserById(id_Update);
		System.out.printf("check user query by id,userUpdateGet: %s_%s_%s_%s\n",
				userQuery.getFullname(),
				userQuery.getEmail(),
				userQuery.getPassword(),
				userQuery.getRole_id());
		
		req.setAttribute("fullname", userQuery.getFullname());
		req.setAttribute("email", userQuery.getEmail());
		req.setAttribute("password", userQuery.getPassword());
		req.setAttribute("role_id", userQuery.getRole_id());
//		req.setAttribute("id", id_Update); -> ko truyen user_id can` update nhu v duoc, vi method post chi nhan tham so tu the form file .jsp
		//->xu ly nhu ben duoi
		this.currId = id_Update; 
		req.getRequestDispatcher("user-update.jsp").forward(req, resp);
	}
	private void doGetUserDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {				
		int id = Integer.parseInt(req.getParameter("id"));
		boolean deleteSuccess = userService.deleteUser(id);
		if(deleteSuccess) {
			System.out.println("kiem tra user-delete, userId = " + req.getParameter("id") + " thanh cong");
		}else {
			System.out.println("kiem tra user-delete, userId = " + req.getParameter("id") + " that bai");
		}
		
		resp.sendRedirect(req.getContextPath()+ "/user-table");
	}
	private void doGetUserDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {				
		
		int currId = Integer.parseInt(req.getParameter("id"));
		
		//task with idStatus = 1;
		List<TaskEntity> taskList1 = userService.findAllTaskByUserIdAndStatusId(currId, 1);
		//task with idStatus = 2;
		List<TaskEntity> taskList2 = userService.findAllTaskByUserIdAndStatusId(currId, 2);
		//task with idStatus = 3;
		List<TaskEntity> taskList3 = userService.findAllTaskByUserIdAndStatusId(currId, 3);
		
		req.setAttribute("taskList1", taskList1);
		req.setAttribute("taskList2", taskList2);
		req.setAttribute("taskList3", taskList3);
		
		int taskPercentList[] = userService.calculateTaskPercent(currId);
		
		req.setAttribute("taskPercent1", taskPercentList[0]);
		req.setAttribute("taskPercent2", taskPercentList[1]);
		req.setAttribute("taskPercent3", taskPercentList[2]);
		System.out.println("task percent 1 2 3: " + taskPercentList[0] + taskPercentList[1] + taskPercentList[2]);
		UserTableEntity userEntity = userService.getUserById(currId);
		req.setAttribute("userEntity", userEntity);
		userEntity.getFullname();
		
		req.getRequestDispatcher("user-details.jsp").forward(req, resp);
	}
	
	
	private void doPostUserAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		int role_id = Integer.parseInt(req.getParameter("role_id"));
		
		//----------------------chữa lỗi UTF-8------------------
		byte[] bytes1 = fullname.getBytes(StandardCharsets.ISO_8859_1);
		fullname = new String(bytes1, StandardCharsets.UTF_8);
		
		byte[] bytes2 = email.getBytes(StandardCharsets.ISO_8859_1);
		email = new String(bytes2, StandardCharsets.UTF_8);
		
		byte[] bytes3 = password.getBytes(StandardCharsets.ISO_8859_1);
		password = new String(bytes3, StandardCharsets.UTF_8);
		
//		byte[] bytes4 = role_id.getBytes(StandardCharsets.ISO_8859_1);
//		role_id = new String(bytes4, StandardCharsets.UTF_8);
		//-------------------------------------------------------
		System.out.println("check roleName and description: " + fullname + "_" + email + "_" + password + "_" + role_id);
		
		userService.insert(fullname, email, password, role_id);
		
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}
	private void doPostUserUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		int role_id = Integer.parseInt(req.getParameter("role_id"));
		
//		int id = Integer.parseInt(req.getParameter("id")); --> post co nhan duoc id dau? the form trong file .jsp lam gi co id
		
		//----------------------chữa lỗi UTF-8------------------
		byte[] bytes1 = fullname.getBytes(StandardCharsets.ISO_8859_1);
		fullname = new String(bytes1, StandardCharsets.UTF_8);
		
		byte[] bytes2 = email.getBytes(StandardCharsets.ISO_8859_1);
		email = new String(bytes2, StandardCharsets.UTF_8);
		
		byte[] bytes3 = password.getBytes(StandardCharsets.ISO_8859_1);
		password = new String(bytes3, StandardCharsets.UTF_8);
		
//		byte[] bytes4 = role_id.getBytes(StandardCharsets.ISO_8859_1);
//		role_id = new String(bytes4, StandardCharsets.UTF_8);
		//-------------------------------------------------------
		System.out.println("check update user: " + fullname + "_" + email + "_" + password + "_" + role_id);
		
		int isUpdateSuccess = userService.update(fullname, email, password, role_id, this.currId);
		System.out.println("update user " + this.currId + "thanh cong!");
		
		//reset currId
		int tempId = this.currId; // dung de reDirect link
		this.currId = -1;
		
		resp.sendRedirect(req.getContextPath()+"/user-update?id=" + tempId);
	}
}
