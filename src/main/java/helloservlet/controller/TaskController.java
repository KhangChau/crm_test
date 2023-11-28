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

import org.apache.catalina.User;
import org.apache.catalina.manager.StatusManagerServlet;

import helloservlet.entity.JobsEntity;
import helloservlet.entity.RoleEntity;
import helloservlet.entity.StatusEntity;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;
import helloservlet.entity.UserTableEntity;
import helloservlet.repository.StatusRepository;
import helloservlet.service.JobsService;
import helloservlet.service.TaskService;
import helloservlet.service.UserService;

@WebServlet( name = "TaskController", urlPatterns = {"/task-table","/task-add","/task-update", "/task-delete"})
public class TaskController extends HttpServlet{
	private TaskService taskService = new TaskService();
	private StatusRepository statusRepository = new StatusRepository();
	private UserService userService = new UserService();
	private JobsService jobsService = new JobsService();
	int defaultIdStatus = 1;
	int currId = -1;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = req.getServletPath();
		System.out.printf("kiem tra path: %s\n", path);
		
		if(path.equals("/task-table")) {
			doGetTaskTable(req, resp);
		}
		else if(path.equals("/task-add")) {
			doGetTaskAdd(req, resp);
		}
		else if(path.equals("/task-update")) {
			doGetTaskUpdate(req, resp);
		}
		else if(path.equals("/task-delete")) {
			doGetTaskDelete(req, resp);
		}
		
		
//		req.getRequestDispatcher("task-table.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		
		String path = req.getServletPath();
		
		if(path.equals("/task-add")) {
			doPostTaskAdd(req, resp);
		}
		else if(path.equals("/task-update")) {
			doPostTaskUpdate(req, resp);
		}
	
	}
	
	private void doGetTaskTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<TaskEntity> list = taskService.findAllTask();
		
		req.setAttribute("listTask", list);
		
		//jstl library -> ho~ tro nhung doan lenh loop/if-else -> tra ve html
		
		
		req.getRequestDispatcher("task-table.jsp").forward(req, resp);
//		System.out.println("getTaskTable controller check 2");
	}
	private void doGetTaskAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserEntity> listUser = userService.findAllIdAndNameUser();
		List<JobsEntity> listJob = jobsService.getAllJobs();
		//select-option for role
		req.setAttribute("listUser", listUser);
		req.setAttribute("listJob", listJob);
		
		req.getRequestDispatcher("task-add.jsp").forward(req, resp);
	}
	private void doGetTaskUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {				
		List<UserEntity> listUser = userService.findAllIdAndNameUser();
		List<JobsEntity> listJob = jobsService.getAllJobs();
		List<StatusEntity> listStatus = statusRepository.findAll();
		//select-option for role
		req.setAttribute("listUser", listUser);
		req.setAttribute("listJob", listJob);
		req.setAttribute("listStatus", listStatus);
		
		int id_Update = Integer.parseInt(req.getParameter("id"));
		
		System.out.println("kiem tra id_update gui tu phuong thuc GET: " +id_Update);
		
		TaskEntity taskQuery = taskService.getTaskById(id_Update);

		
		req.setAttribute("idJob", taskQuery.getIdJob());
		req.setAttribute("name", taskQuery.getName());
		req.setAttribute("idUser", taskQuery.getIdUser());
		req.setAttribute("startDate", taskQuery.getStringStartDate());
		req.setAttribute("endDate", taskQuery.getStringEndDate());
		req.setAttribute("idStatus", taskQuery.getIdStatus());
//		req.setAttribute("id", id_Update); -> ko truyen user_id can` update nhu v duoc, vi method post chi nhan tham so tu the form file .jsp
		//->xu ly nhu ben duoi
		this.currId = id_Update; 
		req.getRequestDispatcher("task-update.jsp").forward(req, resp);
	}
	private void doGetTaskDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int id_delete = Integer.parseInt(req.getParameter("id"));
		taskService.delete(id_delete);
		
		resp.sendRedirect(req.getContextPath()+"/task-table");
//		System.out.println("getTaskTable controller check 2");
	}
	
	private void doPostTaskAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		int idJob = Integer.parseInt(req.getParameter("idJob"));
		int idUser = Integer.parseInt(req.getParameter("idUser"));
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		int idStatus = defaultIdStatus;
		
		//----------------------chữa lỗi UTF-8------------------
		byte[] bytes1 = name.getBytes(StandardCharsets.ISO_8859_1);
		name = new String(bytes1, StandardCharsets.UTF_8);
		byte[] bytes2 = startDate.getBytes(StandardCharsets.ISO_8859_1);
		startDate = new String(bytes2, StandardCharsets.UTF_8);
		byte[] bytes3 = endDate.getBytes(StandardCharsets.ISO_8859_1);
		endDate = new String(bytes3, StandardCharsets.UTF_8);
		//-------------------------------------------------------
		System.out.println("check roleName and description: " + name + "_" + idJob + "_" + idUser + "_" + startDate + "_" + endDate);
		
		taskService.insert(name, startDate, endDate, idUser, idJob, idStatus);
		
		req.getRequestDispatcher("task-add.jsp").forward(req, resp);
	}
	private void doPostTaskUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("name");
		int idJob = Integer.parseInt(req.getParameter("idJob"));
		int idUser = Integer.parseInt(req.getParameter("idUser"));
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		int idStatus = Integer.parseInt(req.getParameter("idStatus"));
		
		//----------------------chữa lỗi UTF-8------------------
		byte[] bytes1 = name.getBytes(StandardCharsets.ISO_8859_1);
		name = new String(bytes1, StandardCharsets.UTF_8);
		byte[] bytes2 = startDate.getBytes(StandardCharsets.ISO_8859_1);
		startDate = new String(bytes2, StandardCharsets.UTF_8);
		byte[] bytes3 = endDate.getBytes(StandardCharsets.ISO_8859_1);
		endDate = new String(bytes3, StandardCharsets.UTF_8);
		//-------------------------------------------------------
		System.out.println("check roleName and description: " + name + "_" + idJob + "_" + idUser + "_" + startDate + "_" + endDate);
		
		taskService.update(this.currId, name, startDate, endDate, idUser, idJob, idStatus);

		//reset currId
		int tempId = this.currId; // dung de reDirect link
		this.currId = -1;
		
		resp.sendRedirect(req.getContextPath()+"/task-update?id=" + tempId);
	}
}
