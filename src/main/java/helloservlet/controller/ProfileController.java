package helloservlet.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.entity.JobsEntity;
import helloservlet.entity.StatusEntity;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;
import helloservlet.repository.StatusRepository;
import helloservlet.repository.TaskRepository;
import helloservlet.service.ProfileService;

@WebServlet (name = "ProfileController", urlPatterns = {"/profile","/profile-edit"})
public class ProfileController extends HttpServlet{
	ProfileService profileService = new ProfileService();
	StatusRepository statusRepository = new StatusRepository();
	TaskRepository taskRepository = new TaskRepository();
	int currTaskId = -1;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		System.out.printf("kiem tra path: %s\n", path);
		
		if(path.equals("/profile")) {
			doGetProfile(req, resp);
		}
		else if(path.equals("/profile-edit")) {
			doGetProfileEdit(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if(path.equals("/profile-edit")) {
			doPostProfileEdit(req, resp);
		}
//		doPostProfileEdit(req, resp);
	}
	
	private void doGetProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//lay id, fullname, gmail <-gmail, password tu` cookie
		String[] eAp= this.getEmailAndPasswordFromCookie(req);
		String email = eAp[0];
		String password = eAp[1];		
		UserEntity user = profileService.findUserByEmailAndPassword(email, password);
		int userId = user.getId();		
		req.setAttribute("userEntity", user);
		
		
		//percentTask
		int taskPercentList[] = profileService.calculateTaskPercent(userId);
		
		req.setAttribute("taskPercent1", taskPercentList[0]);
		req.setAttribute("taskPercent2", taskPercentList[1]);
		req.setAttribute("taskPercent3", taskPercentList[2]);
		System.out.println("task percent 1 2 3: " + taskPercentList[0] + taskPercentList[1] + taskPercentList[2]);
		
		//List<TaskEntity> list1
		List<TaskEntity> taskList = profileService.findAllTaskByUserIdOrderByJobId(userId);
		req.setAttribute("taskList", taskList);
		System.out.println("kiem tra TaskList size: " + taskList.size());
		req.getRequestDispatcher("profile.jsp").forward(req, resp);
	}
	private void doGetProfileEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		
		int taskId = Integer.parseInt(req.getParameter("id"));
		currTaskId = taskId;
		//query lay task theo taskid
		TaskEntity task = taskRepository.getTaskById(taskId);
		req.setAttribute("task", task);
		
		//query lay danh sach status
		List<StatusEntity> statusList = statusRepository.findAll();
		req.setAttribute("listStatus", statusList);
		
		req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
	}
	
	private void doPostProfileEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		int idStatus = Integer.parseInt(req.getParameter("idStatus"));
		
		taskRepository.updateTaskStatusOnly(currTaskId, idStatus);
		int tempTaskId = currTaskId;
		currTaskId = -1;
		resp.sendRedirect(req.getContextPath()+"/profile-edit?id=" + tempTaskId);
	}
	
	
	public String[] getEmailAndPasswordFromCookie(HttpServletRequest req) {
		Cookie[] listCookie = req.getCookies();
		if(listCookie == null) {
			String[] ss = {"",""};
			return ss;
		}
		String email = "";
		String password = "";
		
		//duyet qua tung cookie ben trong List
		for(Cookie cookie : listCookie) {
			//Kiem tra ten cookie co phai la username khong
			if(cookie.getName().equals("email")) {
				//lay gia tri cua cookie username
				email = cookie.getValue();
//				System.out.println("Gia tri " + cookie.getValue());
			}
			if(cookie.getName().equals("password")) {
				password = cookie.getValue();
//				System.out.println("Gia tri " + cookie.getValue());
			}
		}
		
		String[] ss = {email, password};
		return ss;
	}
}
