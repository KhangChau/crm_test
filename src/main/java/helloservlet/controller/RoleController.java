package helloservlet.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.entity.RoleEntity;
import helloservlet.service.RoleService;


@WebServlet (name = "roleAddServlet", urlPatterns = {"/role-add", "/role-table", "/role-delete"})
public class RoleController extends HttpServlet{
	private RoleService roleService = new RoleService();
	
	//role-add.html -> role-add.jsp
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		
		String path = req.getServletPath();
		System.out.printf("kiem tra path: %s\n", path);
		
		if(path.equals("/role-add")) {
//			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			doGetRoleAdd(req, resp);
		}
		else if (path.equals("/role-table")) {
//			req.getRequestDispatcher("role-table.html").forward(req, resp);
			doGetRoleTable(req, resp);
		}
		else if (path.equals("/role-delete")) {
			try {
				doGetRoleDelete(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			
		}
		
//		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doPost(req, resp);
		this.doPostRoleAdd(req, resp);
	}
	
	private void doGetRoleAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	}
	private void doGetRoleTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("getRoleTable controller check 1");
		List<RoleEntity> list = roleService.getAllRole();
		
		req.setAttribute("listRole", list);
		
		//jstl library -> ho~ tro nhung doan lenh loop/if-else -> tra ve html
		
		
		req.getRequestDispatcher("role-table.jsp").forward(req, resp);
		System.out.println("getRoleTable controller check 2");
	}
	private void doGetRoleDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		int id = Integer.parseInt(req.getParameter("id"));
		boolean deleteSuccess = roleService.deleteRole(id);
		if(deleteSuccess) {
			System.out.println("kiem tra xoa thanh cong!");
		}else {
			System.out.println("kiem tra xoa that bai!");
		}
		resp.sendRedirect(req.getContextPath() + "/role-table");
	}
	
	private void doPostRoleAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		
		//----------------------chữa lỗi UTF-8------------------
		byte[] bytes1 = name.getBytes(StandardCharsets.ISO_8859_1);
		name = new String(bytes1, StandardCharsets.UTF_8);
		byte[] bytes2 = description.getBytes(StandardCharsets.ISO_8859_1);
		description = new String(bytes2, StandardCharsets.UTF_8);
		//-------------------------------------------------------
		System.out.println("check roleName and description: " + name + "_" + description);
		
		roleService.insert(name, description);
		
		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	}
}
