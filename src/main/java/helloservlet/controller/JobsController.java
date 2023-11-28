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

import helloservlet.entity.DetailsJobsEntity;
import helloservlet.entity.JobsEntity;
import helloservlet.entity.RoleEntity;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserTableEntity;
import helloservlet.service.JobsService;
import helloservlet.service.RoleService;
import helloservlet.service.UserService;
//jobs = groupwork

@WebServlet (name = "jobsController", urlPatterns = {"/jobs-table", "/jobs-add", "/jobs-update", "/jobs-delete", "/jobs-details"})
public class JobsController extends HttpServlet{
	
	private JobsService jobsService = new JobsService();
	private int currId = -1; //for jobs_id (update)
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		String path = req.getServletPath();
		System.out.printf("kiem tra path: %s\n", path);
		
		if(path.equals("/jobs-table")) {
			doGetJobsTable(req, resp);
		}
		else if(path.equals("/jobs-add")) {
			doGetJobsAdd(req, resp);
		}
		else if(path.equals("/jobs-update")) {
			doGetJobsUpdate(req, resp);
		}
		else if(path.equals("/jobs-delete")) {
//			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			try {
				doGetJobsDelete(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(path.equals("/jobs-details")) {
			doGetJobsDetails(req, resp);
		}
		
//		req.getRequestDispatcher("user-table.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		
		String path = req.getServletPath();
		
		if(path.equals("/jobs-add")) {
			doPostJobsAdd(req, resp);
		}
		else if(path.equals("/jobs-update")) {
			doPostJobsUpdate(req, resp);
		}
		
	}
	
	private void doGetJobsTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("getRoleTable controller check 1");
		List<JobsEntity> list = jobsService.getAllJobs();
		
		req.setAttribute("listJobs", list);
		
		//jstl library -> ho~ tro nhung doan lenh loop/if-else -> tra ve html
		
		
		req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
		System.out.println("getJobsTable controller thanh cong");
	}
	private void doGetJobsAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		List<JobsEntity> list = jobsService.getAllRole();
		//select-option for role
//		req.setAttribute("listRole", list);
		
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
	}
	private void doGetJobsUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {				
		
		int id_Update = Integer.parseInt(req.getParameter("id"));
		System.out.println("kiem tra id_update gui tu phuong thuc GET: " +id_Update);
		
		JobsEntity job = jobsService.getJobById(id_Update);
		System.out.printf("check user query by id,userUpdateGet: %s_%s_%s_%s\n",
				job.getName(),
				job.getId(),
				job.getStringStartDate(),
				job.getStringEndDate());
		
		req.setAttribute("name", job.getName());
		req.setAttribute("start_date", job.getStringStartDate());
		req.setAttribute("end_date", job.getStringEndDate());
		
		this.currId = id_Update; 
		req.getRequestDispatcher("groupwork-update.jsp").forward(req, resp);
	}
	private void doGetJobsDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {				
		int id = Integer.parseInt(req.getParameter("id"));
		boolean deleteSuccess = jobsService.delete(id);
		if(deleteSuccess) {
			System.out.println("kiem tra job-delete, bobId = " + req.getParameter("id") + " thanh cong");
		}else {
			System.out.println("kiem tra job-delete, jobId = " + req.getParameter("id") + " that bai");
		}
		
		resp.sendRedirect(req.getContextPath()+ "/jobs-table");
	}
	private void doGetJobsDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {				
		
		int currJobId = Integer.parseInt(req.getParameter("id"));
		String jobName = jobsService.findNameById(currJobId);
		req.setAttribute("groupworkName", jobName);
		
		List<DetailsJobsEntity> listUserWithListTask = jobsService.findAllDetailsJobsEntityByJobsId(currJobId);
		req.setAttribute("taskListOfUser", listUserWithListTask);
		
		int taskPercentList[] = jobsService.calculateTaskPercent(currJobId);
		
		req.setAttribute("taskPercent1", taskPercentList[0]);
		req.setAttribute("taskPercent2", taskPercentList[1]);
		req.setAttribute("taskPercent3", taskPercentList[2]);
		System.out.println("task percent 1 2 3: " + taskPercentList[0] + taskPercentList[1] + taskPercentList[2]);
		
		List<DetailsJobsEntity> list = jobsService.findAllDetailsJobsEntityByJobsId(currJobId);
		req.setAttribute("taskListOfUser", list);
		
		req.getRequestDispatcher("groupwork-details.jsp").forward(req, resp);
	}	
	
	
	private void doPostJobsAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String startDate = req.getParameter("start_Date");
		String endDate = req.getParameter("end_Date");
		
		//----------------------chữa lỗi UTF-8------------------
		byte[] bytes1 = name.getBytes(StandardCharsets.ISO_8859_1);
		name = new String(bytes1, StandardCharsets.UTF_8);
		byte[] bytes2 = startDate.getBytes(StandardCharsets.ISO_8859_1);
		startDate = new String(bytes2, StandardCharsets.UTF_8);
		byte[] bytes3 = endDate.getBytes(StandardCharsets.ISO_8859_1);
		endDate = new String(bytes3, StandardCharsets.UTF_8);
		//-------------------------------------------------------
		System.out.println("check roleName and description: " + name + "_" + startDate + "_" + startDate);
		
		jobsService.insert(name, startDate, endDate);
		
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
	}
	private void doPostJobsUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String startDate = req.getParameter("start_Date");
		String endDate = req.getParameter("end_Date");
		
		//----------------------chữa lỗi UTF-8------------------
		byte[] bytes1 = name.getBytes(StandardCharsets.ISO_8859_1);
		name = new String(bytes1, StandardCharsets.UTF_8);
		byte[] bytes2 = startDate.getBytes(StandardCharsets.ISO_8859_1);
		startDate = new String(bytes2, StandardCharsets.UTF_8);
		byte[] bytes3 = endDate.getBytes(StandardCharsets.ISO_8859_1);
		endDate = new String(bytes3, StandardCharsets.UTF_8);
		//-------------------------------------------------------
		System.out.println("check roleName and description: " + name + "_" + startDate + "_" + startDate);
		
		jobsService.update(name, startDate, endDate, this.currId);
		
		int tempid = this.currId;
		this.currId = -1;
		resp.sendRedirect(req.getContextPath()+"/jobs-update?id=" + tempid);
	}
}
